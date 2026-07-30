package com.skd.equivalentlegacy.emc.nss;

import java.util.function.Consumer;

public interface NSSTag extends NormalizedSimpleStack {
    boolean representsTag();

    void forEachElement(Consumer<NormalizedSimpleStack> consumer);

    @Override
    default void forSelfAndEachElement(Consumer<NormalizedSimpleStack> consumer) {
        consumer.accept(this);
        forEachElement(consumer);
    }
}
