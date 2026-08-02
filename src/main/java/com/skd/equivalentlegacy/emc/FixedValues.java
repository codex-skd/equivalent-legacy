package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

import java.util.*;

public final class FixedValues implements IHasConversions {
    private final Object2LongMap<NormalizedSimpleStack> setValueBefore;
    private final Object2LongMap<NormalizedSimpleStack> setValueAfter;
    private final List<CustomConversion> conversions;

    public FixedValues() {
        this(new Object2LongOpenHashMap<>(), new Object2LongOpenHashMap<>(), new ArrayList<>());
    }

    public FixedValues(Object2LongMap<NormalizedSimpleStack> setValueBefore,
                       Object2LongMap<NormalizedSimpleStack> setValueAfter,
                       List<CustomConversion> conversions) {
        this.setValueBefore = setValueBefore;
        this.setValueAfter = setValueAfter;
        this.conversions = conversions;
    }

    public void merge(FixedValues other) {
        setValueBefore.putAll(other.setValueBefore);
        setValueAfter.putAll(other.setValueAfter);
        conversions.addAll(other.conversions);
    }

    @Override
    public boolean isEmpty() {
        return setValueBefore.isEmpty() && setValueAfter.isEmpty() && conversions.isEmpty();
    }

    public Object2LongMap<NormalizedSimpleStack> setValueBefore() {
        return setValueBefore;
    }

    public Object2LongMap<NormalizedSimpleStack> setValueAfter() {
        return setValueAfter;
    }

    public List<CustomConversion> conversions() {
        return Collections.unmodifiableList(conversions);
    }

    public void addSetValueBefore(NormalizedSimpleStack stack, long value) {
        setValueBefore.put(stack, value);
    }

    public void addSetValueAfter(NormalizedSimpleStack stack, long value) {
        setValueAfter.put(stack, value);
    }

    public void addConversion(CustomConversion conversion) {
        conversions.add(conversion);
    }

    @Override
    public String toString() {
        return "FixedValues[before=" + setValueBefore.size() + ", after=" + setValueAfter.size()
                + ", conversions=" + conversions.size() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedValues that)) return false;
        return Objects.equals(setValueBefore, that.setValueBefore)
                && Objects.equals(setValueAfter, that.setValueAfter)
                && Objects.equals(conversions, that.conversions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setValueBefore, setValueAfter, conversions);
    }
}
