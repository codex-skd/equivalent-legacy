package com.skd.equivalentlegacy.gameObjs.items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import com.skd.equivalentlegacy.api.block_entity.IDMPedestal;
import com.skd.equivalentlegacy.api.capabilities.PECapabilities;
import com.skd.equivalentlegacy.api.capabilities.item.IAlchBagItem;
import com.skd.equivalentlegacy.api.capabilities.item.IAlchChestItem;
import com.skd.equivalentlegacy.api.capabilities.item.IPedestalItem;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.registries.PEDataComponentTypes;
import com.skd.equivalentlegacy.integration.IntegrationHelper;
import com.skd.equivalentlegacy.utils.BalanceHelper;
import com.skd.equivalentlegacy.utils.ItemHelper;
import com.skd.equivalentlegacy.utils.MathUtils;
import com.skd.equivalentlegacy.utils.PlayerHelper;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RepairTalisman extends ItemPE implements IAlchBagItem, IAlchChestItem, IPedestalItem, ICapabilityAware, ISelfCraftingRemainder {

	private static final BiPredicate<ItemStack, Void> CAN_REPAIR_ITEM = (stack, ignored) -> !stack.isEmpty() &&
																							stack.getCapability(PECapabilities.MODE_CHANGER_ITEM_CAPABILITY) == null &&
																							ItemHelper.isRepairableDamagedItem(stack);
	private static final BiPredicate<ItemStack, Player> CAN_REPAIR_PLAYER_ITEM =
			(stack, player) -> CAN_REPAIR_ITEM.test(stack, null) && (stack != player.getMainHandItem() || !player.swinging);

	public RepairTalisman(Properties props) {
		super(props.component(PEDataComponentTypes.COOLDOWN, (byte) 0));
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, display, tooltip, flags);
		tooltip.accept(PELang.TOOLTIP_REPAIR_1.translate());
		tooltip.accept(PELang.TOOLTIP_REPAIR_2.translate(BalanceHelper.scaleEmc(BalanceHelper.emcPerRepair())));
		if (BalanceHelper.requireCurio()) {
			tooltip.accept(PELang.TOOLTIP_REPAIR_3.translate());
		}
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
		super.inventoryTick(stack, level, entity, slot);
		if (!level.isClientSide() && entity instanceof Player player
				&& BalanceHelper.repairEnabled() && BalanceHelper.isAbilityAllowed(level)
				&& (!BalanceHelper.requireCurio() || IntegrationHelper.isEquippedInCurio(player, stack))
				&& PlayerHelper.checkCooldown(player, this, EquivalentLegacyConfig.server.cooldown.player.repair)) {
			repairAllItems(player, stack);
		}
	}

	@Override
	public <PEDESTAL extends BlockEntity & IDMPedestal> boolean updateInPedestal(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull PEDESTAL pedestal) {
		if (!level.isClientSide() && BalanceHelper.repairEnabled() && BalanceHelper.isAbilityAllowed(level)
				&& EquivalentLegacyConfig.server.cooldown.pedestal.repair.get() != -1) {
			if (pedestal.getActivityCooldown() == 0) {
				level.getEntitiesOfClass(Player.class, pedestal.getEffectBounds()).forEach(RepairTalisman::repairAllItems);
				pedestal.setActivityCooldown(level, pos, EquivalentLegacyConfig.server.cooldown.pedestal.repair.get());
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
		if (EquivalentLegacyConfig.server.cooldown.pedestal.repair.get() != -1) {
			list.add(PELang.PEDESTAL_REPAIR_TALISMAN_1.translateColored(ChatFormatting.BLUE));
			list.add(PELang.PEDESTAL_REPAIR_TALISMAN_2.translateColored(ChatFormatting.BLUE, MathUtils.tickToSecFormatted(EquivalentLegacyConfig.server.cooldown.pedestal.repair.get(), tickRate)));
		}
		return list;
	}

	@Override
	public boolean updateInAlchChest(@NotNull Level level, @NotNull BlockPos pos, @NotNull ItemStack stack) {
		if (!level.isClientSide() && BalanceHelper.repairEnabled() && BalanceHelper.includeAlchStorage() && BalanceHelper.isAbilityAllowed(level)) {
			IItemHandler inv = WorldHelper.getItemHandler(level, pos, null);
			if (inv != null) {
				return updateInHandler(inv, stack, null);
			}
		}
		return false;
	}

	@Override
	public boolean updateInAlchBag(@NotNull IItemHandler inv, @NotNull Player player, @NotNull ItemStack stack) {
		if (player.level().isClientSide() || !BalanceHelper.repairEnabled() || !BalanceHelper.includeAlchStorage() || !BalanceHelper.isAbilityAllowed(player.level())) {
			return false;
		}
		return updateInHandler(inv, stack, player);
	}

	private boolean updateInHandler(@NotNull IItemHandler inv, @NotNull ItemStack stack, @Nullable Player player) {
		byte coolDown = stack.getOrDefault(PEDataComponentTypes.COOLDOWN, (byte) 0);
		if (coolDown > 0) {
			stack.set(PEDataComponentTypes.COOLDOWN, (byte) (coolDown - 1));
			return true;
		}
		long cost = player == null ? 0L : BalanceHelper.scaleEmc(BalanceHelper.emcPerRepair());
		if (repairAllItems(inv, null, CAN_REPAIR_ITEM, player, stack, cost)) {
			stack.set(PEDataComponentTypes.COOLDOWN, (byte) 19);
			return true;
		}
		return false;
	}

	@Override
	public void attachCapabilities(RegisterCapabilitiesEvent event) {
		IntegrationHelper.registerCuriosCapability(event, this);
	}

	private static void repairAllItems(Player player, ItemStack talisman) {
		long cost = BalanceHelper.scaleEmc(BalanceHelper.emcPerRepair());
		repairPlayerInventory(player.getInventory(), player, talisman, cost);
		if (BalanceHelper.includeCurios()) {
			IItemHandler curios = IntegrationHelper.getCurioItemHandler(player);
			if (curios != null) {
				repairAllItems(curios, player, CAN_REPAIR_PLAYER_ITEM, player, talisman, cost);
			}
		}
	}

	private static void repairAllItems(Player player) {
		repairPlayerInventory(player.getInventory(), player, null, 0L);
		if (BalanceHelper.includeCurios()) {
			IItemHandler curios = IntegrationHelper.getCurioItemHandler(player);
			if (curios != null) {
				repairAllItems(curios, player, CAN_REPAIR_PLAYER_ITEM, player, null, 0L);
			}
		}
	}

	/**
	 * Charges the given cost from the player (or the talisman's internal buffer) to repair a single durability point. Returns true when the repair is affordable (or when
	 * there is no cost to pay, such as while the balance system is disabled).
	 */
	private static boolean charge(Player player, @Nullable ItemStack talisman, long cost) {
		if (talisman == null || cost <= 0) {
			return true;
		}
		if (consumeFuel(player, talisman, cost, false)) {
			removeEmc(talisman, cost);
			return true;
		}
		return false;
	}

	private static boolean repairPlayerInventory(Inventory inventory, Player player, @Nullable ItemStack talisman, long cost) {
		boolean hasAction = false;
		for (int i = 0, slots = inventory.getContainerSize(); i < slots; i++) {
			ItemStack stack = inventory.getItem(i);
			if (CAN_REPAIR_PLAYER_ITEM.test(stack, player) && charge(player, talisman, cost)) {
				stack.setDamageValue(stack.getDamageValue() - 1);
				hasAction = true;
			}
		}
		return hasAction;
	}

	private static <DATA> boolean repairAllItems(@Nullable IItemHandler inv, DATA data, BiPredicate<ItemStack, DATA> canRepairStack,
			@Nullable Player player, @Nullable ItemStack talisman, long cost) {
		if (inv == null) {
			return false;
		}
		boolean hasAction = false;
		for (int i = 0, slots = inv.getSlots(); i < slots; i++) {
			ItemStack invStack = inv.getStackInSlot(i);
			if (!canRepairStack.test(invStack, data) || !charge(player, talisman, cost)) {
				continue;
			}
			ItemStack repaired = invStack.copy();
			repaired.setDamageValue(repaired.getDamageValue() - 1);
			if (inv instanceof IItemHandlerModifiable modifiable) {
				modifiable.setStackInSlot(i, repaired);
				hasAction = true;
			} else if (inv.isItemValid(i, repaired)) {
				// Non-modifiable handlers (e.g. the legacy bridge over Regalia Slots API's
				// ResourceHandler) hand out fresh ItemStack copies from getStackInSlot, so mutating
				// invStack does nothing. Round-trip through extract/insert instead, which mutate the
				// backing handler. The isItemValid guard above guarantees the repaired stack can go
				// back into the same slot, so this cannot lose the item.
				ItemStack extracted = inv.extractItem(i, invStack.getCount(), false);
				if (!extracted.isEmpty()) {
					extracted.setDamageValue(extracted.getDamageValue() - 1);
					ItemStack leftover = inv.insertItem(i, extracted, false);
					if (leftover.isEmpty()) {
						hasAction = true;
					} else {
						// Should not happen for a freshly emptied slot; put it back untouched.
						inv.insertItem(i, leftover, false);
					}
				}
			}
		}
		return hasAction;
	}
}