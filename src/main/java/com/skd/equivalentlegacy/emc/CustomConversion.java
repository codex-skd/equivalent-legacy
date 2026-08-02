package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.Objects;

public final class CustomConversion {
    private final int count;
    private final NormalizedSimpleStack output;
    private final Object2IntMap<NormalizedSimpleStack> ingredients;
    private final boolean propagateTags;

    public static final CustomConversion INVALID = new CustomConversion(-1, null, new Object2IntOpenHashMap<>());

    public CustomConversion(int count, NormalizedSimpleStack output,
                            Object2IntMap<NormalizedSimpleStack> ingredients) {
        this(count, output, ingredients, true);
    }

    public CustomConversion(int count, NormalizedSimpleStack output,
                            Object2IntMap<NormalizedSimpleStack> ingredients, boolean propagateTags) {
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
        Object2IntOpenHashMap<NormalizedSimpleStack> map = new Object2IntOpenHashMap<>();
        map.putAll(ingredients);
        return new CustomConversion(count, output, map);
    }

    public int count() { return count; }

    public NormalizedSimpleStack output() { return output; }

    public Object2IntMap<NormalizedSimpleStack> ingredients() { return ingredients; }

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
