package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.mob_farming.MobFarmingManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public class MobFarmingTickHandler {

    @SubscribeEvent
    static void onServerTick(ServerTickEvent.Post event) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            MobFarmingManager.tick(level);
        }
    }

    @SubscribeEvent
    static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level().isClientSide()) return;

        BlockPos mobPos = mob.blockPosition();

        var spawnerEither = event.getSpawner();
        BlockPos spawnerPos = null;
        if (spawnerEither != null) {
            spawnerPos = spawnerEither.left()
                    .map(BlockEntity::getBlockPos)
                    .orElse(null);
        }

        for (var entry : MobFarmingManager.getActiveSpawners().entrySet()) {
            BlockPos registeredPos = entry.getKey();
            var state = entry.getValue();

            if (!state.enabled) continue;

            double distSq = registeredPos.distSqr(mobPos);
            if (distSq > MobFarmingManager.DETECTION_RANGE * MobFarmingManager.DETECTION_RANGE) continue;

            mob.setPersistenceRequired();

            if (event.getSpawnType() == EntitySpawnReason.SPAWNER
                    && spawnerPos != null
                    && spawnerPos.equals(registeredPos)) {
                int nearbyCount = mob.level().getEntitiesOfClass(Mob.class,
                        new AABB(registeredPos).inflate(MobFarmingManager.DETECTION_RANGE),
                        m -> m != mob && m.isAlive()).size();

                if (nearbyCount >= state.maxNearby) {
                    event.setSpawnCancelled(true);
                }
            }
            break;
        }
    }
}
