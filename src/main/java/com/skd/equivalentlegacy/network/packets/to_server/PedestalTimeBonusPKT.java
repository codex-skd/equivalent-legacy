package com.skd.equivalentlegacy.network.packets.to_server;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.api.capabilities.PECapabilities;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.block_entities.DMPedestalBlockEntity;
import com.skd.equivalentlegacy.gameObjs.items.rings.TimeWatch;
import com.skd.equivalentlegacy.network.packets.IPEPacket;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PedestalTimeBonusPKT(BlockPos pos, int bonusTicks) implements IPEPacket {

	public static final CustomPacketPayload.Type<PedestalTimeBonusPKT> TYPE = new CustomPacketPayload.Type<>(ELCore.rl("pedestal_time_bonus"));
	public static final StreamCodec<RegistryFriendlyByteBuf, PedestalTimeBonusPKT> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PedestalTimeBonusPKT::pos,
			ByteBufCodecs.VAR_INT, PedestalTimeBonusPKT::bonusTicks,
			PedestalTimeBonusPKT::new
	);

	@NotNull
	@Override
	public CustomPacketPayload.Type<PedestalTimeBonusPKT> type() {
		return TYPE;
	}

	@Override
	public void handle(IPayloadContext context) {
		if (!(context.player() instanceof ServerPlayer player)) {
			return;
		}
		Level level = player.level();
		if (!level.isLoaded(pos) || player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > 64) {
			return;
		}
		DMPedestalBlockEntity pedestal = WorldHelper.getBlockEntity(DMPedestalBlockEntity.class, level, pos, true);
		if (pedestal == null) {
			return;
		}
		ItemStack stack = pedestal.getInventory().getStackInSlot(0);
		if (stack.isEmpty() || !(stack.getItem() instanceof TimeWatch) || stack.getCapability(PECapabilities.PEDESTAL_ITEM_CAPABILITY) == null) {
			return;
		}
		int max = EquivalentLegacyConfig.server.effects.timePedBonus.get();
		int clamped = Math.min(Math.max(bonusTicks, 0), max);
		pedestal.setTimeBonusTicks(level, pos, clamped);
		player.displayClientMessage(PELang.PEDESTAL_TIME_WATCH_BONUS.translate(clamped, max), true);
	}
}
