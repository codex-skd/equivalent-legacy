package com.skd.equivalentlegacy.emc.capability;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import java.util.Set;

/**
 * Capability tracking which items/recipes a player has "discovered" for transmutation.
 *
 * Used by TransmutationTable to know which items are transmutable.
 */
public interface IKnowledgeProvider {

    /**
     * Checks if player knows how to transmute an item.
     */
    boolean knows(NormalizedSimpleStack nss);

    /**
     * Teaches the player about an item (makes it transmutable).
     */
    void learn(NormalizedSimpleStack nss);

    /**
     * Gets all known items (immutable copy).
     */
    Set<NormalizedSimpleStack> getKnownItems();

    /**
     * Forget all knowledge.
     */
    void reset();
}
