package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import net.minecraft.world.item.ItemStack;

/**
 * Reduces the EMC value of damaged items proportionally to their remaining durability.
 *
 * <p>{@code damaged_item_emc = baseEmc * remainingDurability / maxDamage}.</p>
 */
public class DamageProcessor implements IComponentProcessor {

	@Override
	public String getName() {
		return "DamageProcessor";
	}

	@Override
	public long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler) {
		if (!stack.isDamaged()) {
			return 0;
		}
		int maxDamage = stack.getMaxDamage();
		int remainingDurability = maxDamage - stack.getDamageValue();
		if (remainingDurability <= 0) {
			//Broken tool or invalid damage data: the item is worth no EMC
			return -Long.MAX_VALUE;
		}
		long base = handler.getBaseEMCValue(NSSItem.createItem(stack.getItem()));
		if (base <= 0) {
			return 0;
		}
		long proportional;
		if (remainingDurability == 1) {
			//Skip the multiplication to avoid overflow on single-durability items
			proportional = base / maxDamage;
		} else {
			proportional = Math.multiplyExact(base, remainingDurability) / maxDamage;
		}
		return proportional - base;
	}
}
