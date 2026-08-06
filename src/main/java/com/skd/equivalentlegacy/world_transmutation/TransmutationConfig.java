package com.skd.equivalentlegacy.world_transmutation;

import com.skd.equivalentlegacy.config.ServerConfig;

/**
 * Live view over the world_transmutation server config plus a couple of constants.
 * Tuned by the entries in {@link ServerConfig} (added in Phase 2).
 */
public final class TransmutationConfig {
    public static final int DEFAULT_RANGE = 4;
    public static final int DEFAULT_COOLDOWN = 10;

    private TransmutationConfig() {}

    public static int getRange() {
        return DEFAULT_RANGE;
    }

    public static int getCooldown() {
        return ServerConfig.COOLDOWN_PLAYER != null ? ServerConfig.COOLDOWN_PLAYER.get() : DEFAULT_COOLDOWN;
    }

    public static boolean emitsParticles() {
        return true;
    }
}