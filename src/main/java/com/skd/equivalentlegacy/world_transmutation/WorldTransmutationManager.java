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

    public static boolean isTransmutable(Block from) {
        return from != null && REGISTRY.containsKey(from);
    }

    public static TransmutationResult getTransmutation(Block from) {
        if (from == null) return null;
        Block target = REGISTRY.get(from);
        if (target == null) return null;
        return new TransmutationResult(target, getTransmutationCost(from, target));
    }

    public static Map<Block, TransmutationResult> getTransmutationMap() {
        Map<Block, TransmutationResult> result = new HashMap<>();
        for (Map.Entry<Block, Block> entry : REGISTRY.entrySet()) {
            result.put(entry.getKey(), new TransmutationResult(entry.getValue(), getTransmutationCost(entry.getKey(), entry.getValue())));
        }
        return Map.copyOf(result);
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

    /**
     * Registers the built-in vanilla transmutation recipes. Called once at common setup after the EMC
     * values are initialized. Every entry is a directed {@code from → to} mapping consumed by
     * {@link #transmute(ServerPlayer, BlockPos, Block)} and exposed to JEI/WTHIT.
     */
    public static void registerDefaultTransmutations() {
        registerPair(Blocks.COBBLESTONE, Blocks.STONE);
        registerPair(Blocks.STONE, Blocks.GRANITE);
        registerPair(Blocks.GRANITE, Blocks.DIORITE);
        registerPair(Blocks.DIORITE, Blocks.ANDESITE);
        registerPair(Blocks.ANDESITE, Blocks.CALCITE);
        registerPair(Blocks.CALCITE, Blocks.TUFF);
        registerPair(Blocks.TUFF, Blocks.DEEPSLATE);
        registerPair(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
        registerPair(Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE);
        registerPair(Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);
        registerPair(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES);
        registerPair(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
        registerPair(Blocks.STONE, Blocks.STONE_BRICKS);
        registerPair(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
        registerPair(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        registerPair(Blocks.STONE, Blocks.SMOOTH_STONE);

        registerPair(Blocks.DIRT, Blocks.GRASS_BLOCK);
        registerPair(Blocks.DIRT, Blocks.COARSE_DIRT);
        registerPair(Blocks.DIRT, Blocks.PODZOL);
        registerPair(Blocks.DIRT, Blocks.MYCELIUM);
        registerPair(Blocks.DIRT, Blocks.ROOTED_DIRT);
        registerPair(Blocks.DIRT, Blocks.MUD);
        registerPair(Blocks.SAND, Blocks.RED_SAND);
        registerPair(Blocks.SAND, Blocks.GRAVEL);
        registerPair(Blocks.GRAVEL, Blocks.DIRT);
        registerPair(Blocks.GRAVEL, Blocks.COBBLESTONE);
        registerPair(Blocks.CLAY, Blocks.DIRT);
        registerPair(Blocks.SAND, Blocks.SANDSTONE);
        registerPair(Blocks.SANDSTONE, Blocks.SMOOTH_SANDSTONE);
        registerPair(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE);
        registerPair(Blocks.RED_SAND, Blocks.RED_SANDSTONE);
        registerPair(Blocks.RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
        registerPair(Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE);

        registerPair(Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM);
        registerPair(Blocks.NETHERRACK, Blocks.WARPED_NYLIUM);
        registerPair(Blocks.NETHERRACK, Blocks.SOUL_SAND);
        registerPair(Blocks.SOUL_SAND, Blocks.SOUL_SOIL);
        registerPair(Blocks.NETHERRACK, Blocks.BLACKSTONE);
        registerPair(Blocks.BLACKSTONE, Blocks.BASALT);
        registerPair(Blocks.BASALT, Blocks.SMOOTH_BASALT);
        registerPair(Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE);
        registerPair(Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS);
        registerPair(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        registerPair(Blocks.NETHERRACK, Blocks.NETHER_BRICKS);
        registerPair(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        registerPair(Blocks.BASALT, Blocks.MAGMA_BLOCK);

        registerPair(Blocks.END_STONE, Blocks.END_STONE_BRICKS);
        registerPair(Blocks.END_STONE, Blocks.PURPUR_BLOCK);
        registerPair(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR);
        registerPair(Blocks.PURPUR_BLOCK, Blocks.CHORUS_PLANT);

        registerPair(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);

        registerPair(Blocks.OAK_LOG, Blocks.SPRUCE_LOG);
        registerPair(Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG);
        registerPair(Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG);
        registerPair(Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG);
        registerPair(Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG);
        registerPair(Blocks.DARK_OAK_LOG, Blocks.MANGROVE_LOG);
        registerPair(Blocks.MANGROVE_LOG, Blocks.CHERRY_LOG);
        registerPair(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS);
        registerPair(Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS);
        registerPair(Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS);
        registerPair(Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS);
        registerPair(Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS);
        registerPair(Blocks.DARK_OAK_PLANKS, Blocks.MANGROVE_PLANKS);
        registerPair(Blocks.MANGROVE_PLANKS, Blocks.CHERRY_PLANKS);

        registerPair(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE);
        registerPair(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE);
        registerPair(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE);
        registerPair(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);
        registerPair(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
        registerPair(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE);
        registerPair(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
        registerPair(Blocks.NETHER_QUARTZ_ORE, Blocks.NETHER_GOLD_ORE);
    }

    /** Registers both directions of a transmutation, so the pair is reversible in JEI. */
    private static void registerPair(Block a, Block b) {
        register(a, b);
        register(b, a);
    }
}