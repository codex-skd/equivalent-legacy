package com.skd.equivalentlegacy.emc.components.processor;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;

/**
 * Adds the EMC of items stored inside a bundle.
 */
public class BundleProcessor extends SimpleContainerProcessor<BundleContents> {

	@Override
	public String getName() {
		return "BundleProcessor";
	}

	@Override
	protected DataComponentType<BundleContents> getComponentType() {
		return DataComponents.BUNDLE_CONTENTS;
	}

	@Override
	protected Iterable<ItemStack> getStoredItems(BundleContents component) {
		return component.itemCopyStream().toList();
	}
}
