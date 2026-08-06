package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * Adds EMC for enchantments, scaled by enchantment rarity.
 *
 * <p>{@code enchanted_diamond_sword = EMC(diamond_sword) + EMC(sharpness, ...)} where each
 * enchantment contributes {@code bonus / rarityWeight} per level.</p>
 */
public class EnchantmentProcessor extends PersistentComponentProcessor<ItemEnchantments> {

	private static final long DEFAULT_ENCHANT_EMC_BONUS = 5_000;

	@Override
	public String getName() {
		return "EnchantmentProcessor";
	}

	@Override
	protected DataComponentType<ItemEnchantments> getComponentType(ItemStack stack) {
		return stack.is(Items.ENCHANTED_BOOK) ? DataComponents.STORED_ENCHANTMENTS : DataComponents.ENCHANTMENTS;
	}

	@Override
	protected boolean shouldPersist(ItemStack stack, ItemEnchantments component) {
		return !component.isEmpty();
	}

	@Override
	protected long calculateComponentEMC(ItemStack stack, ItemEnchantments enchantments, EMCMappingHandler handler) {
		long emcBonus = 0;
		for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
			int rarityWeight = entry.getKey().value().definition().weight();
			if (rarityWeight > 0) {
				emcBonus = Math.addExact(emcBonus, Math.multiplyExact(DEFAULT_ENCHANT_EMC_BONUS / rarityWeight, entry.getIntValue()));
			}
		}
		return emcBonus;
	}
}
