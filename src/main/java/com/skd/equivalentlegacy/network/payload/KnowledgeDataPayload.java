package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public record KnowledgeDataPayload(Map<String, Long> entries) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KnowledgeDataPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("knowledge_data"));

    public static final StreamCodec<ByteBuf, KnowledgeDataPayload> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public KnowledgeDataPayload decode(ByteBuf buf) {
            int size = ByteBufCodecs.VAR_INT.decode(buf);
            Map<String, Long> map = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                map.put(ByteBufCodecs.STRING_UTF8.decode(buf), ByteBufCodecs.VAR_LONG.decode(buf));
            }
            return new KnowledgeDataPayload(map);
        }

        @Override
        public void encode(ByteBuf buf, KnowledgeDataPayload payload) {
            ByteBufCodecs.VAR_INT.encode(buf, payload.entries.size());
            for (var entry : payload.entries.entrySet()) {
                ByteBufCodecs.STRING_UTF8.encode(buf, entry.getKey());
                ByteBufCodecs.VAR_LONG.encode(buf, entry.getValue());
            }
        }
    };

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(KnowledgeDataPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() != null) {
                com.skd.equivalentlegacy.gui.TransmutationScreen.cacheKnowledgeData(payload.entries());
            }
        });
    }
}
