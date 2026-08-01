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
        com.skd.equivalentlegacy.item.EquivalentLegacyCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
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
