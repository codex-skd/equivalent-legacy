package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.emc.EMCNetwork;
import com.skd.equivalentlegacy.emc.EMCNetworkData;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public final class NetworkEvents {
    private NetworkEvents() {}

    @SubscribeEvent
    static void onServerStarting(ServerStartingEvent event) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            EMCNetworkData data = level.getDataStorage().computeIfAbsent(EMCNetworkData.TYPE);
            EMCNetwork.setEmc(level.dimension(), data.emc);
        }
    }

    @SubscribeEvent
    static void onServerStopping(ServerStoppingEvent event) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            EMCNetworkData data = level.getDataStorage().computeIfAbsent(EMCNetworkData.TYPE);
            data.emc = EMCNetwork.getEmc(level.dimension());
            data.setDirty();
        }
    }
}
