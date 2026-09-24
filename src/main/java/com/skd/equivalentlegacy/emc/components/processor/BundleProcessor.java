package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.api.ItemInfo;
import com.skd.equivalentlegacy.api.components.DataComponentProcessor;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.jetbrains.annotations.NotNull;

//TODO: Is there some way to only enable this processor when FeatureFlags.BUNDLE is enabled? It doesn't seem worth figuring out currently as
// vanilla doesn't even have BundleItem return false for isEnabled(FeatureFlagSet) and instead just doesn't add them to the creative menu
@DataComponentProcessor
public class BundleProcessor extends SimpleContainerProcessor<BundleContents> {

	@Override
	public String getName() {
		return PEConfigTranslations.DCP_BUNDLE.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.DCP_BUNDLE.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.DCP_BUNDLE.tooltip();
	}

	@Override
	protected DataComponentType<BundleContents> getComponentType(@NotNull ItemInfo info) {
		return DataComponents.BUNDLE_CONTENTS;
	}

	@Override
	protected boolean shouldPersist(@NotNull ItemInfo info, @NotNull BundleContents component) {
		return !component.isEmpty();
	}

	@Override
	protected Iterable<ItemStack> getStoredItems(BundleContents component) {
		return component.itemCopyStream().toList();
	}
}
