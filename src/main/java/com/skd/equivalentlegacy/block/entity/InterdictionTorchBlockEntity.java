package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class InterdictionTorchBlockEntity extends BlockEntity {
    private static final double RADIUS = 4.0;
    private static final double FORCE = 0.15;

    public InterdictionTorchBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.INTERDICTION_TORCH.get(), pos, state);
    }

    public static BlockEntityTicker<InterdictionTorchBlockEntity> ticker() {
        return (level, pos, state, be) -> tick(be);
    }

    private static void tick(InterdictionTorchBlockEntity be) {
        Level level = be.getLevel();
        if (level == null || level.isClientSide()) return;
        AABB area = new AABB(be.worldPosition).inflate(RADIUS);
        Vec3 center = Vec3.atCenterOf(be.worldPosition);
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity instanceof Player player && player.isCreative()) continue;
            Vec3 away = entity.position().subtract(center).normalize();
            entity.setDeltaMovement(entity.getDeltaMovement().add(away.scale(FORCE)));
        }
    }
}
