package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

/**
 * Public API for querying and manipulating EMC values.
 *
 * Entry point for addons, GUIs, and other systems that need to:
 * - Query EMC values for items/fluids
 * - Learn about items (for transmutation)
 * - Access custom conversions
 */
public final class EquivalentLegacyEMCAPI {

    private static EMCMappingHandler handler;

    private EquivalentLegacyEMCAPI() {}

    /**
     * Initialize the API with the EMCMappingHandler (called during mod setup).
     */
    public static void initialize(EMCMappingHandler mappingHandler) {
        handler = mappingHandler;
    }

    /**
     * Gets the EMC value of an ItemStack.
     *
     * @param stack The item to query
     * @return EMC value (0 if unknown or no value)
     */
    public static long getEMCValue(ItemStack stack) {
        if (handler == null || stack.isEmpty()) {
            return 0L;
        }
        return handler.getEMCValue(stack);
    }

    /**
     * Gets the base EMC value of a Normalized Simple Stack (for internal use).
     *
     * @param nss The normalized stack to query
     * @return EMC value (0 if unknown or no value)
     */
    public static long getBaseEMCValue(NormalizedSimpleStack nss) {
        if (handler == null || nss == null) {
            return 0L;
        }
        return handler.getBaseEMCValue(nss);
    }

    /**
     * Gets the EMCMappingHandler (advanced use only).
     *
     * @return The handler, or null if not yet initialized
     */
    @Nullable
    public static EMCMappingHandler getHandler() {
        return handler;
    }

    /**
     * Invalidates the EMC cache (call after modifying custom conversions at runtime).
     */
    public static void invalidateCache() {
        if (handler != null) {
            handler.invalidateCache();
        }
    }
}
