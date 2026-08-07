package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.chest.ChestNetwork;
import com.skd.equivalentlegacy.chest.ChestNetworkManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SyncNetworkDataPayload(UUID networkId, List<BlockPos> chestPositions, long totalEmc) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncNetworkDataPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("sync_network_data"));

    public static final StreamCodec<ByteBuf, SyncNetworkDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.map(UUID::fromString, UUID::toString), SyncNetworkDataPayload::networkId,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), SyncNetworkDataPayload::chestPositions,
            ByteBufCodecs.VAR_LONG, SyncNetworkDataPayload::totalEmc,
            SyncNetworkDataPayload::new
    );

    @Override
    public CustomPacketPayload.Type<SyncNetworkDataPayload> type() {
        return TYPE;
    }

    public static void handle(SyncNetworkDataPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientNetworkCache.handle(payload);
        });
    }

    public static final class ClientNetworkCache {
        private static final List<SyncNetworkDataPayload> cachedNetworks = new ArrayList<>();

        public static void handle(SyncNetworkDataPayload payload) {
            cachedNetworks.removeIf(n -> n.networkId().equals(payload.networkId()));
            if (!payload.chestPositions().isEmpty()) {
                cachedNetworks.add(payload);
            }
        }

        public static SyncNetworkDataPayload getNetworkFor(BlockPos chestPos) {
            for (SyncNetworkDataPayload network : cachedNetworks) {
                if (network.chestPositions().contains(chestPos)) {
                    return network;
                }
            }
            return null;
        }

        public static int getNetworkChestCount(BlockPos chestPos) {
            SyncNetworkDataPayload network = getNetworkFor(chestPos);
            return network != null ? network.chestPositions().size() : 0;
        }

        public static long getNetworkTotalEmc(BlockPos chestPos) {
            SyncNetworkDataPayload network = getNetworkFor(chestPos);
            return network != null ? network.totalEmc() : 0L;
        }

        public static void clear() {
            cachedNetworks.clear();
        }
    }
}
