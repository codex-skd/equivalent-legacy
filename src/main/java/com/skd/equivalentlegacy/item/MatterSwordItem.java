package com.skd.equivalentlegacy.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MatterSwordItem extends ChargableItem {
    private static final float AOE_DAMAGE = 4.0F;

    public MatterSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        if (!(attacker instanceof Player player) || !(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        int radius = getCharge(stack) + 1;
        for (LivingEntity entity : serverLevel.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius))) {
            if (entity == attacker || entity == target) continue;
            entity.hurtServer(serverLevel, serverLevel.damageSources().playerAttack(player), AOE_DAMAGE);
        }
    }
}
