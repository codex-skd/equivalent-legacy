package com.skd.equivalentlegacy.emc.mapper;

public interface IValueArithmetic<V extends Comparable<V>> {
    V add(V a, V b);

    V multiply(V a, int b);

    V getZero();

    V getOne();

    V getInfinite();

    boolean isZero(V value);

    boolean isInfinite(V value);

    int compare(V a, V b);

    V divide(V a, int b);

    V min(V a, V b);
}
