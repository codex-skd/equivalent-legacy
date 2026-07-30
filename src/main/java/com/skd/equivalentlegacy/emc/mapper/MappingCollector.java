package com.skd.equivalentlegacy.emc.mapper;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.HolderLookup;

import java.util.*;

public class MappingCollector<T, V extends Comparable<V>, A extends IValueArithmetic<V>>
        implements IExtendedMappingCollector<T, V, A> {

    protected final A arithmetic;
    protected final Map<T, V> valueBefore = new HashMap<>();
    protected final Map<T, V> valueAfter = new HashMap<>();
    protected final List<Conversion<T>> conversions = new ArrayList<>();

    public MappingCollector(A arithmetic) {
        this.arithmetic = arithmetic;
    }

    @Override
    public void addConversion(int count, T output, Object2IntMap<T> ingredients) {
        conversions.add(new Conversion<>(count, output, new Object2IntOpenHashMap<>(ingredients), arithmetic));
    }

    @Override
    public void addConversion(int count, T output, Iterable<T> ingredients) {
        Object2IntMap<T> map = new Object2IntOpenHashMap<>();
        for (T t : ingredients) {
            map.mergeInt(t, 1, Integer::sum);
        }
        conversions.add(new Conversion<>(count, output, map, arithmetic));
    }

    @Override
    public void addConversion(int count, T output, Object2IntMap<T> ingredients, A arithmetic) {
        conversions.add(new Conversion<>(count, output, new Object2IntOpenHashMap<>(ingredients), arithmetic));
    }

    @Override
    public void addConversion(int count, T output, Iterable<T> ingredients, A arithmetic) {
        Object2IntMap<T> map = new Object2IntOpenHashMap<>();
        for (T t : ingredients) {
            map.mergeInt(t, 1, Integer::sum);
        }
        conversions.add(new Conversion<>(count, output, map, arithmetic));
    }

    @Override
    public void setValueBefore(T thing, V value) {
        valueBefore.put(thing, value);
    }

    @Override
    public void setValueAfter(T thing, V value) {
        valueAfter.put(thing, value);
    }

    @Override
    public void setValueFromConversion(int count, T output, Iterable<T> ingredients) {
    }

    @Override
    public void setValueFromConversion(int count, T output, Object2IntMap<T> ingredients) {
    }

    @Override
    public void finishCollection(HolderLookup.Provider provider) {
    }

    @Override
    public A getArithmetic() {
        return arithmetic;
    }

    public static class Conversion<T> {
        public final int count;
        public final T output;
        public final Object2IntMap<T> ingredients;
        public final IValueArithmetic<?> arithmetic;

        public Conversion(int count, T output, Object2IntMap<T> ingredients, IValueArithmetic<?> arithmetic) {
            this.count = count;
            this.output = output;
            this.ingredients = ingredients;
            this.arithmetic = arithmetic;
        }
    }
}
