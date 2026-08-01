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

    public static final DeferredBlock<Block> ALCHEMICAL_COAL_BLOCK = BLOCKS.register("alchemical_coal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MOBIUS_FUEL_BLOCK = BLOCKS.register("mobius_fuel_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE)));

    public static final DeferredBlock<Block> AETERNALIS_FUEL_BLOCK = BLOCKS.register("aeternalis_fuel_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE)));

    public static final DeferredBlock<Block> DARK_MATTER_BLOCK = BLOCKS.register("dark_matter_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final DeferredBlock<Block> RED_MATTER_BLOCK = BLOCKS.register("red_matter_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    private static final BlockBehaviour.Properties MACHINE_PROPERTIES = BlockBehaviour.Properties.of()
            .strength(3.0F, 6.0F).sound(SoundType.METAL);

    public static final DeferredBlock<Block> COLLECTOR_MK1 = BLOCKS.register("collector_mk1",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.COLLECTOR::get,
                    CollectorBlockEntity.ticker()));

    public static final DeferredBlock<Block> COLLECTOR_MK2 = BLOCKS.register("collector_mk2",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.COLLECTOR::get,
                    CollectorBlockEntity.ticker()));

    public static final DeferredBlock<Block> COLLECTOR_MK3 = BLOCKS.register("collector_mk3",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.COLLECTOR::get,
                    CollectorBlockEntity.ticker()));

    public static final DeferredBlock<Block> RELAY_MK1 = BLOCKS.register("relay_mk1",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.RELAY::get,
                    RelayBlockEntity.ticker()));

    public static final DeferredBlock<Block> RELAY_MK2 = BLOCKS.register("relay_mk2",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.RELAY::get,
                    RelayBlockEntity.ticker()));

    public static final DeferredBlock<Block> RELAY_MK3 = BLOCKS.register("relay_mk3",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.RELAY::get,
                    RelayBlockEntity.ticker()));

    public static final DeferredBlock<Block> CONDENSER_MK1 = BLOCKS.register("condenser_mk1",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.CONDENSER::get,
                    CondenserBlockEntity.ticker()));

    public static final DeferredBlock<Block> CONDENSER_MK2 = BLOCKS.register("condenser_mk2",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.CONDENSER::get,
                    CondenserBlockEntity.ticker()));

    public static final DeferredBlock<Block> ALCHEMICAL_CHEST = BLOCKS.register("alchemical_chest",
            () -> new BaseMachineBlock(MACHINE_PROPERTIES, EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST::get, null));

    public static final DeferredBlock<Block> TRANSMUTATION_TABLE = BLOCKS.register("transmutation_table",
            () -> new TransmutationTableBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.STONE)));

    public static final DeferredBlock<Block> INTERDICTION_TORCH = BLOCKS.register("interdiction_torch",
            () -> new InterdictionTorchBlock(BlockBehaviour.Properties.of().strength(1.0F).noCollision().lightLevel(state -> 14)));

    private EquivalentLegacyBlocks() {}
}
