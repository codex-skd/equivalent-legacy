package com.skd.equivalentlegacy.emc.nss;

import com.mojang.serialization.MapCodec;
import java.util.function.Consumer;

public interface NormalizedSimpleStack {
    MapCodec<? extends NormalizedSimpleStack> codec();

    default void forSelfAndEachElement(Consumer<NormalizedSimpleStack> consumer) {
        consumer.accept(this);
    }
}
