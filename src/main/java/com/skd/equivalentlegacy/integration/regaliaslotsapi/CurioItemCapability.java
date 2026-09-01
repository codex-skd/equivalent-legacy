package com.skd.equivalentlegacy.integration.regaliaslotsapi;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import com.skd.regaliaslotsapi.api.RegaliaSlotsApiCapability;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.type.capability.ICurio;

public record CurioItemCapability(ItemStack stack) implements ICurio {

	@Override
	public ItemStack getStack() {
		return stack;
	}

	@Override
	public void curioTick(SlotContext context) {
		if (!context.cosmetic()) {
			//Note: We act as if curios are being held by the offhand when it comes to ticking
			getStack().inventoryTick(context.entity().level(), context.entity(), 0, false);
		}
	}

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id) {
		if (!slotContext.cosmetic() && stack.getItem() instanceof IExposesCurioAttributes exposesCurioAttributes) {
			Multimap<Holder<Attribute>, AttributeModifier> attributes = LinkedHashMultimap.create();
			exposesCurioAttributes.addAttributes(attributes);
			return attributes;
		}
		return ICurio.super.getAttributeModifiers(slotContext, id);
	}

	public static void register(RegisterCapabilitiesEvent event, Item item) {
		event.registerItem(RegaliaSlotsApiCapability.ITEM, (stack, ctx) -> new CurioItemCapability(stack), item);
	}
}