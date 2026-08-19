package com.skd.equivalentlegacy.config;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.skd.equivalentlegacy.ELCore;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLPaths;

public class EquivalentLegacyConfig {

	//Prior to 1.5.2 this resolved ELCore.MODNAME ("EquivalentLegacy"), so existing installs had their
	// config under config/EquivalentLegacy/ instead of config/equivalent_legacy/ — see CHANGELOG 1.5.2.
	public static final Path CONFIG_DIR = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(ELCore.MODID));
	private static final Map<IConfigSpec, IPEConfig> KNOWN_CONFIGS = new HashMap<>();

	public static final ServerConfig server = new ServerConfig();
	public static final CommonConfig common = new CommonConfig();
	public static final ClientConfig client = new ClientConfig();

	public static void register(ModContainer modContainer) {
		registerConfig(modContainer, server);
		registerConfig(modContainer, common);
		registerConfig(modContainer, client);
	}

	public static Collection<IPEConfig> getConfigs() {
		return Collections.unmodifiableCollection(KNOWN_CONFIGS.values());
	}

	/**
	 * Creates and register a mod config, and track it so that we can properly clear cached values.
	 */
	public static void registerConfig(ModContainer modContainer, IPEConfig config) {
		modContainer.registerConfig(config.getConfigType(), config.getConfigSpec(), ELCore.MODID + "/" + config.getFileName() + ".toml");
		KNOWN_CONFIGS.put(config.getConfigSpec(), config);
	}

	public static void onConfigLoad(ModConfigEvent event) {
		//Note: We listen to both the initial load and the reload, to make sure that we fix any accidentally
		// cached values from calls before the initial loading
		ModConfig config = event.getConfig();
		//Make sure it is for the same modid as us
		if (config.getModId().equals(ELCore.MODID)) {
			IPEConfig peConfig = KNOWN_CONFIGS.get(config.getSpec());
			if (peConfig != null) {
				peConfig.clearCache(event instanceof ModConfigEvent.Unloading);
			}
		}
	}
}