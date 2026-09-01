package com.skd.equivalentlegacy.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public final class ItemCapabilityHelper {

	private ItemCapabilityHelper() {
	}

	@Nullable
	public static IItemHandler getPlayerInventory(Player player) {
		return player.getCapability(Capabilities.ItemHandler.ENTITY);
	}

	@Nullable
	public static IItemHandler of(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}
		return null;
	}
}
