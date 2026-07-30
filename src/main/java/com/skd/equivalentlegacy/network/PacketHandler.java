package com.skd.equivalentlegacy.network;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.network.payload.KnowledgeDataPayload;
import com.skd.equivalentlegacy.network.payload.KnowledgeSyncChangePayload;
import com.skd.equivalentlegacy.network.payload.KnowledgeSyncEmcPayload;
import com.skd.equivalentlegacy.network.payload.KnowledgeSyncPayload;
import com.skd.equivalentlegacy.network.payload.TransmuteRequestPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class PacketHandler {
    private PacketHandler() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            PayloadRegistrar registrar = event.registrar(EquivalentLegacy.MODID);

            registrar.playToClient(
                    KnowledgeSyncPayload.TYPE,
                    KnowledgeSyncPayload.STREAM_CODEC,
                    KnowledgeSyncPayload::handle
            );

            registrar.playToClient(
                    KnowledgeSyncEmcPayload.TYPE,
                    KnowledgeSyncEmcPayload.STREAM_CODEC,
                    KnowledgeSyncEmcPayload::handle
            );

            registrar.playToClient(
                    KnowledgeSyncChangePayload.TYPE,
                    KnowledgeSyncChangePayload.STREAM_CODEC,
                    KnowledgeSyncChangePayload::handle
            );

            registrar.playToClient(
                    KnowledgeDataPayload.TYPE,
                    KnowledgeDataPayload.STREAM_CODEC,
                    KnowledgeDataPayload::handle
            );

            registrar.playToServer(
                    TransmuteRequestPayload.TYPE,
                    TransmuteRequestPayload.STREAM_CODEC,
                    TransmuteRequestPayload::handle
            );
        });
    }
}
