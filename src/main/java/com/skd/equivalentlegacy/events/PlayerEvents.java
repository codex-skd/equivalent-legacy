package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.config.ServerConfig;
import com.skd.equivalentlegacy.item.PhilosophersStone;
import com.skd.equivalentlegacy.item.TransmutationStone;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import com.skd.equivalentlegacy.world_transmutation.WorldTransmutationManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public final class PlayerEvents {
    private PlayerEvents() {}

    @SubscribeEvent
    static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerKnowledge.of(serverPlayer).sync(serverPlayer);
            EquivalentLegacy.LOGGER.debug("Synced knowledge for player {}", serverPlayer.getName().getString());
        }
    }

    @SubscribeEvent
    static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerKnowledge.of(serverPlayer).sync(serverPlayer);
        }
    }

    @SubscribeEvent
    static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerKnowledge.of(serverPlayer).sync(serverPlayer);
        }
    }

    @SubscribeEvent
    static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;
        if (!(stack.getItem() instanceof PhilosophersStone)
                && !(stack.getItem() instanceof TransmutationStone)) {
            return;
        }
        if (!ServerConfig.TRANSMUTATION_ENABLED.get()) return;
        event.setCancellationResult(InteractionResult.CONSUME);
        event.setCanceled(true);
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        BlockPos pos = event.getPos();
        var sourceState = event.getLevel().getBlockState(pos);
        var target = WorldTransmutationManager.getTransmutationTarget(sourceState.getBlock());
        if (target == null) return;
        WorldTransmutationManager.transmute(player, pos, target);
    }
}
