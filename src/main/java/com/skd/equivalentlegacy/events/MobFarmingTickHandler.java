package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.mob_farming.MobFarmingManager;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public class MobFarmingTickHandler {

    @SubscribeEvent
    static void onServerTick(ServerTickEvent.Post event) {
        MobFarmingManager.getActiveSpawners().forEach((pos, state) -> {
            if (!state.enabled) return;
            if (state.blockEntity == null || state.blockEntity.getLevel() == null) return;

            AABB range = new AABB(pos).inflate(16);
            var xpOrbs = state.blockEntity.getLevel().getEntitiesOfClass(ExperienceOrb.class, range);

            xpOrbs.forEach(orb -> {
                orb.discard();
            });
        });
    }
}
