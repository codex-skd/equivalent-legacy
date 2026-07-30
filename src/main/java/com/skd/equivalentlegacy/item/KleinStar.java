package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.ChatFormatting;
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

public class KleinStar extends Item {
    private final KleinStarTier tier;

    public KleinStar(Properties properties, KleinStarTier tier) {
        super(properties.stacksTo(1));
        this.tier = tier;
    }

    public KleinStarTier getTier() {
        return tier;
    }

    public long getStoredEmc(ItemStack stack) {
        return stack.getOrDefault(EquivalentLegacyDataComponents.STORED_EMC, 0L);
    }

    public long getMaxEmc() {
        return tier.getMaxEmc();
    }

    public long getSpace(ItemStack stack) {
        return getMaxEmc() - getStoredEmc(stack);
    }

    public long insertEmc(ItemStack stack, long amount, boolean simulate) {
        long stored = getStoredEmc(stack);
        long canInsert = Math.min(amount, getMaxEmc() - stored);
        if (!simulate && canInsert > 0) {
            stack.set(EquivalentLegacyDataComponents.STORED_EMC, stored + canInsert);
        }
        return canInsert;
    }

    public long extractEmc(ItemStack stack, long amount, boolean simulate) {
        long stored = getStoredEmc(stack);
        long canExtract = Math.min(amount, stored);
        if (!simulate && canExtract > 0) {
            stack.set(EquivalentLegacyDataComponents.STORED_EMC, stored - canExtract);
        }
        return canExtract;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }
        PlayerKnowledge knowledge = PlayerKnowledge.of(player);
        long stored = getStoredEmc(stack);

        if (player.isShiftKeyDown()) {
            long extracted = extractEmc(stack, Math.min(10000L, stored), false);
            if (extracted > 0) {
                knowledge.addEmc(extracted);
            }
        } else {
            long playerEmc = knowledge.getEmc();
            if (playerEmc > 0) {
                long toInsert = Math.min(playerEmc, getSpace(stack));
                toInsert = Math.min(toInsert, 10000L);
                if (toInsert > 0) {
                    knowledge.subtractEmc(toInsert);
                    insertEmc(stack, toInsert, false);
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getStoredEmc(stack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        long stored = getStoredEmc(stack);
        if (stored == 0) return 0;
        return Math.round((float) stored / getMaxEmc() * 13f);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        long stored = getStoredEmc(stack);
        long max = getMaxEmc();
        float ratio = max > 0 ? (float) stored / max : 0f;
        int r = Math.round((1f - ratio) * 255f);
        int g = Math.round(ratio * 255f);
        return (r << 16) | (g << 8) | 0x33;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        long stored = getStoredEmc(stack);
        long max = getMaxEmc();
        tooltip.accept(Component.translatable("tooltip.equivalent_legacy.stored_emc", stored, max)
                .withStyle(ChatFormatting.GOLD));
    }
}
