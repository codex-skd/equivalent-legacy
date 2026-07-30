package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;

import java.util.Objects;

public final class CustomConversion {
    private final int count;
    private final NormalizedSimpleStack output;
    private final Object2IntSortedMap<NormalizedSimpleStack> ingredients;
    private final boolean propagateTags;

    public static final CustomConversion INVALID = new CustomConversion(-1, null, new Object2IntRBTreeMap<>());

    public CustomConversion(int count, NormalizedSimpleStack output,
                            Object2IntSortedMap<NormalizedSimpleStack> ingredients) {
        this(count, output, ingredients, true);
    }

    public CustomConversion(int count, NormalizedSimpleStack output,
                            Object2IntSortedMap<NormalizedSimpleStack> ingredients, boolean propagateTags) {
        this.count = count;
        this.output = output;
        this.ingredients = ingredients;
        this.propagateTags = propagateTags;
    }

    public static CustomConversion getFor(int count, NormalizedSimpleStack output,
                                          Object2IntMap<NormalizedSimpleStack> ingredients) {
        if (count <= 0 || output == null || ingredients == null || ingredients.isEmpty()) {
            return INVALID;
        }
        Object2IntRBTreeMap<NormalizedSimpleStack> sorted = new Object2IntRBTreeMap<>();
        sorted.putAll(ingredients);
        return new CustomConversion(count, output, sorted);
    }

    public int count() { return count; }

    public NormalizedSimpleStack output() { return output; }

    public Object2IntSortedMap<NormalizedSimpleStack> ingredients() { return ingredients; }

    public boolean propagateTags() { return propagateTags; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomConversion that)) return false;
        return count == that.count && propagateTags == that.propagateTags
                && Objects.equals(output, that.output) && Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, output, ingredients, propagateTags);
    }

    @Override
    public String toString() {
        return "CustomConversion[" + count + "x " + output + " from " + ingredients + "]";
    }
}
