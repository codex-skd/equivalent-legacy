package com.skd.equivalentlegacy.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PESaw extends ChargableItem {
    public PESaw(Properties properties) {
        super(properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
        boolean result = super.mineBlock(stack, level, state, pos, owner);
        if (!level.isClientSide() && owner instanceof Player player) {
            int radius = Math.max(1, getCharge(stack));
            ToolAoe.breakArea(stack, level, pos, player, radius);
        }
        return result;
    }
}