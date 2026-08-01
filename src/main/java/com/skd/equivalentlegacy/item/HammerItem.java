package com.skd.equivalentlegacy.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends Item {
    private static final int AOE_RADIUS = 1;
    private static final float MAX_BREAK_SPEED = 50.0F;

    public HammerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity owner) {
        boolean result = super.mineBlock(stack, level, state, pos, owner);
        if (!level.isClientSide() && owner instanceof Player player) {
            for (BlockPos target : getAoePositions(pos, player)) {
                breakBlock(stack, level, target, player);
            }
        }
        return result;
    }

    private List<BlockPos> getAoePositions(BlockPos pos, Player player) {
        Vec3 eye = player.getEyePosition();
        double dx = eye.x - (pos.getX() + 0.5);
        double dy = eye.y - (pos.getY() + 0.5);
        double dz = eye.z - (pos.getZ() + 0.5);
        double ax = Math.abs(dx);
        double ay = Math.abs(dy);
        double az = Math.abs(dz);

        List<BlockPos> positions = new ArrayList<>();
        int r = AOE_RADIUS;
        if (ay >= ax && ay >= az) {
            for (int ox = -r; ox <= r; ox++) {
                for (int oz = -r; oz <= r; oz++) {
                    if (ox != 0 || oz != 0) positions.add(pos.offset(ox, 0, oz));
                }
            }
        } else if (ax >= ay && ax >= az) {
            for (int oy = -r; oy <= r; oy++) {
                for (int oz = -r; oz <= r; oz++) {
                    if (oy != 0 || oz != 0) positions.add(pos.offset(0, oy, oz));
                }
            }
        } else {
            for (int ox = -r; ox <= r; ox++) {
                for (int oy = -r; oy <= r; oy++) {
                    if (ox != 0 || oy != 0) positions.add(pos.offset(ox, oy, 0));
                }
            }
        }
        return positions;
    }

    private void breakBlock(ItemStack stack, Level level, BlockPos pos, Player player) {
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
