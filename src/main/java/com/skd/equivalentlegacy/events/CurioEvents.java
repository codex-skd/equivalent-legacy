package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.item.CurioItem;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public final class CurioEvents {
    private static final int EFFECT_DURATION = 220;
    private static final double RADIUS = 4.0;

    private CurioEvents() {}

    @SubscribeEvent
    static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) return;
        applyEffects(player);
        handleMagnetAndVoid(player, level);
        handleRepair(player);
    }

    private static void applyEffects(Player player) {
        for (ItemStack stack : inventoryItems(player)) {
            if (stack.getItem() instanceof CurioItem curio) {
                switch (curio.getType()) {
                    case EVERTIDE_AMULET ->
                            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, EFFECT_DURATION, 0, false, false));
                    case VOLCANITE_AMULET ->
                            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, EFFECT_DURATION, 0, false, false));
                    case LIFE_STONE ->
                            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_DURATION, 1, false, false));
                    case SOUL_STONE -> {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_DURATION, 0, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, EFFECT_DURATION, 0, false, false));
                    }
                    case BODY_STONE ->
                            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, EFFECT_DURATION, 0, false, false));
                    case MIND_STONE -> {
                        player.addEffect(new MobEffectInstance(MobEffects.SPEED, EFFECT_DURATION, 0, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, EFFECT_DURATION, 0, false, false));
                    }
                    case SWIFTWOLF_RENDING_GALE -> {
                        player.addEffect(new MobEffectInstance(MobEffects.SPEED, EFFECT_DURATION, 1, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, EFFECT_DURATION, 1, false, false));
                    }
                    case WATCH_OF_FLOWING_TIME ->
                            player.addEffect(new MobEffectInstance(MobEffects.HASTE, EFFECT_DURATION, 0, false, false));
                    default -> {
                    }
                }
            }
        }
    }

    private static void handleMagnetAndVoid(Player player, Level level) {
        boolean magnet = false;
        boolean voidRing = false;
        for (ItemStack stack : inventoryItems(player)) {
            if (stack.getItem() instanceof CurioItem curio) {
                if (curio.getType() == CurioItem.Type.BLACK_HOLE_BAND) magnet = true;
                if (curio.getType() == CurioItem.Type.VOID_RING) voidRing = true;
            }
        }
        if (!magnet && !voidRing) return;

        AABB area = new AABB(player.blockPosition()).inflate(RADIUS);
        Vec3 playerPos = player.position().add(0.0, 1.0, 0.0);
        for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, area)) {
            if (voidRing) {
                long emc = EMCHelper.getEMC(NSSItem.createItem(item.getItem()));
                if (emc > 0) {
                    PlayerKnowledge.of(player).addEmc(emc * item.getItem().getCount());
                    item.discard();
                    continue;
                }
            }
            if (magnet) {
                Vec3 delta = playerPos.subtract(item.position()).normalize().scale(0.06);
                item.setDeltaMovement(item.getDeltaMovement().add(delta));
            }
        }
    }

    private static Iterable<ItemStack> inventoryItems(Player player) {
        var inventory = player.getInventory();
        java.util.List<ItemStack> stacks = new java.util.ArrayList<>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            stacks.add(inventory.getItem(i));
        }
        return stacks;
    }

    private static void handleRepair(Player player) {
        boolean hasTalisman = false;
        for (ItemStack stack : inventoryItems(player)) {
            if (stack.getItem() instanceof CurioItem curio && curio.getType() == CurioItem.Type.REPAIR_TALISMAN) {
                hasTalisman = true;
                break;
            }
        }
        if (!hasTalisman) return;
        for (ItemStack stack : inventoryItems(player)) {
            if (stack.isDamaged()) {
                stack.setDamageValue(stack.getDamageValue() - 1);
                return;
            }
        }
    }
}
