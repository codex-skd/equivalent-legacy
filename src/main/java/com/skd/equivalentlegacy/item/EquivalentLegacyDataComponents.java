package com.skd.equivalentlegacy.item;

import com.mojang.serialization.Codec;
import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class EquivalentLegacyDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, EquivalentLegacy.MODID);

    public static final Supplier<DataComponentType<Long>> STORED_EMC = DATA_COMPONENT_TYPES.register("stored_emc",
            () -> DataComponentType.<Long>builder()
                    .persistent(Codec.LONG)
                    .networkSynchronized(ByteBufCodecs.VAR_LONG)
                    .build());

    private EquivalentLegacyDataComponents() {}
}
