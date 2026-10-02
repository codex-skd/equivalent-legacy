package com.skd.equivalentlegacy.gameObjs.items.rings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.skd.equivalentlegacy.api.block_entity.IDMPedestal;
import com.skd.equivalentlegacy.api.capabilities.item.IPedestalItem;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.items.ICapabilityAware;
import com.skd.equivalentlegacy.gameObjs.registries.PEDataComponentTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PESoundEvents;
import com.skd.equivalentlegacy.integration.IntegrationHelper;
import com.skd.equivalentlegacy.utils.MathUtils;
import com.skd.equivalentlegacy.utils.PlayerHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LifeStone extends PEToggleItem implements IPedestalItem, ICapabilityAware {

	public LifeStone(Properties props) {
		super(props.component(PEDataComponentTypes.STORED_EMC, 0L));
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, display, tooltip, flags);
		tooltip.accept(PELang.TOOLTIP_LIFE_STONE.translate());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
		super.inventoryTick(stack, level, entity, slot);
		if (level.isClientSide() || !hotBarOrOffHand(slot) || !(entity instanceof Player player)) {
			return;
		}
		if (stack.getOrDefault(PEDataComponentTypes.ACTIVE, false)) {
			if (consumeFuel(player, stack, 2 * 64, false)) {
				if (PlayerHelper.checkFeedCooldown(player)) {
					level.playSound(null, player.getX(), player.getY(), player.getZ(), PESoundEvents.HEAL.get(), SoundSource.PLAYERS, 1, 1);
					player.getFoodData().eat(2, 10);
					player.gameEvent(GameEvent.EAT);
					removeEmc(stack, 64);
				}
				if (PlayerHelper.checkHealCooldown(player)) {
					level.playSound(null, player.getX(), player.getY(), player.getZ(), PESoundEvents.HEAL.get(), SoundSource.PLAYERS, 1, 1);
					player.heal(2.0F);
					removeEmc(stack, 64);
				}
			} else {
				stack.set(PEDataComponentTypes.ACTIVE, false);
			}
		}
	}

	@Override
	public <PEDESTAL extends BlockEntity & IDMPedestal> boolean updateInPedestal(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull PEDESTAL pedestal) {
		if (!level.isClientSide() && EquivalentLegacyConfig.server.cooldown.pedestal.life.get() != -1) {
			if (pedestal.getActivityCooldown() == 0) {
				for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, pedestal.getEffectBounds())) {
					if (player.getHealth() < player.getMaxHealth()) {
						level.playSound(null, player.getX(), player.getY(), player.getZ(), PESoundEvents.HEAL.get(), SoundSource.BLOCKS, 1, 1);
						player.heal(1.0F); // 1/2 heart
					}
					if (player.getFoodData().needsFood()) {
						level.playSound(null, player.getX(), player.getY(), player.getZ(), PESoundEvents.HEAL.get(), SoundSource.BLOCKS, 1, 1);
						player.getFoodData().eat(1, 1); // 1/2 shank
						player.gameEvent(GameEvent.EAT);
					}
				}
				pedestal.setActivityCooldown(level, pos, EquivalentLegacyConfig.server.cooldown.pedestal.life.get());
			} else {
				pedestal.decrementActivityCooldown(level, pos);
			}
		}
		return false;
	}

	@NotNull
	@Override
	public List<Component> getPedestalDescription(float tickRate) {
		List<Component> list = new ArrayList<>();
		if (EquivalentLegacyConfig.server.cooldown.pedestal.life.get() != -1) {
			list.add(PELang.PEDESTAL_LIFE_STONE_1.translateColored(ChatFormatting.BLUE));
			list.add(PELang.PEDESTAL_LIFE_STONE_2.translateColored(ChatFormatting.BLUE, MathUtils.tickToSecFormatted(EquivalentLegacyConfig.server.cooldown.pedestal.life.get(), tickRate)));
		}
		return list;
	}

	@Override
	public void attachCapabilities(RegisterCapabilitiesEvent event) {
		IntegrationHelper.registerCuriosCapability(event, this);
	}
}