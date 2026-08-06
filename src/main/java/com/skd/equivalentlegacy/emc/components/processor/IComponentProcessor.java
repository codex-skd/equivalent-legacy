package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import net.minecraft.world.item.ItemStack;

/**
 * Base interface for data component processors.
 *
 * <p>A processor computes the additive EMC contribution (delta) of the data components it
 * handles for the given stack. The contribution is added to the base (component-less) EMC
 * value of the stack by {@link com.skd.equivalentlegacy.emc.components.DataComponentMapper}.
 * Throwing an {@link ArithmeticException} signals that the stack has no valid EMC value.</p>
 */
public interface IComponentProcessor {

	/**
	 * Computes the EMC delta contributed by this processor for the given stack.
	 *
	 * @param stack   the stack being evaluated
	 * @param handler the mapping handler used to query EMC values of nested items
	 * @return additive EMC contribution (may be negative, e.g. for damage)
	 * @throws ArithmeticException if the stack has no valid EMC value due to its components
	 */
	long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler);

	default boolean isAvailable() {
		return true;
	}

	default String getName() {
		return getClass().getSimpleName();
	}
}
