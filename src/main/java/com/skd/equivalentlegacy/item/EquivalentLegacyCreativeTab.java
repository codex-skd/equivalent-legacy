package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquivalentLegacyCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EquivalentLegacy.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EQUIVALENT_LEGACY_TAB = CREATIVE_MODE_TABS.register("equivalent_legacy_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.equivalent_legacy"))
                    .icon(() -> new ItemStack(EquivalentLegacyItems.PHILOSOPHERS_STONE.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(EquivalentLegacyItems.PHILOSOPHERS_STONE.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_EIN.get());
                    })
                    .build());

    private EquivalentLegacyCreativeTab() {}
}
