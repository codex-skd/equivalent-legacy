package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.entity.CollectorBlockEntity;
import com.skd.equivalentlegacy.block.entity.CondenserBlockEntity;
import com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities;
import com.skd.equivalentlegacy.block.entity.RelayBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquivalentLegacyBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EquivalentLegacy.MODID);

    public static final DeferredBlock<Block> ALCHEMICAL_COAL_BLOCK = BLOCKS.registerBlock("alchemical_coal_block",
            Block::new, () -> BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE));

    public static final DeferredBlock<Block> MOBIUS_FUEL_BLOCK = BLOCKS.registerBlock("mobius_fuel_block",
            Block::new, () -> BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE));

    public static final DeferredBlock<Block> AETERNALIS_FUEL_BLOCK = BLOCKS.registerBlock("aeternalis_fuel_block",
            Block::new, () -> BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE));

    public static final DeferredBlock<Block> DARK_MATTER_BLOCK = BLOCKS.registerBlock("dark_matter_block",
            Block::new, () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL));

    public static final DeferredBlock<Block> RED_MATTER_BLOCK = BLOCKS.registerBlock("red_matter_block",
            Block::new, () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL));

    private static final BlockBehaviour.Properties MACHINE_PROPERTIES = BlockBehaviour.Properties.of()
            .strength(3.0F, 6.0F).sound(SoundType.METAL);

    public static final DeferredBlock<Block> COLLECTOR_MK1 = BLOCKS.registerBlock("collector_mk1",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.COLLECTOR::get, CollectorBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> COLLECTOR_MK2 = BLOCKS.registerBlock("collector_mk2",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.COLLECTOR::get, CollectorBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> COLLECTOR_MK3 = BLOCKS.registerBlock("collector_mk3",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.COLLECTOR::get, CollectorBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> RELAY_MK1 = BLOCKS.registerBlock("relay_mk1",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.RELAY::get, RelayBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> RELAY_MK2 = BLOCKS.registerBlock("relay_mk2",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.RELAY::get, RelayBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> RELAY_MK3 = BLOCKS.registerBlock("relay_mk3",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.RELAY::get, RelayBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> CONDENSER_MK1 = BLOCKS.registerBlock("condenser_mk1",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.CONDENSER::get, CondenserBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> CONDENSER_MK2 = BLOCKS.registerBlock("condenser_mk2",
            props -> new BaseMachineBlock(props, EquivalentLegacyBlockEntities.CONDENSER::get, CondenserBlockEntity.ticker()),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> ALCHEMICAL_CHEST = BLOCKS.registerBlock("alchemical_chest",
            props -> new AlchemicalChestBlock(props, EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST::get),
            () -> MACHINE_PROPERTIES);

    public static final DeferredBlock<Block> TRANSMUTATION_TABLE = BLOCKS.registerBlock("transmutation_table",
            TransmutationTableBlock::new, () -> BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE));

    public static final DeferredBlock<Block> INTERDICTION_TORCH = BLOCKS.registerBlock("interdiction_torch",
            InterdictionTorchBlock::new, () -> BlockBehaviour.Properties.of().strength(1.0F).noCollision().lightLevel(state -> 14));

    public static final DeferredBlock<Block> DM_FURNACE = BLOCKS.registerBlock("dm_furnace",
            props -> new MatterFurnaceBlock(props, EquivalentLegacyBlockEntities.DM_FURNACE::get),
            () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(MatterFurnaceBlock.LIT) ? 13 : 0));

    public static final DeferredBlock<Block> RM_FURNACE = BLOCKS.registerBlock("rm_furnace",
            props -> new MatterFurnaceBlock(props, EquivalentLegacyBlockEntities.RM_FURNACE::get),
            () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(MatterFurnaceBlock.LIT) ? 13 : 0));

public static final DeferredBlock<com.skd.equivalentlegacy.block.Pedestal> PEDESTAL = BLOCKS.registerBlock("pedestal",
            props -> new com.skd.equivalentlegacy.block.Pedestal(props, com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities.PEDESTAL::get),
            () -> BlockBehaviour.Properties.of().strength(2.0F, 6.0F).sound(SoundType.STONE));

    public static final DeferredBlock<com.skd.equivalentlegacy.block.DMPedestal> DM_PEDESTAL = BLOCKS.registerBlock("dm_pedestal",
            props -> new com.skd.equivalentlegacy.block.DMPedestal(props),
            () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL));

    public static final DeferredBlock<com.skd.equivalentlegacy.block.RMPedestal> RM_PEDESTAL = BLOCKS.registerBlock("rm_pedestal",
            props -> new com.skd.equivalentlegacy.block.RMPedestal(props),
            () -> BlockBehaviour.Properties.of().strength(6.0F, 7.0F).sound(SoundType.METAL));

    public static final DeferredBlock<com.skd.equivalentlegacy.block.DestructionCatalyst> DESTRUCTION_CATALYST = BLOCKS.registerBlock(
            "destruction_catalyst",
            props -> new com.skd.equivalentlegacy.block.DestructionCatalyst(props),
            () -> BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.WOOD));

    private EquivalentLegacyBlocks() {}
}
