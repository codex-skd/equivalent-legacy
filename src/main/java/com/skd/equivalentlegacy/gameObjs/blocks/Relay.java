package com.skd.equivalentlegacy.gameObjs.blocks;

import java.util.List;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.EnumRelayTier;
import com.skd.equivalentlegacy.gameObjs.block_entities.RelayMK1BlockEntity;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.utils.EMCHelper;
import com.skd.equivalentlegacy.utils.MathUtils;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Relay extends BlockDirection implements PEEntityBlock<RelayMK1BlockEntity> {

	private final EnumRelayTier tier;

	public Relay(EnumRelayTier tier, Properties props) {
		super(props);
		this.tier = tier;
	}

	public EnumRelayTier getTier() {
		return tier;
	}

	@NotNull
	@Override
	@Deprecated
	protected InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult rtr) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		RelayMK1BlockEntity relay = WorldHelper.getBlockEntity(RelayMK1BlockEntity.class, level, pos, true);
		if (relay != null) {
			player.openMenu(relay, pos);
		}
		return InteractionResult.CONSUME;
	}

	public void addTooltip(ItemStack stack, List<Component> tooltip) {
		if (EquivalentLegacyConfig.client.statToolTips.get()) {
			tooltip.add(PELang.EMC_MAX_OUTPUT_RATE.translateColored(ChatFormatting.DARK_PURPLE, ChatFormatting.BLUE, EMCHelper.formatEmc(tier.getChargeRate())));
			tooltip.add(PELang.EMC_MAX_STORAGE.translateColored(ChatFormatting.DARK_PURPLE, ChatFormatting.BLUE, EMCHelper.formatEmc(tier.getStorage())));
		}
	}

	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends RelayMK1BlockEntity> getType() {
		return switch (tier) {
			case MK1 -> PEBlockEntityTypes.RELAY;
			case MK2 -> PEBlockEntityTypes.RELAY_MK2;
			case MK3 -> PEBlockEntityTypes.RELAY_MK3;
		};
	}

	@Override
	@Deprecated
	public boolean triggerEvent(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int id, int param) {
		super.triggerEvent(state, level, pos, id, param);
		return triggerBlockEntityEvent(state, level, pos, id, param);
	}

	//onBlockStateChange fires only after LevelChunk#setBlockState has already removed this block's block entity,
	// so a drop from there always finds it gone. Block#onRemove runs while the block entity is still present,
	// which is where 26.2 relied on RelayMK1BlockEntity#preRemoveSideEffects (a hook 1.21.1 does not have).
	@Override
	@Deprecated
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof RelayMK1BlockEntity relay) {
			relay.dropContentsOnRemoval(level, pos);
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	@Deprecated
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Deprecated
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Direction direction) {
		RelayMK1BlockEntity relay = WorldHelper.getBlockEntity(RelayMK1BlockEntity.class, level, pos, true);
		if (relay == null) {
			return 0;
		}
		return MathUtils.scaleToRedstone(relay.getStoredEmc(), relay.getMaximumEmc());
	}
}