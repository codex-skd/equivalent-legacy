package com.skd.equivalentlegacy.emc.mapper;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface IExtendedMappingCollector<T, V extends Comparable<V>, A extends IValueArithmetic<?>>
        extends IMappingCollector<T, V> {
    void addConversion(int count, T output, Object2IntMap<T> ingredients, A arithmetic);

    void addConversion(int count, T output, Iterable<T> ingredients, A arithmetic);

    A getArithmetic();
}
