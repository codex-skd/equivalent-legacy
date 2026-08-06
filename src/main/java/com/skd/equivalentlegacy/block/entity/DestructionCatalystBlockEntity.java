package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DestructionCatalystBlockEntity extends BlockEntity {
    public static final int DEFAULT_FUSE = 80;

    private int fuse = DEFAULT_FUSE;

    public DestructionCatalystBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.DESTRUCTION_CATALYST.get(), pos, state);
    }

    public int getFuse() {
        return fuse;
    }

    public void setFuse(int fuse) {
        this.fuse = fuse;
    }
}