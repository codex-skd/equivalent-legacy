package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.player.EquivalentLegacyAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record KnowledgeSyncChangePayload(String itemId, boolean learned) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KnowledgeSyncChangePayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("sync_knowledge_change"));

    public static final StreamCodec<ByteBuf, KnowledgeSyncChangePayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, KnowledgeSyncChangePayload::itemId,
                    ByteBufCodecs.BOOL, KnowledgeSyncChangePayload::learned,
                    KnowledgeSyncChangePayload::new
            );

    @Override
    public CustomPacketPayload.Type<KnowledgeSyncChangePayload> type() {
        return TYPE;
    }

    public static void handle(KnowledgeSyncChangePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player != null) {
                var attachment = player.getData(EquivalentLegacyAttachments.PLAYER_KNOWLEDGE);
                if (payload.learned()) {
                    attachment.addKnowledge(payload.itemId());
                } else {
                    attachment.removeKnowledge(payload.itemId());
                }
            }
        });
    }
}
