package com.skd.equivalentlegacy.emc.mapper;

import com.mojang.logging.LogUtils;
import com.skd.equivalentlegacy.emc.CustomConversion;
import com.skd.equivalentlegacy.emc.FixedValues;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Central coordinator for EMC value resolution.
 *
 * <p>Mappers are resolved in priority order:
 * <ol>
 *     <li>{@link FixedValues} (highest priority, seeded via {@link #initialize(FixedValues)})</li>
 *     <li>Registered {@link IEMCMapper}s in registration order (recipe mappers, then special mappers)</li>
 *     <li>Data component enhancers applied on top of the base map (registered via
 *     {@link #registerComponentEnhancer(IComponentEnhancer)})</li>
 * </ol>
 * The graph-based fallback ({@link SimpleGraphMapper}) is used to derive values from the
 * conversions contributed by the fixed values and the registered mappers.</p>
 *
 * <p>Phase 1B constraint: simple {@code long} arithmetic, circular dependencies are detected
 * and reported as errors (not resolved).</p>
 */
public final class EMCMappingHandler {

	public static final EMCMappingHandler INSTANCE = new EMCMappingHandler();

	/**
	 * Functional hook applied to a stack that carries data components. Registered by the
	 * data component subsystem on top of the base (component-less) EMC value.
	 */
	@FunctionalInterface
	public interface IComponentEnhancer {
		long enhance(ItemStack stack, long baseEmc);
	}

	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Object LOCK = new Object();

	private static FixedValues fixedValues = new FixedValues();
	private static final List<IEMCMapper> mappers = new ArrayList<>();
	private static final List<IComponentEnhancer> componentEnhancers = new ArrayList<>();

	private static volatile Object2LongMap<NormalizedSimpleStack> emcValues = new Object2LongOpenHashMap<>();
	private static volatile boolean dirty = true;

	private EMCMappingHandler() {}

	/**
	 * Seeds the handler with the hardcoded fixed values (highest priority source).
	 */
	public static void initialize(FixedValues defaults) {
		synchronized (LOCK) {
			fixedValues = defaults;
			dirty = true;
		}
	}

	/**
	 * Registers a mapper. Mappers registered earlier take priority over later ones.
	 */
	public static void registerMapper(IEMCMapper mapper) {
		synchronized (LOCK) {
			mappers.add(mapper);
			dirty = true;
		}
	}

	/**
	 * Registers a data-component enhancer applied on top of the base EMC value at query time.
	 */
	public static void registerComponentEnhancer(IComponentEnhancer enhancer) {
		synchronized (LOCK) {
			componentEnhancers.add(enhancer);
		}
	}

	/**
	 * Forces the cached mapping to be cleared and recomputed on the next query.
	 */
	public static void invalidateCache() {
		dirty = true;
	}

	/**
	 * Recomputes the full mapping graph from the fixed values and all registered mappers.
	 */
	public static void remap() {
		synchronized (LOCK) {
			SimpleGraphMapper<NormalizedSimpleStack, Long, LongArithmetic> mapper = new SimpleGraphMapper<>(LongArithmetic.INSTANCE);

			for (var entry : fixedValues.setValueBefore().object2LongEntrySet()) {
				mapper.setValueBefore(entry.getKey(), entry.getLongValue());
			}
			for (var entry : fixedValues.setValueAfter().object2LongEntrySet()) {
				mapper.setValueAfter(entry.getKey(), entry.getLongValue());
			}
			for (CustomConversion conversion : fixedValues.conversions()) {
				mapper.addConversion(conversion.count(), conversion.output(), conversion.ingredients());
			}

			for (IEMCMapper emcMapper : mappers) {
				try {
					emcMapper.addMappings(mapper);
					LOGGER.debug("Collected mappings from {}", emcMapper.getName());
				} catch (Exception e) {
					LOGGER.error("Exception during mapping collection from mapper {}. EMC values might be inconsistent!", emcMapper.getName(), e);
				}
			}

			detectCircularDependencies(mapper);

			Object2LongOpenHashMap<NormalizedSimpleStack> resolved = new Object2LongOpenHashMap<>();
			mapper.generateValues().forEach((stack, value) -> resolved.put(stack, value));
			emcValues = resolved;
			dirty = false;
			LOGGER.info("Remapped {} EMC values", emcValues.size());
		}
	}

	/**
	 * Public entry point: returns the EMC value of the given stack, or zero if the item
	 * (or one of its components) has no EMC value.
	 */
	public long getEMCValue(ItemStack stack) {
		ensureMapped();
		if (stack.isEmpty()) {
			return 0L;
		}
		long baseValue = getBaseEMCValue(NSSItem.createItem(stack.getItem()));
		if (baseValue <= 0) {
			return 0L;
		}
		if (stack.getComponentsPatch().isEmpty()) {
			return baseValue;
		}
		return enhanceWithComponents(stack, baseValue);
	}

	/**
	 * Returns the base (component-less) EMC value of a normalized stack.
	 */
	public long getBaseEMCValue(NormalizedSimpleStack stack) {
		ensureMapped();
		return emcValues.getLong(stack);
	}

	/**
	 * Returns the EMC value for a fluid. Fluids are not mapped in Phase 1B, so this
	 * always returns zero.
	 */
	public long getEMCValue(Fluid fluid) {
		return 0L;
	}

	/**
	 * Returns the EMC value for an item tag: the lowest positive EMC among its elements,
	 * or zero if none of the tag's elements have an EMC value. Non-item tags yield zero.
	 */
	public long getEMCValue(TagKey<?> tag) {
		ensureMapped();
		if (tag.isFor(Registries.ITEM)) {
			@SuppressWarnings("unchecked")
			TagKey<Item> itemTag = (TagKey<Item>) tag;
			long min = 0;
			for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(itemTag)) {
				long value = emcValues.getLong(NSSItem.createItem(holder.value()));
				if (value > 0 && (min == 0 || value < min)) {
					min = value;
				}
			}
			return min;
		}
		return 0L;
	}

	public boolean hasEMCValue(ItemStack stack) {
		return getEMCValue(stack) > 0;
	}

	public int getEmcMapSize() {
		ensureMapped();
		return emcValues.size();
	}

	public Set<NormalizedSimpleStack> getMappedStacks() {
		ensureMapped();
		return new HashSet<>(emcValues.keySet());
	}

	public MappingResult resolve(ItemStack stack) {
		ensureMapped();
		NSSItem base = NSSItem.createItem(stack.getItem());
		long baseValue = emcValues.getLong(base);
		if (baseValue <= 0) {
			return MappingResult.absent(base);
		}
		if (stack.getComponentsPatch().isEmpty()) {
			return MappingResult.of(base, baseValue, "fixed");
		}
		long enhanced = enhanceWithComponents(stack, baseValue);
		return enhanced > 0 ? MappingResult.of(base, enhanced, "fixed+components") : MappingResult.absent(base);
	}

	private long enhanceWithComponents(ItemStack stack, long baseValue) {
		long value = baseValue;
		for (IComponentEnhancer enhancer : componentEnhancers) {
			try {
				value = enhancer.enhance(stack, value);
			} catch (ArithmeticException e) {
				//Overflow or invalid calculation: treat as no EMC value
				return 0L;
			}
			if (value <= 0) {
				return 0L;
			}
		}
		return value;
	}

	private static void ensureMapped() {
		if (dirty) {
			synchronized (LOCK) {
				if (dirty) {
					remap();
				}
			}
		}
	}

	private static void detectCircularDependencies(SimpleGraphMapper<NormalizedSimpleStack, Long, LongArithmetic> mapper) {
		Set<NormalizedSimpleStack> visiting = new HashSet<>();
		Set<NormalizedSimpleStack> visited = new HashSet<>();
		for (MappingCollector.Conversion<NormalizedSimpleStack> conversion : mapper.getConversions()) {
			if (!visited.contains(conversion.output) && hasCycle(conversion.output, mapper, visiting, visited)) {
				throw new IllegalStateException("Circular EMC conversion dependency detected starting at: " + conversion.output);
			}
		}
	}

	private static boolean hasCycle(NormalizedSimpleStack node,
									SimpleGraphMapper<NormalizedSimpleStack, Long, LongArithmetic> mapper,
									Set<NormalizedSimpleStack> visiting, Set<NormalizedSimpleStack> visited) {
		if (visiting.contains(node)) {
			return true;
		}
		if (visited.contains(node)) {
			return false;
		}
		visiting.add(node);
		for (MappingCollector.Conversion<NormalizedSimpleStack> conversion : mapper.getConversions()) {
			if (conversion.output != null && conversion.output.equals(node)) {
				for (NormalizedSimpleStack ingredient : conversion.ingredients.keySet()) {
					if (hasCycle(ingredient, mapper, visiting, visited)) {
						return true;
					}
				}
			}
		}
		visiting.remove(node);
		visited.add(node);
		return false;
	}
}
