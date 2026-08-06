package com.skd.equivalentlegacy.world_transmutation;

import net.minecraft.world.level.block.Block;

/**
 * Immutable view of a single world-transmutation recipe: the destination block and the EMC cost
 * required to transmute a source block into it (difference between the two block items' EMC values).
 */
public record TransmutationResult(Block resultBlock, long emcCost) {}
