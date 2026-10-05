package com.skd.equivalentlegacy.gameObjs.items.rings;

import java.util.ArrayList;
import java.util.List;
import com.skd.equivalentlegacy.api.block_entity.IDMPedestal;
import com.skd.equivalentlegacy.api.capabilities.item.IPedestalItem;
import com.skd.equivalentlegacy.api.capabilities.item.IProjectileShooter;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.entity.EntityFireProjectile;
import com.skd.equivalentlegacy.gameObjs.items.ICapabilityAware;
import com.skd.equivalentlegacy.gameObjs.items.IFireProtector;
import com.skd.equivalentlegacy.gameObjs.registries.PEDataComponentTypes;
import com.skd.equivalentlegacy.integration.IntegrationHelper;
import com.skd.equivalentlegacy.utils.MathUtils;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

public class Ignition extends PEToggleItem implements IPedestalItem, IFireProtector, IProjectileShooter, ICapabilityAware {

	public Ignition(Properties props) {
		super(props.component(PEDataComponentTypes.STORED_EMC, 0L)
				.component(PEDataComponentTypes.UNPROCESSED_EMC, 0.0)
		);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		tooltip.add(PELang.TOOLTIP_IGNITION_1.translate());
		tooltip.add(PELang.TOOLTIP_IGNITION_2.translate());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, level, entity, slot, selected);
		if (level.isClientSide || !hotBarOrOffHand(slot) || !(entity instanceof Player player)) {
			return;
		}
		if (stack.getOrDefault(PEDataComponentTypes.ACTIVE, false)) {
			if (!hasEmc(player, stack, 64, true)) {
				stack.set(PEDataComponentTypes.ACTIVE, false);
			} else {
				WorldHelper.igniteNearby(level, player);
				removeEmc(stack, 0.32F);
			}
		} else {
			WorldHelper.extinguishNearby(level, player);
		}
	}

	@NotNull
	@Override
	public InteractionResult useOn(@NotNull UseOnContext ctx) {
		return WorldHelper.igniteBlock(ctx);
	}

	@Override
	public <PEDESTAL extends BlockEntity & IDMPedestal> boolean updateInPedestal(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull PEDESTAL pedestal) {
		if (!level.isClientSide && EquivalentLegacyConfig.server.cooldown.pedestal.ignition.get() != -1) {
			if (pedestal.getActivityCooldown() == 0) {
				DamageSource fire = level.damageSources().inFire();
				for (Mob living : level.getEntitiesOfClass(Mob.class, pedestal.getEffectBounds())) {
					living.hurt(fire, 3.0F);
					living.igniteForSeconds(8);
				}
				pedestal.setActivityCooldown(level, pos, EquivalentLegacyConfig.server.cooldown.pedestal.ignition.get());
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
		if (EquivalentLegacyConfig.server.cooldown.pedestal.ignition.get() != -1) {
			list.add(PELang.PEDESTAL_IGNITION_1.translateColored(ChatFormatting.BLUE));
			list.add(PELang.PEDESTAL_IGNITION_2.translateColored(ChatFormatting.BLUE, MathUtils.tickToSecFormatted(EquivalentLegacyConfig.server.cooldown.pedestal.ignition.get(), tickRate)));
		}
		return list;
	}

	@Override
	public boolean shootProjectile(@NotNull Player player, @NotNull ItemStack stack, InteractionHand hand) {
		Level level = player.level();
		if (level.isClientSide) {
			return false;
		}
		EntityFireProjectile fire = new EntityFireProjectile(player, false, level);
		fire.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 1);
		level.addFreshEntity(fire);
		return true;
	}

	@Override
	public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility action) {
		return action == ItemAbilities.FIRESTARTER_LIGHT || super.canPerformAction(stack, action);
	}

	@Override
	public void attachCapabilities(RegisterCapabilitiesEvent event) {
		IntegrationHelper.registerCuriosCapability(event, this);
	}
}