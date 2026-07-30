package com.skd.equivalentlegacy.emc.mapper;

import java.util.Map;

public interface IValueGenerator<T, V extends Comparable<V>> {
    Map<T, V> generateValues();
}
