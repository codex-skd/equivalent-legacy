package com.skd.equivalentlegacy.api;

/**
 * Implemented by items, block entities, or attachments that expose a total EMC value to the public API.
 * <p>External mods can query this without depending on internal EMC storage classes.</p>
 */
public interface IEMCProvider {
    long getStoredEmc();

    long getMaximumEmc();
}