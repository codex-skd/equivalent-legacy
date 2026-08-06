package com.skd.equivalentlegacy.api;

import net.minecraft.world.level.block.state.BlockState;

/**
 * Marks a block (or its block entity) as being allowed (or disallowed) for world transmutation.
 * Implementing this on a block-event listener / block entity lets other mods opt blocks in or out of
 * the world transmutation subsystem.
 */
public interface ITransmutationAllowed {
    boolean isTransmutationAllowed(BlockState state);
}