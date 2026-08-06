package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DMPedestalBlockEntity extends PedestalBlockEntity {
    public DMPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.DM_PEDESTAL.get(), pos, state);
    }
}