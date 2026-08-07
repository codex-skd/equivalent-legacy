package com.skd.equivalentlegacy.network.payload;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.chest.ChestNetworkManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ChestLinkPayload(BlockPos pos, boolean link, boolean unlink) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ChestLinkPayload> TYPE =
            new CustomPacketPayload.Type<>(EquivalentLegacy.rl("chest_link"));

    public static final StreamCodec<ByteBuf, ChestLinkPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ChestLinkPayload::pos,
            ByteBufCodecs.BOOL, ChestLinkPayload::link,
            ByteBufCodecs.BOOL, ChestLinkPayload::unlink,
            ChestLinkPayload::new
    );

    @Override
    public CustomPacketPayload.Type<ChestLinkPayload> type() {
        return TYPE;
    }

    public static void handle(ChestLinkPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                var level = (net.minecraft.server.level.ServerLevel) player.level();
                var manager = ChestNetworkManager.getInstance();

                if (payload.unlink()) {
                    manager.removeChestFromNetwork(level, payload.pos());
                    manager.clearPendingLink(player);
                } else if (payload.link()) {
                    manager.getOrCreatePendingLink(player, level, payload.pos());
                }
            }
        });
    }
}
