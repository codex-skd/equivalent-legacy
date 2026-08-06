package com.skd.equivalentlegacy.api.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * Fired when a player newly learns an item in their transmutation knowledge set.
 * Can be cancelled to prevent the learn.
 */
public class EMCDiscoveryEvent extends Event implements ICancellableEvent {
    private final ServerPlayer player;
    private final ItemStack discovered;
    private final long emcValue;

    public EMCDiscoveryEvent(ServerPlayer player, ItemStack discovered, long emcValue) {
        this.player = player;
        this.discovered = discovered;
        this.emcValue = emcValue;
    }

    public ServerPlayer getPlayer() { return player; }
    public ItemStack getDiscovered() { return discovered; }
    public long getEmcValue() { return emcValue; }
}