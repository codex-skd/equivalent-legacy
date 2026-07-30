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
        com.skd.equivalentlegacy.gui.ModMenuTypes.MENU_TYPES.register(modEventBus);

        com.skd.equivalentlegacy.network.PacketHandler.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Equivalent Legacy loading...");
    }

    public static Identifier rl(String path) {
        return Identifier.parse(MODID + ":" + path);
    }
}
