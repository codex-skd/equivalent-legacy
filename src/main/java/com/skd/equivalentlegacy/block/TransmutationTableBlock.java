package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.gui.TransmutationContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TransmutationTableBlock extends Block {
    public TransmutationTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new TransmutationContainer(id, inv),
                    Component.translatable("container.equivalent_legacy.transmutation")));
        }
        return InteractionResult.SUCCESS;
    }
}
