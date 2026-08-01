package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.EquivalentLegacy;
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

    private EquivalentLegacyBlocks() {}
}
