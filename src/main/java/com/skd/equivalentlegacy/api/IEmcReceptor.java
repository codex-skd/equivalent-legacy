package com.skd.equivalentlegacy.api;

/**
 * Implemented by blocks / entities that pull EMC from neighbours. Used by the relay network and by
 * pedestals that require a feed-in path.
 */
public interface IEmcReceptor {
    boolean acceptsEmcFrom(net.minecraft.core.Direction side);

    long receiveEmc(long amount);
}