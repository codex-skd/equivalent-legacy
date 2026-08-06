package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RMPedestalBlockEntity extends PedestalBlockEntity {
    public RMPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.RM_PEDESTAL.get(), pos, state);
    }
}