package com.skd.equivalentlegacy.emc.capability;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import java.util.Optional;

/**
 * Capability for external EMC value providers.
 *
 * Allows mods to register custom EMC values for their items without modifying Equivalent Legacy.
 */
public interface IEmcProvider {

    /**
     * Provides a custom EMC value for an NSS, if available.
     *
     * @return Optional containing EMC value, or empty if this provider doesn't know this item
     */
    Optional<Long> getEmc(NormalizedSimpleStack nss);

    /**
     * Gets provider name (for logging/debugging).
     */
    String getName();
}
