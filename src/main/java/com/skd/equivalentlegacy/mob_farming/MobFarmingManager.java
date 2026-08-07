package com.skd.equivalentlegacy.mob_farming;

import com.skd.equivalentlegacy.block.entity.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobFarmingManager {
    private static final Map<BlockPos, SpawnerState> activeSpawners = new HashMap<>();
    public static final int DETECTION_RANGE = 16;
    public static final int XP_TO_EMC_RATIO = 7;

    public static class SpawnerState {
        public BlockPos pos;
        public BlockEntity blockEntity;
        public boolean enabled = true;
        public int delay = 20;
        public int spawnCount = 4;
        public int maxNearby = 6;
        public int tickCooldown;
        public int totalXpCollected;
        public int totalDropsCollected;

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

    public static void tick(ServerLevel level) {
        List<BlockPos> toRemove = new ArrayList<>();

        for (Map.Entry<BlockPos, SpawnerState> entry : activeSpawners.entrySet()) {
            BlockPos pos = entry.getKey();
            SpawnerState state = entry.getValue();

            if (!state.enabled) continue;

            BlockEntity be = level.getBlockEntity(pos);
            if (be == null || be.isRemoved()) {
                toRemove.add(pos);
                continue;
            }

            state.blockEntity = be;

            if (state.tickCooldown > 0) {
                state.tickCooldown--;
            }

            List<PedestalBlockEntity> pedestals = findNearbyPedestals(level, pos);
            for (PedestalBlockEntity pedestal : pedestals) {
                if (!pedestal.isMobFarmingSetup()) continue;
                ItemStack displayed = pedestal.getDisplayedItem();
                String itemName = displayed.getItem().toString();

                if (itemName.contains("mind_stone")) {
                    long xp = pedestal.collectNearbyXp();
                    if (xp > 0) {
                        pedestal.addEmc(xp * XP_TO_EMC_RATIO);
                        state.totalXpCollected += (int)xp;
                        pedestal.setChanged();
                    }
                }
                if (itemName.contains("black_hole_band")) {
                    long emcGained = pedestal.collectNearbyDrops();
                    if (emcGained > 0) {
                        pedestal.addEmc(emcGained);
                        state.totalDropsCollected++;
                        pedestal.setChanged();
                    }
                }
            }
        }

        for (BlockPos pos : toRemove) {
            unregisterSpawner(pos);
        }
    }

    private static List<PedestalBlockEntity> findNearbyPedestals(ServerLevel level, BlockPos center) {
        List<PedestalBlockEntity> result = new ArrayList<>();
        int r = DETECTION_RANGE / 2;
        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-r, -r, -r),
                center.offset(r, r, r))) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof PedestalBlockEntity pedestal) {
                result.add(pedestal);
            }
        }
        if (result.isEmpty()) {
            BlockPos self = new BlockPos(
                    center.getX() + DETECTION_RANGE / 2,
                    center.getY(),
                    center.getZ());
            BlockEntity be = level.getBlockEntity(self);
            if (be instanceof PedestalBlockEntity ped) {
                result.add(ped);
            }
        }
        return result;
    }

    public static boolean isSpawnerRegistered(BlockPos pos) {
        return activeSpawners.containsKey(pos);
    }
}
