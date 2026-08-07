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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlchemicalChestBlockEntity>> ALCHEMICAL_CHEST =
            BLOCK_ENTITY_TYPES.register("alchemical_chest", () -> new BlockEntityType<>(AlchemicalChestBlockEntity::new,
                    EquivalentLegacyBlocks.ALCHEMICAL_CHEST.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InterdictionTorchBlockEntity>> INTERDICTION_TORCH =
            BLOCK_ENTITY_TYPES.register("interdiction_torch", () -> new BlockEntityType<>(InterdictionTorchBlockEntity::new,
                    EquivalentLegacyBlocks.INTERDICTION_TORCH.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterFurnaceBlockEntity>> DM_FURNACE =
            BLOCK_ENTITY_TYPES.register("dm_furnace", () -> new BlockEntityType<>(MatterFurnaceBlockEntity::new,
                    EquivalentLegacyBlocks.DM_FURNACE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatterFurnaceBlockEntity>> RM_FURNACE =
            BLOCK_ENTITY_TYPES.register("rm_furnace", () -> new BlockEntityType<>(MatterFurnaceBlockEntity::new,
                    EquivalentLegacyBlocks.RM_FURNACE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL =
            BLOCK_ENTITY_TYPES.register("pedestal", () -> new BlockEntityType<>(PedestalBlockEntity::new,
                    EquivalentLegacyBlocks.PEDESTAL.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DMPedestalBlockEntity>> DM_PEDESTAL =
            BLOCK_ENTITY_TYPES.register("dm_pedestal", () -> new BlockEntityType<>(DMPedestalBlockEntity::new,
                    EquivalentLegacyBlocks.DM_PEDESTAL.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RMPedestalBlockEntity>> RM_PEDESTAL =
            BLOCK_ENTITY_TYPES.register("rm_pedestal", () -> new BlockEntityType<>(RMPedestalBlockEntity::new,
                    EquivalentLegacyBlocks.RM_PEDESTAL.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DestructionCatalystBlockEntity>> DESTRUCTION_CATALYST =
            BLOCK_ENTITY_TYPES.register("destruction_catalyst", () -> new BlockEntityType<>(DestructionCatalystBlockEntity::new,
                    EquivalentLegacyBlocks.DESTRUCTION_CATALYST.get()));

    private EquivalentLegacyBlockEntities() {}
}
