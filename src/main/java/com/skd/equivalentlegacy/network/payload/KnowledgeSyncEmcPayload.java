package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.player.EquivalentLegacyAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record KnowledgeSyncEmcPayload(long emc) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KnowledgeSyncEmcPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("sync_emc"));

    public static final StreamCodec<ByteBuf, KnowledgeSyncEmcPayload> STREAM_CODEC =
            ByteBufCodecs.VAR_LONG.map(KnowledgeSyncEmcPayload::new, KnowledgeSyncEmcPayload::emc);

    @Override
    public CustomPacketPayload.Type<KnowledgeSyncEmcPayload> type() {
        return TYPE;
    }

    public static void handle(KnowledgeSyncEmcPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player != null) {
                var attachment = player.getData(EquivalentLegacyAttachments.PLAYER_KNOWLEDGE);
                attachment.setEmc(payload.emc());
            }
        });
    }
}
