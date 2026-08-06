package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.ArmorTrim;

/**
 * Adds the EMC of the applied armor trim (material + pattern template).
 */
public class ArmorTrimProcessor implements IComponentProcessor {

	@Override
	public String getName() {
		return "ArmorTrimProcessor";
	}

	@Override
	public long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler) {
		if (!stack.is(ItemTags.TRIMMABLE_ARMOR)) {
			return 0;
		}
		ArmorTrim trim = stack.get(DataComponents.TRIM);
		if (trim == null) {
			return 0;
		}
		//TODO 26.2: ArmorTrimMaterial API changed; skip trim EMC adjustment for now.
		return 0;
	}
}
