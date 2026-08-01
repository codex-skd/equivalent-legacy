package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities;
import com.skd.equivalentlegacy.block.entity.InterdictionTorchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class InterdictionTorchBlock extends Block implements EntityBlock {
    public InterdictionTorchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InterdictionTorchBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == EquivalentLegacyBlockEntities.INTERDICTION_TORCH.get() ? (BlockEntityTicker<T>) InterdictionTorchBlockEntity.ticker() : null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
