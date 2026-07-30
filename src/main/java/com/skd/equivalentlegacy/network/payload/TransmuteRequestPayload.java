package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.gui.TransmutationContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TransmuteRequestPayload(String itemId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TransmuteRequestPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("transmute_request"));

    public static final StreamCodec<ByteBuf, TransmuteRequestPayload> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(TransmuteRequestPayload::new, TransmuteRequestPayload::itemId);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TransmuteRequestPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player != null && player.containerMenu instanceof TransmutationContainer container) {
                container.transmute(payload.itemId());
            }
        });
    }
}
