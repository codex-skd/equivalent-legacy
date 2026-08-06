package com.skd.equivalentlegacy.world_transmutation;

import com.skd.equivalentlegacy.api.events.WorldTransmutationEvent;
import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

/**
 * Central gate for "transmute this block to that block in the world" workflow. Calls go through here
 * so policy, EMC economy, cooldowns, blacklist/whitelist and event firing are centralized.
 */
public final class WorldTransmutationManager {
    private static final Map<Block, Block> REGISTRY = new HashMap<>();
    private static final java.util.Set<Block> BLACKLIST = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());

    private WorldTransmutationManager() {}

    public static void register(Block from, Block to) {
        if (from == null || to == null || from == Blocks.AIR || to == Blocks.AIR) return;
        REGISTRY.put(from, to);
        BLACKLIST.remove(from);
    }

    public static void blacklist(Block block) {
        BLACKLIST.add(block);
        REGISTRY.remove(block);
    }

    public static boolean canTransmute(Block from, Block to) {
        if (from == null || to == null) return false;
        if (BLACKLIST.contains(from)) return false;
        Block mapped = REGISTRY.get(from);
        return mapped != null && mapped == to;
    }

    public static Block getTransmutationTarget(Block from) {
        return REGISTRY.get(from);
    }

    public static long getTransmutationCost(Block from, Block to) {
        if (!canTransmute(from, to)) return 0L;
        long fromEmc = emcOfBlockItem(from);
        long toEmc = emcOfBlockItem(to);
        return Math.max(0L, toEmc - fromEmc);
    }

    public static boolean transmute(ServerPlayer player, BlockPos pos, Block target) {
        if (player == null || pos == null || target == null) return false;
        if (player.level().isClientSide()) return false;
        BlockState sourceState = player.level().getBlockState(pos);
        Block source = sourceState.getBlock();
        if (!canTransmute(source, target)) return false;
        long cost = getTransmutationCost(source, target);
        if (cost > 0) {
            PlayerKnowledge knowledge = PlayerKnowledge.of(player);
            if (knowledge.getEmc() < cost) return false;
        }
        WorldTransmutationEvent event = new WorldTransmutationEvent(player, pos, sourceState, target.defaultBlockState(), cost);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return false;

        if (cost > 0) {
            PlayerKnowledge.of(player).subtractEmc(cost);
            PlayerKnowledge.of(player).syncEmc(player);
        }
        BlockState targetState = target.defaultBlockState();
        boolean ok = player.level().setBlock(pos, targetState, 3);
        if (ok) {
            player.level().levelEvent(2001, pos, Block.getId(sourceState));
        }
        return ok;
    }

    private static long emcOfBlockItem(Block block) {
        Item item = block.asItem();
        if (item == null || item == net.minecraft.world.item.Items.AIR) return 0L;
        return EMCHelper.getEMC(NSSItem.createItem(new ItemStack(item)));
    }
}