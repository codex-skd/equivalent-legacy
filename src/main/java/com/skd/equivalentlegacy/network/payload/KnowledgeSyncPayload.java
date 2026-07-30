package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.player.EquivalentLegacyAttachments;
import com.skd.equivalentlegacy.player.PlayerKnowledgeAttachment;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record KnowledgeSyncPayload(PlayerKnowledgeAttachment data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KnowledgeSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("sync_knowledge"));

    public static final StreamCodec<ByteBuf, KnowledgeSyncPayload> STREAM_CODEC =
            PlayerKnowledgeAttachment.STREAM_CODEC.map(KnowledgeSyncPayload::new, KnowledgeSyncPayload::data);

    @Override
    public CustomPacketPayload.Type<KnowledgeSyncPayload> type() {
        return TYPE;
    }

    public static void handle(KnowledgeSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player != null) {
                player.setData(EquivalentLegacyAttachments.PLAYER_KNOWLEDGE, payload.data());
            }
        });
    }
}
