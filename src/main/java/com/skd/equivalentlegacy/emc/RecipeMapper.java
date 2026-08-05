package com.skd.equivalentlegacy.emc;

import net.minecraft.world.item.crafting.RecipeHolder;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class RecipeMapper {
    private static final Logger LOGGER = LogUtils.getLogger();

    private RecipeMapper() {}

    public static FixedValues mapRecipes(Iterable<RecipeHolder<?>> recipes) {
        FixedValues fixedValues = new FixedValues();
        int recipeCount = 0;

        for (RecipeHolder<?> recipeHolder : recipes) {
            recipeCount++;
        }

        LOGGER.info("Scanned {} recipes (recipe-based EMC calculation not yet available)", recipeCount);
        return fixedValues;
    }
}
