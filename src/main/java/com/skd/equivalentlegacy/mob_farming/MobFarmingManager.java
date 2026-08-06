package com.skd.equivalentlegacy.mob_farming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;

public class MobFarmingManager {
    private static final Map<BlockPos, SpawnerState> activeSpawners = new HashMap<>();
    public static final int DETECTION_RANGE = 16;

    public static class SpawnerState {
        public BlockPos pos;
        public BlockEntity blockEntity;
        public boolean enabled = true;
        public int delay = 20;
        public int spawnCount = 4;
        public int maxNearby = 6;

        public SpawnerState(BlockPos pos, BlockEntity entity) {
            this.pos = pos;
            this.blockEntity = entity;
        }
    }

    public static void registerSpawner(BlockPos pos, BlockEntity spawner) {
        if (!activeSpawners.containsKey(pos)) {
            activeSpawners.put(pos, new SpawnerState(pos, spawner));
        }
    }

    public static void unregisterSpawner(BlockPos pos) {
        activeSpawners.remove(pos);
    }

    public static SpawnerState getSpawnerState(BlockPos pos) {
        return activeSpawners.get(pos);
    }

    public static void updateSpawnerConfig(BlockPos pos, int delay, int spawnCount, int maxNearby) {
        SpawnerState state = activeSpawners.get(pos);
        if (state != null) {
            state.delay = Math.max(20, Math.min(200, delay));
            state.spawnCount = Math.max(1, Math.min(8, spawnCount));
            state.maxNearby = Math.max(6, Math.min(32, maxNearby));
            state.blockEntity.setChanged();
        }
    }

    public static void toggleSpawner(BlockPos pos) {
        SpawnerState state = activeSpawners.get(pos);
        if (state != null) {
            state.enabled = !state.enabled;
        }
    }

    public static Map<BlockPos, SpawnerState> getActiveSpawners() {
        return new HashMap<>(activeSpawners);
    }

    public static void clear() {
        activeSpawners.clear();
    }
}
