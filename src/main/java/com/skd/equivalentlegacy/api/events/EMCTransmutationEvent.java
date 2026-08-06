package com.skd.equivalentlegacy.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

/**
 * Fired before and after an item-to-item transmutation is performed (transmutation table / tablet,
 * philosopher's stone GUI, world transmutation). {@link Result#ALLOW} permits and consumes EMC,
 * {@link Result#DENY} cancels.
 */
public class EMCTransmutationEvent extends Event {
    private final ServerPlayer player;
    private final ItemStack source;
    private final ItemStack target;
    private final long cost;
    private final boolean before;

    public EMCTransmutationEvent(ServerPlayer player, ItemStack source, ItemStack target, long cost, boolean before) {
        this.player = player;
        this.source = source;
        this.target = target;
        this.cost = cost;
        this.before = before;
    }

    public ServerPlayer getPlayer() { return player; }
    public ItemStack getSource() { return source; }
    public ItemStack getTarget() { return target; }
    public long getCost() { return cost; }
    public boolean isBefore() { return before; }
}