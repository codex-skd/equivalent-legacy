package com.skd.equivalentlegacy.emc.components;

import com.skd.equivalentlegacy.emc.components.processor.IComponentProcessor;
import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main orchestrator for data component based EMC calculation.
 *
 * <p>Iterates the registered {@link IComponentProcessor}s and applies their additive
 * contributions on top of the base EMC value of an item. If any processor invalidates
 * the stack (overflow or a contained item without EMC) the whole stack is treated as
 * having no EMC value.</p>
 */
public final class DataComponentMapper {

	private static final List<IComponentProcessor> PROCESSORS = new ArrayList<>();

	private DataComponentMapper() {}

	public static void registerProcessor(IComponentProcessor processor) {
		PROCESSORS.add(processor);
	}

	public static List<IComponentProcessor> getProcessors() {
		return Collections.unmodifiableList(PROCESSORS);
	}

	public static boolean hasProcessors() {
		return !PROCESSORS.isEmpty();
	}

	/**
	 * Registers {@link DataComponentMapper#calculateComponentEMC(ItemStack, long)} as the
	 * component enhancer of the mapping handler. Called during mod common setup.
	 */
	public static void installEnhancer() {
		EMCMappingHandler.registerComponentEnhancer(DataComponentMapper::calculateComponentEMC);
	}

	/**
	 * Applies all registered processors on top of the base EMC value.
	 *
	 * @param stack   the stack with (potentially) modified components
	 * @param baseEmc the base EMC of the component-less variant of the item
	 * @return the enhanced EMC value, or zero if the stack has no valid EMC value
	 */
	public static long calculateComponentEMC(ItemStack stack, long baseEmc) {
		long current = baseEmc;
		for (IComponentProcessor processor : PROCESSORS) {
			if (!processor.isAvailable()) {
				continue;
			}
			try {
				current = Math.addExact(current, processor.calculateComponentEMC(stack, EMCMappingHandler.INSTANCE));
			} catch (ArithmeticException e) {
				//Overflow or a processor explicitly invalidated the stack
				return 0;
			}
			if (current <= 0) {
				return 0;
			}
		}
		return current;
	}
}
