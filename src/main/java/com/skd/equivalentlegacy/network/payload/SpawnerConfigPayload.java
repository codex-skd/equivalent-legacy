package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.mob_farming.MobFarmingManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpawnerConfigPayload(
    BlockPos spawnerPos,
    int delay,
    int spawnCount,
    int maxNearby
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SpawnerConfigPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("spawner_config"));

    public static final StreamCodec<ByteBuf, SpawnerConfigPayload> STREAM_CODEC = StreamCodec.of(
        (buf, payload) -> {
            buf.writeLong(payload.spawnerPos.asLong());
            buf.writeInt(payload.delay);
            buf.writeInt(payload.spawnCount);
            buf.writeInt(payload.maxNearby);
        },
        buf -> new SpawnerConfigPayload(
            BlockPos.of(buf.readLong()),
            buf.readInt(),
            buf.readInt(),
            buf.readInt()
        )
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SpawnerConfigPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player != null && !player.level().isClientSide()) {
                MobFarmingManager.updateSpawnerConfig(
                    payload.spawnerPos(),
                    payload.delay(),
                    payload.spawnCount(),
                    payload.maxNearby()
                );
            }
        });
    }
}
