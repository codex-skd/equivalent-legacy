package com.skd.equivalentlegacy.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public final class EquivalentLegacyConfig {
    private static final Logger LOGGER = LogUtils.getLogger();

    private EquivalentLegacyConfig() {}

    public static void register(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        container.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        LOGGER.info("Registered configs: common, client, server");
    }
}
