package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

/**
 * Base class for processors that add the EMC of the items stored inside a container
 * component (shulker boxes, bundles, alchemical bags, ...).
 *
 * <p>{@code shulker_box_with_diamonds = EMC(shulker_box) + EMC(diamonds_inside)}. If any
 * contained item has no EMC value the container as a whole has no EMC value.</p>
 */
public abstract class SimpleContainerProcessor<TYPE> implements IComponentProcessor {

	protected abstract DataComponentType<TYPE> getComponentType();

	protected abstract Iterable<ItemStack> getStoredItems(TYPE component);

	@Override
	public long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler) {
		TYPE component = stack.get(getComponentType());
		if (component == null) {
			return 0;
		}
		long total = 0;
		for (ItemStack item : getStoredItems(component)) {
			if (item.isEmpty()) {
				continue;
			}
			long itemEmc = handler.getEMCValue(item);
			if (itemEmc == 0) {
				//A contained item has no EMC value so the whole container cannot be converted
				throw new ArithmeticException("contained item has no EMC value");
			}
			total = Math.addExact(total, Math.multiplyExact(itemEmc, item.getCount()));
		}
		return total;
	}
}
