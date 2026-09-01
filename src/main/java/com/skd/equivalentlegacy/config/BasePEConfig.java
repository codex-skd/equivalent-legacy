package com.skd.equivalentlegacy.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.config.value.CachedValue;
import net.neoforged.neoforge.common.ModConfigSpec;

public abstract class BasePEConfig implements IPEConfig {

	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	private final List<CachedValue<?>> cachedConfigValues = new ArrayList<>();

	@Override
	public void clearCache(boolean unloading) {
		for (CachedValue<?> cachedConfigValue : cachedConfigValues) {
			cachedConfigValue.clearCache(unloading);
		}
	}

	@Override
	public <T> void addCachedValue(CachedValue<T> configValue) {
		cachedConfigValues.add(configValue);
	}

	@Override
	public void save() {
		EXECUTOR.submit(new ConfigSaver(getConfigSpec()));
	}

	private static class ConfigSaver implements Runnable {

		private final ModConfigSpec configSpec;
		private int retries = 0;

		private ConfigSaver(ModConfigSpec configSpec) {
			this.configSpec = configSpec;
		}

		@Override
		public void run() {
			try {
				configSpec.save();
			} catch (Exception e) {
				ELCore.LOGGER.error("Failed to save config", e);
				if (retries++ < 3) {
					EXECUTOR.submit(this);
				} else {
					ELCore.LOGGER.error("Giving up");
				}
			}
		}
	}
}