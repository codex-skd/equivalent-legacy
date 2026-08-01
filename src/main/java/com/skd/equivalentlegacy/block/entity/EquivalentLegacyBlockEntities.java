package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquivalentLegacyBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EquivalentLegacy.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CollectorBlockEntity>> COLLECTOR =
            BLOCK_ENTITY_TYPES.register("collector", () -> new BlockEntityType<>(CollectorBlockEntity::new,
                    EquivalentLegacyBlocks.COLLECTOR_MK1.get(),
                    EquivalentLegacyBlocks.COLLECTOR_MK2.get(),
                    EquivalentLegacyBlocks.COLLECTOR_MK3.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RelayBlockEntity>> RELAY =
            BLOCK_ENTITY_TYPES.register("relay", () -> new BlockEntityType<>(RelayBlockEntity::new,
                    EquivalentLegacyBlocks.RELAY_MK1.get(),
                    EquivalentLegacyBlocks.RELAY_MK2.get(),
                    EquivalentLegacyBlocks.RELAY_MK3.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CondenserBlockEntity>> CONDENSER =
            BLOCK_ENTITY_TYPES.register("condenser", () -> new BlockEntityType<>(CondenserBlockEntity::new,
                    EquivalentLegacyBlocks.CONDENSER_MK1.get(),
                    EquivalentLegacyBlocks.CONDENSER_MK2.get()));

    private EquivalentLegacyBlockEntities() {}
}
