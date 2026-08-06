package com.skd.equivalentlegacy.emc.components.processor;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/**
 * Adds the EMC of items stored in the {@code minecraft:container} component
 * (shulker boxes, bundles, alchemical bags, chest-like containers, ...).
 */
public class ContainerProcessor extends SimpleContainerProcessor<ItemContainerContents> {

	@Override
	public String getName() {
		return "ContainerProcessor";
	}

	@Override
	protected DataComponentType<ItemContainerContents> getComponentType() {
		return DataComponents.CONTAINER;
	}

	@Override
	protected Iterable<ItemStack> getStoredItems(ItemContainerContents component) {
		return component.nonEmptyItemCopyStream().toList();
	}
}
