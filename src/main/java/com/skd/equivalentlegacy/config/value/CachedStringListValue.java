package com.skd.equivalentlegacy.config.value;

import java.util.List;
import java.util.function.Supplier;
import com.skd.equivalentlegacy.config.IPEConfig;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

/**
 * From Mekanism
 */
public class CachedStringListValue extends CachedValue<List<? extends String>> implements Supplier<List<? extends String>> {

	private boolean resolved;
	private List<? extends String> cachedValue;

	private CachedStringListValue(IPEConfig config, ConfigValue<List<? extends String>> internal) {
		super(config, internal);
	}

	public static CachedStringListValue wrap(IPEConfig config, ConfigValue<List<? extends String>> internal) {
		return new CachedStringListValue(config, internal);
	}

	public List<? extends String> getOrDefault() {
		if (resolved || isLoaded()) {
			return get();
		}
		return internal.getDefault();
	}

	@Override
	public List<? extends String> get() {
		if (!resolved) {
			//If we don't have a cached value or need to resolve it again, get it from the actual ConfigValue
			cachedValue = internal.get();
			resolved = true;
		}
		return cachedValue;
	}

	public void set(List<? extends String> value) {
		internal.set(value);
		cachedValue = value;
	}

	@Override
	protected boolean clearCachedValue(boolean checkChanged) {
		if (!resolved) {
			//Isn't cached don't need to clear it or run any invalidation listeners
			return false;
		}
		List<? extends String> oldCachedValue = cachedValue;
		resolved = false;
		//Return if we are meant to check the changed ones, and it is different then it used to be
		return checkChanged && !oldCachedValue.equals(get());
	}
}
