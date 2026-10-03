package com.skd.equivalentlegacy.gameObjs.registration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class PERegistryUtil {

	private PERegistryUtil() {
	}

	public static BlockBehaviour.Properties blockProps(ResourceLocation id) {
		return BlockBehaviour.Properties.of();
	}

	public static Item.Properties itemProps(ResourceLocation id) {
		return new Item.Properties();
	}

	public static Item.Properties blockItemProps(ResourceLocation id) {
		return itemProps(id);
	}
}
