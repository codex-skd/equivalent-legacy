package com.skd.equivalentlegacy.integration.recipe_viewer.jei;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IIngredientAliasRegistration;
import com.skd.equivalentlegacy.PECore;
import com.skd.equivalentlegacy.integration.recipe_viewer.alias.RVAliasHelper;
import com.skd.equivalentlegacy.utils.text.IHasTranslationKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * From Mekanism
 */
public class JEIAliasHelper implements RVAliasHelper<ItemStack> {

	private final IIngredientAliasRegistration registration;

	public JEIAliasHelper(IIngredientAliasRegistration registration) {
		this.registration = registration;
	}

	@Override
	public ItemStack ingredient(ItemLike itemLike) {
		return new ItemStack(itemLike);
	}

	@Override
	public List<ItemStack> tagContents(TagKey<Item> tag) {
		return StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(tag).spliterator(), false)
				.map(holder -> new ItemStack(holder.value()))
				.toList();
	}

	@Override
	public void addAliases(List<ItemStack> stacks, IHasTranslationKey... aliases) {
		if (aliases.length == 0) {
			PECore.LOGGER.warn("Expected to have at least one alias for  item ingredients: {}", stacks.stream()
					.map(stack -> stack.typeHolder().getRegisteredName())
					.collect(Collectors.joining(", "))
			);
		} else if (!stacks.isEmpty()) {
			registration.addAliases(VanillaTypes.ITEM_STACK, stacks, Arrays.stream(aliases)
					.map(IHasTranslationKey::getTranslationKey)
					.sorted()
					.toList());
		}
	}
}