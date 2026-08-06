package com.skd.equivalentlegacy.api;

/**
 * Implemented by anything that stores EMC and supports add/remove operations: pedestals, collectors,
 * relay conduits, players' transmutation pool, klein stars, etc.
 */
public interface IEMCStorage {
    long getStoredEmc();

    long getMaximumEmc();

    long receiveEmc(long amount, boolean simulate);

    long extractEmc(long amount, boolean simulate);
}