package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.api.ItemInfo;
import com.skd.equivalentlegacy.api.components.DataComponentProcessor;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;

@DataComponentProcessor
public class ContainerProcessor extends SimpleContainerProcessor<ItemContainerContents> {

	@Override
	public String getName() {
		return PEConfigTranslations.DCP_CONTAINER.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.DCP_CONTAINER.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.DCP_CONTAINER.tooltip();
	}

	@Override
	protected DataComponentType<ItemContainerContents> getComponentType(@NotNull ItemInfo info) {
		return DataComponents.CONTAINER;
	}

	@Override
	protected boolean shouldPersist(@NotNull ItemInfo info, @NotNull ItemContainerContents component) {
		return !component.equals(ItemContainerContents.EMPTY);
	}

	@Override
	protected Iterable<ItemStack> getStoredItems(ItemContainerContents component) {
		return component.nonEmptyItemCopyStream().toList();
	}
}
