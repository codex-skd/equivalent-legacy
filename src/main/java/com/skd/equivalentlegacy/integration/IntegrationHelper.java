package com.skd.equivalentlegacy.integration;

import com.skd.equivalentlegacy.integration.regaliaslotsapi.CurioItemCapability;
import com.skd.equivalentlegacy.utils.ItemCapabilityHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jetbrains.annotations.Nullable;

public class IntegrationHelper {

	public static final String CURIO_MODID = "regalia_slots_api";
	public static final String EMI_MODID = "emi";
	public static final String TOP_MODID = "theoneprobe";

	@Nullable
	private static EntityCapability<ResourceHandler<ItemResource>, Void> curioItemHandlerCapability;

	/**
	 * @return The player's Regalia Slots API inventory as a legacy {@link IItemHandler}, or null if Regalia Slots API is not loaded.
	 */
	@Nullable
	public static IItemHandler getCurioItemHandler(Player player) {
		if (!ModList.get().isLoaded(CURIO_MODID)) {
			return null;
		}
		EntityCapability<ResourceHandler<ItemResource>, Void> capability = getCurioItemHandlerCapability();
		if (capability == null) {
			return null;
		}
		return ItemCapabilityHelper.of(player.getCapability(capability));
	}

	/**
	 * Checks whether the given stack is currently equipped in one of the player's Regalia Slots (curio) slots. If the Regalia Slots API is not loaded there are no curio
	 * slots to equip the stack in, so this returns true to keep the legacy behaviour.
	 *
	 * @param player The player wearing the slots
	 * @param stack  The stack to look for
	 *
	 * @return True if the stack is equipped in a curio slot, or if the curio system is unavailable
	 */
	public static boolean isEquippedInCurio(Player player, ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		IItemHandler handler = getCurioItemHandler(player);
		if (handler == null) {
			return true;
		}
		for (int i = 0, slots = handler.getSlots(); i < slots; i++) {
			ItemStack equipped = handler.getStackInSlot(i);
			if (!equipped.isEmpty() && ItemStack.isSameItemSameComponents(equipped, stack)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	private static EntityCapability<ResourceHandler<ItemResource>, Void> getCurioItemHandlerCapability() {
		if (curioItemHandlerCapability == null) {
			try {
				Class<?> clazz = Class.forName("com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability");
				curioItemHandlerCapability = (EntityCapability<ResourceHandler<ItemResource>, Void>) clazz.getField("ITEM_HANDLER").get(null);
			} catch (ReflectiveOperationException e) {
				return null;
			}
		}
		return curioItemHandlerCapability;
	}

	public static void sendIMCMessages(InterModEnqueueEvent event) {
		ModList modList = ModList.get();
		if (modList.isLoaded(TOP_MODID)) {
			invokeOptionalIntegration("com.skd.equivalentlegacy.integration.top.TOPIntegration", "sendIMC", event);
		}
	}

	public static void registerCuriosCapability(RegisterCapabilitiesEvent event, Item item) {
		if (ModList.get().isLoaded(CURIO_MODID)) {
			CurioItemCapability.register(event, item);
		}
	}

	private static void invokeOptionalIntegration(String className, String methodName, Object... args) {
		try {
			Class<?> clazz = Class.forName(className);
			Class<?>[] paramTypes = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				paramTypes[i] = args[i].getClass();
			}
			clazz.getMethod(methodName, paramTypes).invoke(null, args);
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException("Failed to invoke optional integration " + className + "#" + methodName, e);
		}
	}
}
