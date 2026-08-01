package com.skd.equivalentlegacy.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public abstract class ChargableItem extends Item {
    public static final int MAX_CHARGE = 3;

    public ChargableItem(Properties properties) {
        super(properties);
    }

    public int getCharge(ItemStack stack) {
        return stack.getOrDefault(EquivalentLegacyDataComponents.TOOL_CHARGE, 0);
    }

    public void setCharge(ItemStack stack, int charge) {
        stack.set(EquivalentLegacyDataComponents.TOOL_CHARGE, charge);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                int next = (getCharge(stack) + 1) % (MAX_CHARGE + 1);
                setCharge(stack, next);
                player.sendSystemMessage(Component.literal("Charge: " + next));
            }
            return InteractionResult.CONSUME;
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.literal("Charge: " + getCharge(stack)));
    }
}
