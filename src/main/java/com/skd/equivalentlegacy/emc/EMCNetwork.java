package com.skd.equivalentlegacy.emc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EMCNetwork {
    private static final long MAX_POOL = 1_000_000_000L;
    private static final Map<ResourceKey<Level>, Long> POOLS = new ConcurrentHashMap<>();

    private EMCNetwork() {}

    public static long getEmc(Level level) {
        return POOLS.getOrDefault(level.dimension(), 0L);
    }

    public static long getEmc(ResourceKey<Level> dimension) {
        return POOLS.getOrDefault(dimension, 0L);
    }

    public static void setEmc(ResourceKey<Level> dimension, long amount) {
        POOLS.put(dimension, Math.max(0, Math.min(MAX_POOL, amount)));
    }

    public static void addEmc(Level level, long amount) {
        if (amount <= 0) return;
        POOLS.merge(level.dimension(), amount, (current, add) -> Math.min(MAX_POOL, current + add));
    }

    public static long takeEmc(Level level, long max) {
        if (max <= 0) return 0;
        Long current = POOLS.get(level.dimension());
        if (current == null || current <= 0) return 0;
        long taken = Math.min(current, max);
        POOLS.put(level.dimension(), current - taken);
        return taken;
    }
}
