package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
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
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_COAL.get());
                        output.accept(EquivalentLegacyItems.MOBIUS_FUEL.get());
                        output.accept(EquivalentLegacyItems.AETERNALIS_FUEL.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER.get());
                        output.accept(EquivalentLegacyBlocks.ALCHEMICAL_COAL_BLOCK.get());
                        output.accept(EquivalentLegacyBlocks.MOBIUS_FUEL_BLOCK.get());
                        output.accept(EquivalentLegacyBlocks.AETERNALIS_FUEL_BLOCK.get());
                        output.accept(EquivalentLegacyBlocks.DARK_MATTER_BLOCK.get());
                        output.accept(EquivalentLegacyBlocks.RED_MATTER_BLOCK.get());

                        output.accept(EquivalentLegacyItems.COLLECTOR_MK1.get());
                        output.accept(EquivalentLegacyItems.COLLECTOR_MK2.get());
                        output.accept(EquivalentLegacyItems.COLLECTOR_MK3.get());
                        output.accept(EquivalentLegacyItems.RELAY_MK1.get());
                        output.accept(EquivalentLegacyItems.RELAY_MK2.get());
                        output.accept(EquivalentLegacyItems.RELAY_MK3.get());
                        output.accept(EquivalentLegacyItems.CONDENSER_MK1.get());
                        output.accept(EquivalentLegacyItems.CONDENSER_MK2.get());

                        output.accept(EquivalentLegacyItems.DARK_MATTER_SWORD.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_PICKAXE.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_AXE.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_SHOVEL.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_HOE.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_SHEARS.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_HAMMER.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_HELMET.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_CHESTPLATE.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_LEGGINGS.get());
                        output.accept(EquivalentLegacyItems.DARK_MATTER_BOOTS.get());

                        output.accept(EquivalentLegacyItems.RED_MATTER_SWORD.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_PICKAXE.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_AXE.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_SHOVEL.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_HOE.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_SHEARS.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_HAMMER.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_KATAR.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_MORNING_STAR.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_HELMET.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_CHESTPLATE.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_LEGGINGS.get());
                        output.accept(EquivalentLegacyItems.RED_MATTER_BOOTS.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_EIN.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_ZWEI.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_DREI.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_VIER.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_SPHERE.get());
                        output.accept(EquivalentLegacyItems.KLEIN_STAR_OMEGA.get());
                    })
                    .build());

    private EquivalentLegacyCreativeTab() {}
}
