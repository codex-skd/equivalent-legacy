package com.skd.equivalentlegacy.network.packets.to_server;

import io.netty.buffer.ByteBuf;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.components.GemData;
import com.skd.equivalentlegacy.gameObjs.registries.PEDataComponentTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEItems;
import com.skd.equivalentlegacy.network.PEStreamCodecs;
import com.skd.equivalentlegacy.network.packets.IPEPacket;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record UpdateGemModePKT(InteractionHand hand, boolean mode) implements IPEPacket {

	public static final CustomPacketPayload.Type<UpdateGemModePKT> TYPE = new CustomPacketPayload.Type<>(ELCore.rl("update_gem_mode"));
	public static final StreamCodec<ByteBuf, UpdateGemModePKT> STREAM_CODEC = StreamCodec.composite(
			PEStreamCodecs.INTERACTION_HAND, UpdateGemModePKT::hand,
			ByteBufCodecs.BOOL, UpdateGemModePKT::mode,
			UpdateGemModePKT::new
	);

	@NotNull
	@Override
	public CustomPacketPayload.Type<UpdateGemModePKT> type() {
		return TYPE;
	}

	@Override
	public void handle(IPayloadContext context) {
		ItemStack stack = context.player().getItemInHand(hand);
		if (!stack.isEmpty()) {
			if (stack.is(PEItems.GEM_OF_ETERNAL_DENSITY) || stack.is(PEItems.VOID_RING)) {
				stack.update(PEDataComponentTypes.GEM_DATA, GemData.EMPTY, mode, GemData::withWhitelist);
			}
		}
	}
}