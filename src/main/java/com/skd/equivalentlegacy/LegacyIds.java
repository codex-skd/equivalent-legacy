package com.skd.equivalentlegacy;

import com.skd.equivalentlegacy.api.EquivalentLegacyAPI;
import com.skd.equivalentlegacy.gameObjs.registration.DoubleDeferredRegister;
import com.skd.equivalentlegacy.gameObjs.registries.PEAttachmentTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlocks;
import com.skd.equivalentlegacy.gameObjs.registries.PEContainerTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PECreativeTabs;
import com.skd.equivalentlegacy.gameObjs.registries.PEDataComponentTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEEntityTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEItems;
import com.skd.equivalentlegacy.gameObjs.registries.PENormalizedSimpleStacks;
import com.skd.equivalentlegacy.gameObjs.registries.PERecipeConditions;
import com.skd.equivalentlegacy.gameObjs.registries.PERecipeSerializers;
import com.skd.equivalentlegacy.gameObjs.registries.PESoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers legacy registry aliases so existing worlds keep resolving content after rebrands:
 * <ul>
 *   <li>{@code projecte:*} → {@code equivalent_legacy:*} (ProjectE / ProjectEE)</li>
 *   <li>{@code equivalence:*} → {@code equivalent_legacy:*} (Equivalence 1.4.x)</li>
 * </ul>
 * NeoForge 26.x no longer provides {@code MissingMappingsEvent}; {@link DeferredRegister#addAlias}
 * is the supported remapping path for registry objects.
 */
public final class LegacyIds {

	/** Former ProjectE / ProjectEE mod id. */
	public static final String LEGACY_MODID = EquivalentLegacyAPI.LEGACY_MODID;
	/** Former Equivalence 1.4.x mod id. */
	public static final String LEGACY_MODID_EQUIVALENCE = EquivalentLegacyAPI.LEGACY_MODID_EQUIVALENCE;
	/** All prior mod namespaces aliased into {@code equivalent_legacy}. */
	public static final String[] LEGACY_MODIDS = EquivalentLegacyAPI.LEGACY_MODIDS;

	private LegacyIds() {
	}

	/**
	 * Adds same-path aliases from every entry in {@link #LEGACY_MODIDS} for the deferred register.
	 * Must be called after all entries are registered to the deferred register, and before
	 * {@code RegisterEvent} fires.
	 */
	public static <T> void registerAliases(DeferredRegister<T> register) {
		for (DeferredHolder<T, ? extends T> holder : register.getEntries()) {
			ResourceLocation current = holder.getId();
			for (String legacyModId : LEGACY_MODIDS) {
				register.addAlias(ResourceLocation.fromNamespaceAndPath(legacyModId, current.getPath()), current);
			}
		}
	}

	/**
	 * Aliases both sides of a block/item (or similar) paired deferred register.
	 */
	public static void registerAliases(DoubleDeferredRegister<?, ?> register) {
		registerAliases(register.getPrimaryRegister());
		registerAliases(register.getSecondaryRegister());
	}

	/**
	 * Registers legacy aliases for every EquivalentLegacy deferred register that previously lived
	 * under {@code projecte} and/or {@code equivalence}.
	 */
	public static void registerAll() {
		registerAliases(PEAttachmentTypes.ATTACHMENT_TYPES);
		registerAliases(PEBlocks.BLOCKS);
		registerAliases(PEBlockEntityTypes.BLOCK_ENTITY_TYPES);
		registerAliases(PEBlockTypes.BLOCK_TYPES);
		registerAliases(PEContainerTypes.CONTAINER_TYPES);
		registerAliases(PECreativeTabs.CREATIVE_TABS);
		registerAliases(PEDataComponentTypes.DATA_COMPONENT_TYPES);
		registerAliases(PEEntityTypes.ENTITY_TYPES);
		registerAliases(PEItems.ITEMS);
		registerAliases(PENormalizedSimpleStacks.NSS_SERIALIZERS);
		registerAliases(PERecipeConditions.CONDITION_CODECS);
		registerAliases(PERecipeSerializers.RECIPE_SERIALIZERS);
		registerAliases(PESoundEvents.SOUND_EVENTS);
	}
}
