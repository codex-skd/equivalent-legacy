package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MobFarmingStatePayload(
    BlockPos pedestalPos,
    boolean active,
    int xpStored,
    int dropsCollected
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MobFarmingStatePayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("mob_farming_state"));

    public static final StreamCodec<ByteBuf, MobFarmingStatePayload> STREAM_CODEC = StreamCodec.of(
        (buf, payload) -> {
            buf.writeLong(payload.pedestalPos.asLong());
            buf.writeBoolean(payload.active);
            buf.writeInt(payload.xpStored);
            buf.writeInt(payload.dropsCollected);
        },
        buf -> new MobFarmingStatePayload(
            BlockPos.of(buf.readLong()),
            buf.readBoolean(),
            buf.readInt(),
            buf.readInt()
        )
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MobFarmingStatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
        });
    }
}
