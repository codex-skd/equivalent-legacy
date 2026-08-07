package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities;
import com.skd.equivalentlegacy.block.entity.RMPedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class RMPedestal extends Pedestal {
    public RMPedestal(Properties properties) {
        super(properties, () -> (BlockEntityType<? extends com.skd.equivalentlegacy.block.entity.PedestalBlockEntity>) (BlockEntityType<?>) EquivalentLegacyBlockEntities.RM_PEDESTAL.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (level.getBlockEntity(pos) instanceof RMPedestalBlockEntity be) {
            if (player.isCrouching()) {
                int newMode = be.toggleMode();
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.translatable(
                                "message.equivalent_legacy.pedestal_mode." + (newMode == RMPedestalBlockEntity.MODE_LINK ? "link" : "display")));
                return InteractionResult.CONSUME;
            }

            if (be.isLinkMode()) {
                be.clearSelection();
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.translatable("message.equivalent_legacy.link_active"));
                return InteractionResult.CONSUME;
            }

            return be.toggleItem(player);
        }
        return InteractionResult.PASS;
    }
}
