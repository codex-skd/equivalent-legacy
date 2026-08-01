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
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_BLACK.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_BLUE.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_BROWN.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_CYAN.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_GRAY.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_GREEN.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_LIGHT_BLUE.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_LIGHT_GRAY.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_LIME.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_MAGENTA.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_ORANGE.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_PINK.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_PURPLE.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_RED.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_WHITE.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_BAG_YELLOW.get());
                        output.accept(EquivalentLegacyItems.ALCHEMICAL_CHEST.get());

                        output.accept(EquivalentLegacyItems.LOW_COVALENCE_DUST.get());
                        output.accept(EquivalentLegacyItems.MEDIUM_COVALENCE_DUST.get());
                        output.accept(EquivalentLegacyItems.HIGH_COVALENCE_DUST.get());
                        output.accept(EquivalentLegacyItems.TOME.get());

                        output.accept(EquivalentLegacyItems.IRON_BAND.get());
                        output.accept(EquivalentLegacyItems.BLACK_HOLE_BAND.get());
                        output.accept(EquivalentLegacyItems.GEM_OF_ETERNAL_DENSITY.get());
                        output.accept(EquivalentLegacyItems.HARVEST_GODDESS_BAND.get());
                        output.accept(EquivalentLegacyItems.IGNITION_RING.get());
                        output.accept(EquivalentLegacyItems.SWIFTWOLF_RENDING_GALE.get());
                        output.accept(EquivalentLegacyItems.VOID_RING.get());
                        output.accept(EquivalentLegacyItems.ZERO_RING.get());
                        output.accept(EquivalentLegacyItems.ARCANA_RING.get());
                        output.accept(EquivalentLegacyItems.BODY_STONE.get());
                        output.accept(EquivalentLegacyItems.EVERTIDE_AMULET.get());
                        output.accept(EquivalentLegacyItems.LIFE_STONE.get());
                        output.accept(EquivalentLegacyItems.MIND_STONE.get());
                        output.accept(EquivalentLegacyItems.SOUL_STONE.get());
                        output.accept(EquivalentLegacyItems.VOLCANITE_AMULET.get());
                        output.accept(EquivalentLegacyItems.REPAIR_TALISMAN.get());
                        output.accept(EquivalentLegacyItems.WATCH_OF_FLOWING_TIME.get());

                        output.accept(EquivalentLegacyItems.NOVA_CATALYST.get());
                        output.accept(EquivalentLegacyItems.NOVA_CATACLYSM.get());
                        output.accept(EquivalentLegacyItems.DIVINING_ROD_1.get());
                        output.accept(EquivalentLegacyItems.DIVINING_ROD_2.get());
                        output.accept(EquivalentLegacyItems.DIVINING_ROD_3.get());
                        output.accept(EquivalentLegacyItems.DESTRUCTION_CATALYST.get());
                        output.accept(EquivalentLegacyItems.HYPERKINETIC_LENS.get());
                        output.accept(EquivalentLegacyItems.CATALYTIC_LENS.get());
                        output.accept(EquivalentLegacyItems.MERCURIAL_EYE.get());
                        output.accept(EquivalentLegacyItems.ARCHANGEL_SMITE.get());

                        output.accept(EquivalentLegacyItems.TRANSMUTATION_TABLET.get());
                        output.accept(EquivalentLegacyItems.TRANSMUTATION_TABLE.get());
                        output.accept(EquivalentLegacyItems.INTERDICTION_TORCH.get());

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
