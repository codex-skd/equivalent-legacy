package com.skd.equivalentlegacy.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public final class ToolAoe {
    private static final float MAX_BREAK_SPEED = 50.0F;

    private ToolAoe() {}

    public static void breakArea(ItemStack stack, Level level, BlockPos pos, Player player, int radius) {
        Vec3 eye = player.getEyePosition();
        double dx = eye.x - (pos.getX() + 0.5);
        double dy = eye.y - (pos.getY() + 0.5);
        double dz = eye.z - (pos.getZ() + 0.5);
        double ax = Math.abs(dx);
        double ay = Math.abs(dy);
        double az = Math.abs(dz);

        if (ay >= ax && ay >= az) {
            for (int ox = -radius; ox <= radius; ox++) {
                for (int oz = -radius; oz <= radius; oz++) {
                    if (ox != 0 || oz != 0) breakBlock(stack, level, pos.offset(ox, 0, oz), player);
                }
            }
        } else if (ax >= ay && ax >= az) {
            for (int oy = -radius; oy <= radius; oy++) {
                for (int oz = -radius; oz <= radius; oz++) {
                    if (oy != 0 || oz != 0) breakBlock(stack, level, pos.offset(0, oy, oz), player);
                }
            }
        } else {
            for (int ox = -radius; ox <= radius; ox++) {
                for (int oy = -radius; oy <= radius; oy++) {
                    if (ox != 0 || oy != 0) breakBlock(stack, level, pos.offset(ox, oy, 0), player);
                }
            }
        }
    }

    private static void breakBlock(ItemStack stack, Level level, BlockPos pos, Player player) {
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return;
        float destroySpeed = state.getDestroySpeed(level, pos);
        if (destroySpeed < 0.0F || destroySpeed > MAX_BREAK_SPEED) return;

        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity, player, player.getMainHandItem());
        level.levelEvent(2001, pos, Block.getId(state));
        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
    }
}
