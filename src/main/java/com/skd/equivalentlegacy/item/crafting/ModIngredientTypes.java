package com.skd.equivalentlegacy.item.crafting;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModIngredientTypes {
    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, EquivalentLegacy.MODID);

    public static final DeferredHolder<IngredientType<?>, IngredientType<FullKleinStarIngredient>> FULL_KLEIN_STAR =
            INGREDIENT_TYPES.register("full_klein_star", () -> new IngredientType<>(FullKleinStarIngredient.CODEC));

    private ModIngredientTypes() {}
}
