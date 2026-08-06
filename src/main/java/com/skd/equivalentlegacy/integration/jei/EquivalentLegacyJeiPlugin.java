package com.skd.equivalentlegacy.integration.jei;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.item.EquivalentLegacyItems;
import com.skd.equivalentlegacy.world_transmutation.TransmutationResult;
import com.skd.equivalentlegacy.world_transmutation.WorldTransmutationManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * JEI entry point for Equivalent Legacy. Registers the Transmutation recipe category and feeds it with
 * every mapping held by {@link WorldTransmutationManager}. JEI is an optional dependency: this class is
 * only discovered and loaded when JEI is present at runtime.
 */
@JeiPlugin
public class EquivalentLegacyJeiPlugin implements IModPlugin {
    public static final Identifier UID = EquivalentLegacy.rl("transmutation");
    public static final RecipeType<TransmutationRecipeDisplay> TRANSMUTATION_TYPE =
            RecipeType.create(EquivalentLegacy.MODID, "transmutation", TransmutationRecipeDisplay.class);

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new TransmutationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(TRANSMUTATION_TYPE, loadTransmutationRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TRANSMUTATION_TYPE,
                EquivalentLegacyItems.PHILOSOPHERS_STONE.get(),
                EquivalentLegacyItems.TRANSMUTATION_STONE.get());
    }

    private static List<TransmutationRecipeDisplay> loadTransmutationRecipes() {
        List<TransmutationRecipeDisplay> recipes = new ArrayList<>();
        for (var entry : WorldTransmutationManager.getTransmutationMap().entrySet()) {
            Block inputBlock = entry.getKey();
            TransmutationResult result = entry.getValue();
            if (inputBlock == null || result.resultBlock() == null) continue;
            recipes.add(new TransmutationRecipeDisplay(
                    new ItemStack(inputBlock),
                    new ItemStack(result.resultBlock()),
                    result.emcCost()));
        }
        return recipes;
    }
}
