package com.skd.equivalentlegacy.api;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class PEDataComponents {

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE = get("charge");

	private PEDataComponents() {
	}

	private static <TYPE> DeferredHolder<DataComponentType<?>, DataComponentType<TYPE>> get(String name) {
		return DeferredHolder.create(Registries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(EquivalentLegacyAPI.EQUIVALENT_LEGACY_MODID, name));
	}
}