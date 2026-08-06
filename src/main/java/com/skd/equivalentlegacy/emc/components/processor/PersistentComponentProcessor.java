package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

/**
 * Base class for processors that operate on a single data component of a known type.
 */
public abstract class PersistentComponentProcessor<TYPE> implements IComponentProcessor {

	protected abstract DataComponentType<TYPE> getComponentType(ItemStack stack);

	protected abstract long calculateComponentEMC(ItemStack stack, TYPE component, EMCMappingHandler handler);

	protected boolean validItem(ItemStack stack) {
		return true;
	}

	protected boolean shouldPersist(ItemStack stack, TYPE component) {
		return true;
	}

	@Override
	public final long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler) {
		if (validItem(stack)) {
			TYPE component = stack.get(getComponentType(stack));
			if (component != null && shouldPersist(stack, component)) {
				return calculateComponentEMC(stack, component, handler);
			}
		}
		return 0;
	}
}
