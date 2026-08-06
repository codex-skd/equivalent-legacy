package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.block.entity.DestructionCatalystBlockEntity;
import com.skd.equivalentlegacy.entity.EntityNovaCataclysmPrimed;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class DestructionCatalyst extends Block implements EntityBlock {
    public DestructionCatalyst(Properties properties) {
        super(properties);
    }

    public void prime(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (level.isClientSide()) return;
        EntityNovaCataclysmPrimed entity = new EntityNovaCataclysmPrimed(
                level, pos.getX() + 0.5, pos.getY() + 0.0625, pos.getZ() + 0.5);
        entity.setFuse(DestructionCatalystBlockEntity.DEFAULT_FUSE);
        level.addFreshEntity(entity);
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.PRIME_FUSE, pos);
        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
    ) {
        if (!stack.is(Items.FLINT_AND_STEEL) && !stack.is(Items.FIRE_CHARGE)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            prime(level, pos, state, player);
            Item item = stack.getItem();
            if (stack.is(Items.FLINT_AND_STEEL)) {
                stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
            } else {
                stack.consume(1, player);
            }
            player.awardStat(Stats.ITEM_USED.get(item));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(state.getBlock()) && level.hasNeighborSignal(pos)) {
            prime(level, pos, state, null);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (!level.isClientSide() && level.hasNeighborSignal(pos)) {
            prime(level, pos, state, null);
        }
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, net.minecraft.world.level.Explosion explosion) {
        if (level.getGameRules().get(net.minecraft.world.level.gamerules.GameRules.TNT_EXPLODES)) {
            EntityNovaCataclysmPrimed entity = new EntityNovaCataclysmPrimed(
                    level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            entity.setFuse(DestructionCatalystBlockEntity.DEFAULT_FUSE);
            level.addFreshEntity(entity);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DestructionCatalystBlockEntity(pos, state);
    }
}