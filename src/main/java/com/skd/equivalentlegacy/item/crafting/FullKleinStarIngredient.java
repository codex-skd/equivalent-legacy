package com.skd.equivalentlegacy.item.crafting;

import com.mojang.serialization.MapCodec;
import com.skd.equivalentlegacy.item.EquivalentLegacyDataComponents;
import com.skd.equivalentlegacy.item.EquivalentLegacyItems;
import com.skd.equivalentlegacy.item.KleinStarTier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.stream.Stream;

public record FullKleinStarIngredient() implements ICustomIngredient {
    public static final MapCodec<FullKleinStarIngredient> CODEC = MapCodec.unit(FullKleinStarIngredient::new);

    @Override
    public boolean test(ItemStack stack) {
        if (!stack.is(EquivalentLegacyItems.KLEIN_STAR_OMEGA)) {
            return false;
        }
        long stored = stack.getOrDefault(EquivalentLegacyDataComponents.STORED_EMC, 0L);
        return stored >= KleinStarTier.OMEGA.getMaxEmc();
    }

    @Override
    public Stream<Holder<Item>> items() {
        return Stream.of(EquivalentLegacyItems.KLEIN_STAR_OMEGA.get().builtInRegistryHolder());
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return ModIngredientTypes.FULL_KLEIN_STAR.get();
    }
}
