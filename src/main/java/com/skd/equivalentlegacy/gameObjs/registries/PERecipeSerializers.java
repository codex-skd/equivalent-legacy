package com.skd.equivalentlegacy.gameObjs.registries;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.customRecipes.PERecipeSerializer;
import com.skd.equivalentlegacy.gameObjs.customRecipes.PhiloStoneSmeltingRecipe;
import com.skd.equivalentlegacy.gameObjs.customRecipes.RecipeShapelessKleinStar;
import com.skd.equivalentlegacy.gameObjs.customRecipes.RecipesCovalenceRepair;
import com.skd.equivalentlegacy.gameObjs.registration.PEDeferredHolder;
import com.skd.equivalentlegacy.gameObjs.registration.PEDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class PERecipeSerializers {

	public static final PEDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = new PEDeferredRegister<>(Registries.RECIPE_SERIALIZER, ELCore.MODID);

	public static final PEDeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<RecipesCovalenceRepair>> COVALENCE_REPAIR = RECIPE_SERIALIZERS.register("covalence_repair", () -> new SimpleCraftingRecipeSerializer<>(RecipesCovalenceRepair::new));
	public static final PEDeferredHolder<RecipeSerializer<?>, RecipeSerializer<RecipeShapelessKleinStar>> KLEIN = RECIPE_SERIALIZERS.register("crafting_shapeless_kleinstar", () -> PERecipeSerializer.wrapped(RecipeShapelessKleinStar::new));
	public static final PEDeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<PhiloStoneSmeltingRecipe>> PHILO_STONE_SMELTING = RECIPE_SERIALIZERS.register("philo_stone_smelting", () -> new SimpleCraftingRecipeSerializer<>(PhiloStoneSmeltingRecipe::new));
}
