package com.skd.equivalentlegacy.emc.mapper;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.HolderLookup;

public interface IMappingCollector<T, V extends Comparable<V>> {
    void addConversion(int count, T output, Object2IntMap<T> ingredients);

    void addConversion(int count, T output, Iterable<T> ingredients);

    void setValueBefore(T thing, V value);

    void setValueAfter(T thing, V value);

    void setValueFromConversion(int count, T output, Iterable<T> ingredients);

    void setValueFromConversion(int count, T output, Object2IntMap<T> ingredients);

    void finishCollection(HolderLookup.Provider provider);
}
