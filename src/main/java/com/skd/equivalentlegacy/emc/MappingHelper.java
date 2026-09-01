package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.ELCore;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

public final class MappingHelper {

	private MappingHelper() {
	}

	@NotNull
	public static ResourceLocation recipeId(@NotNull RecipeHolder<?> recipeHolder) {
		return recipeHolder.id();
	}

	@NotNull
	public static ItemStack getRecipeOutput(@NotNull Recipe<?> recipe, @NotNull HolderLookup.Provider registryAccess) {
		if (recipe instanceof SingleItemRecipe singleItemRecipe) {
			return singleItemRecipe.assemble(new SingleRecipeInput(ItemStack.EMPTY), registryAccess);
		}
		if (recipe instanceof ShapelessRecipe shapelessRecipe) {
			return shapelessRecipe.assemble(net.minecraft.world.item.crafting.CraftingInput.EMPTY, registryAccess);
		}
		if (recipe instanceof ShapedRecipe shapedRecipe) {
			try {
				return shapedRecipe.assemble(net.minecraft.world.item.crafting.CraftingInput.EMPTY, registryAccess);
			} catch (RuntimeException e) {
				return ItemStack.EMPTY;
			}
		}
		if (recipe instanceof SmithingTransformRecipe transformRecipe) {
			return transformRecipe.assemble(new SmithingRecipeInput(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY), registryAccess);
		}
		if (recipe instanceof SmithingTrimRecipe trimRecipe) {
			return trimRecipe.assemble(new SmithingRecipeInput(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY), registryAccess);
		}
		return ItemStack.EMPTY;
	}

	@NotNull
	public static ItemStack[] getMatchingStacks(@NotNull Ingredient ingredient, @NotNull ResourceLocation recipeId) {
		try {
			return ingredient.getItems();
		} catch (Exception e) {
			ICustomIngredient customIngredient = ingredient.getCustomIngredient();
			if (customIngredient != null) {
				ResourceLocation name = NeoForgeRegistries.INGREDIENT_TYPES.getKey(customIngredient.getType());
				if (name == null) {
					ELCore.LOGGER.error("Error mapping recipe {}. Ingredient of type: {} crashed when getting the matching stacks. Please report this to the ingredient's creator.",
							recipeId, customIngredient.getClass(), e);
				} else {
					ELCore.LOGGER.error("Error mapping recipe {}. Ingredient of type: {} crashed when getting the matching stacks. Please report this to the ingredient's creator ({}).",
							recipeId, name, name.getNamespace(), e);
				}
			} else {
				ELCore.LOGGER.error("Error mapping recipe {}. Crashed when getting the matching stacks.", recipeId, e);
			}
			return new ItemStack[0];
		}
	}
}
