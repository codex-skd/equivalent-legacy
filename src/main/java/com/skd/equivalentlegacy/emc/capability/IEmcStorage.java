package com.skd.equivalentlegacy.emc.capability;

/**
 * Capability for entities/items that can store EMC energy.
 *
 * Used by Klein Stars, Collectors, Condensers, and other EMC-storing devices.
 */
public interface IEmcStorage {

    /**
     * Gets the current stored EMC.
     */
    long getStoredEmc();

    /**
     * Sets the stored EMC (clamped to [0, maxEmc]).
     */
    void setStoredEmc(long value);

    /**
     * Gets the maximum EMC this storage can hold.
     */
    long getMaxEmc();

    /**
     * Adds EMC, returns amount actually added (may be less than requested if storage full).
     */
    long addEmc(long amount);

    /**
     * Removes EMC, returns amount actually removed (may be less than requested if storage depleted).
     */
    long removeEmc(long amount);
}
