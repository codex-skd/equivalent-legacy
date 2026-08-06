package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.mob_farming.MobFarmingManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SpawnerControlItem extends Item {

    public SpawnerControlItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onStopUsing(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int count) {
        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        HitResult hitResult = player.pick(16.0D, 0.0F, false);
        if (hitResult.getType() != HitResult.Type.BLOCK) return;

        BlockHitResult blockHit = (BlockHitResult) hitResult;
        var blockEntity = player.level().getBlockEntity(blockHit.getBlockPos());

        if (blockEntity != null) {
            if (player.isShiftKeyDown()) {
                MobFarmingManager.unregisterSpawner(blockHit.getBlockPos());
            } else {
                MobFarmingManager.registerSpawner(blockHit.getBlockPos(), blockEntity);
            }
        }
    }
}
