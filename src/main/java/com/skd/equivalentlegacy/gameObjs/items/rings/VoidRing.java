package com.skd.equivalentlegacy.gameObjs.items.rings;

import java.util.List;
import com.skd.equivalentlegacy.api.block_entity.IDMPedestal;
import com.skd.equivalentlegacy.api.capabilities.item.IExtraFunction;
import com.skd.equivalentlegacy.api.capabilities.item.IPedestalItem;
import com.skd.equivalentlegacy.gameObjs.items.GemEternalDensity;
import com.skd.equivalentlegacy.gameObjs.registries.PEItems;
import com.skd.equivalentlegacy.utils.ClientKeyHelper;
import com.skd.equivalentlegacy.utils.PEKeybind;
import com.skd.equivalentlegacy.utils.PlayerHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class VoidRing extends GemEternalDensity implements IPedestalItem, IExtraFunction {

	public VoidRing(Properties props) {
		super(props);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		tooltip.add(PELang.TOOLTIP_VOID_RING_1.translate());
		tooltip.add(PELang.TOOLTIP_VOID_RING_2.translate(ClientKeyHelper.getKeyName(PEKeybind.EXTRA_FUNCTION)));
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, level, entity, slot, selected);
		PEItems.BLACK_HOLE_BAND.get().inventoryTick(stack, level, entity, slot, selected);
	}

	@Override
	public <PEDESTAL extends BlockEntity & IDMPedestal> boolean updateInPedestal(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull PEDESTAL pedestal) {
		return PEItems.BLACK_HOLE_BAND.get().updateInPedestal(stack, level, pos, pedestal);
	}

	@NotNull
	@Override
	public List<Component> getPedestalDescription(float tickRate) {
		return PEItems.BLACK_HOLE_BAND.get().getPedestalDescription(tickRate);
	}

	@Override
	public boolean doExtraFunction(@NotNull Player player, @NotNull ItemStack stack, InteractionHand hand) {
		ItemCooldowns cooldowns = player.getCooldowns();
		if (cooldowns.isOnCooldown(stack.getItem())) {
			return false;
		}
		BlockHitResult lookingAt = PlayerHelper.getBlockLookingAt(player, 64);
		BlockPos c;
		if (lookingAt.getType() == Type.MISS) {
			c = BlockPos.containing(PlayerHelper.getLookTarget(player, 32));
		} else {
			c = lookingAt.getBlockPos();
		}
		if (!(player.level() instanceof ServerLevel serverLevel)) {
			return false;
		}
		EntityTeleportEvent event = new EntityTeleportEvent(player, c.getX(), c.getY(), c.getZ());
		if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
			if (player.isPassenger()) {
				player.stopRiding();
			}
			player.resetFallDistance();
			player.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1, 1);
			cooldowns.addCooldown(stack.getItem(), SharedConstants.TICKS_PER_SECOND / 2);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateInAlchBag(@NotNull IItemHandler inv, @NotNull Player player, @NotNull ItemStack stack) {
		// super is Gem of Eternal Density
		return super.updateInAlchBag(inv, player, stack) | PEItems.BLACK_HOLE_BAND.get().updateInAlchBag(inv, player, stack);
	}

	@Override
	public boolean updateInAlchChest(@NotNull Level level, @NotNull BlockPos pos, @NotNull ItemStack stack) {
		// super is Gem of Eternal Density
		return super.updateInAlchChest(level, pos, stack) | PEItems.BLACK_HOLE_BAND.get().updateInAlchChest(level, pos, stack);
	}
}