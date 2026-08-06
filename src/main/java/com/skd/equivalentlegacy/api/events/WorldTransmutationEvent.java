package com.skd.equivalentlegacy.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * Fired when a world transmutation is requested via right-click with the Philosopher's Stone /
 * Transmutation Stone. Listeners can veto by cancelling the event ({@code setCanceled(true)}).
 */
public class WorldTransmutationEvent extends Event implements ICancellableEvent {
    private final ServerPlayer player;
    private final BlockPos pos;
    private final BlockState source;
    private final BlockState target;
    private final long cost;

    public WorldTransmutationEvent(ServerPlayer player, BlockPos pos, BlockState source, BlockState target, long cost) {
        this.player = player;
        this.pos = pos;
        this.source = source;
        this.target = target;
        this.cost = cost;
    }

    public ServerPlayer getPlayer() { return player; }
    public BlockPos getPos() { return pos; }
    public BlockState getSourceState() { return source; }
    public BlockState getTargetState() { return target; }
    public long getCost() { return cost; }
}