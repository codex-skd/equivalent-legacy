package com.skd.equivalentlegacy;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(EquivalentLegacy.MODID)
public class EquivalentLegacy {
    public static final String MODID = "equivalent_legacy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EquivalentLegacy(IEventBus modEventBus, ModContainer modContainer) {
        com.skd.equivalentlegacy.config.EquivalentLegacyConfig.register(modContainer);

        com.skd.equivalentlegacy.player.EquivalentLegacyAttachments.ATTACHMENT_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyItems.ITEMS.register(modEventBus);
        com.skd.equivalentlegacy.block.EquivalentLegacyBlocks.BLOCKS.register(modEventBus);
        com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.item.crafting.ModIngredientTypes.INGREDIENT_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.gui.ModMenuTypes.MENU_TYPES.register(modEventBus);

        com.skd.equivalentlegacy.network.PacketHandler.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Equivalent Legacy loading...");
        initEmcValues();
    }

    private static void initEmcValues() {
        var fixedValues = new com.skd.equivalentlegacy.emc.FixedValues();
        var nssItem = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.DARK_MATTER.get());
        fixedValues.addSetValueBefore(nssItem, 139_264L);

        var nssRed = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.RED_MATTER.get());
        fixedValues.addSetValueBefore(nssRed, 417_792L);

        var nssAlchemical = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.ALCHEMICAL_COAL.get());
        fixedValues.addSetValueBefore(nssAlchemical, 1_024L);

        var nssMobius = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.MOBIUS_FUEL.get());
        fixedValues.addSetValueBefore(nssMobius, 3_072L);

        var nssAeternalis = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.AETERNALIS_FUEL.get());
        fixedValues.addSetValueBefore(nssAeternalis, 9_216L);

        var nssAlchemicalBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.ALCHEMICAL_COAL_BLOCK.get());
        fixedValues.addSetValueBefore(nssAlchemicalBlock, 9_216L);

        var nssMobiusBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.MOBIUS_FUEL_BLOCK.get());
        fixedValues.addSetValueBefore(nssMobiusBlock, 27_648L);

        var nssAeternalisBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.AETERNALIS_FUEL_BLOCK.get());
        fixedValues.addSetValueBefore(nssAeternalisBlock, 82_944L);

        var nssDarkBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.DARK_MATTER_BLOCK.get());
        fixedValues.addSetValueBefore(nssDarkBlock, 1_253_376L);

        var nssRedBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.RED_MATTER_BLOCK.get());
        fixedValues.addSetValueBefore(nssRedBlock, 3_760_128L);

        var nssLowDust = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.LOW_COVALENCE_DUST.get());
        fixedValues.addSetValueBefore(nssLowDust, 512L);

        var nssMediumDust = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.MEDIUM_COVALENCE_DUST.get());
        fixedValues.addSetValueBefore(nssMediumDust, 1_024L);

        var nssHighDust = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.HIGH_COVALENCE_DUST.get());
        fixedValues.addSetValueBefore(nssHighDust, 2_048L);

        var nssTome = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.TOME.get());
        fixedValues.addSetValueBefore(nssTome, 25_172_992L);

        var nssKlein = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_EIN.get());
        fixedValues.addSetValueBefore(nssKlein, 24_576L);

        var nssKleinZwei = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_ZWEI.get());
        fixedValues.addSetValueBefore(nssKleinZwei, 98_304L);

        var nssKleinDrei = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_DREI.get());
        fixedValues.addSetValueBefore(nssKleinDrei, 393_216L);

        var nssKleinVier = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_VIER.get());
        fixedValues.addSetValueBefore(nssKleinVier, 1_572_864L);

        var nssKleinSphere = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_SPHERE.get());
        fixedValues.addSetValueBefore(nssKleinSphere, 6_291_456L);

        var nssKleinOmega = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_OMEGA.get());
        fixedValues.addSetValueBefore(nssKleinOmega, 25_165_824L);

        com.skd.equivalentlegacy.emc.EMCHelper.registerFixedValues(fixedValues);
        LOGGER.info("Registered phase 5 EMC values (Dark Matter, Klein Stars)");
    }

    public static Identifier rl(String path) {
        return Identifier.parse(MODID + ":" + path);
    }
}
