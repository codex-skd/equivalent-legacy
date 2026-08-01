package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.entity.MachineTiers.RelayTier;
import com.skd.equivalentlegacy.gui.RelayMenu;
import com.skd.equivalentlegacy.item.KleinStar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RelayBlockEntity extends BaseMachineBlockEntity {
    public static final int KLEIN_SLOT = 0;

    private int chargeProgress;

    public RelayBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.RELAY.get(), pos, state, 1);
    }

    public int getChargeProgress() {
        return chargeProgress;
    }

    public static BlockEntityTicker<RelayBlockEntity> ticker() {
        return (level, pos, state, be) -> tick(be);
    }

    private static void tick(RelayBlockEntity be) {
        Level level = be.getLevel();
        if (level == null || level.isClientSide()) return;
        be.tickServer(level);
    }

    private void tickServer(Level level) {
        RelayTier tier = RelayTier.of(getBlockState().getBlock());
        pullFromCollectors(level, tier);
        chargeKleinStar(tier);
        setChanged();
    }

    private void pullFromCollectors(Level level, RelayTier tier) {
        for (Direction dir : Direction.values()) {
            BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
            if (be instanceof CollectorBlockEntity collector) {
                long available = collector.getEmc();
                long space = tier.emcCapacity - (long) emc;
                if (available <= 0 || space <= 0) continue;
                long take = Math.min(available, Math.min(space, tier.transferRate));
                if (take > 0) {
                    emc += collector.takeEmc(take);
                }
            }
        }
    }

    private void chargeKleinStar(RelayTier tier) {
        ItemStack stack = inventory.getItem(KLEIN_SLOT);
        if (emc < 1 || stack.isEmpty() || !(stack.getItem() instanceof KleinStar star)) {
            chargeProgress = 0;
            return;
        }
        long space = star.getSpace(stack);
        if (space <= 0) {
            chargeProgress = 100;
            return;
        }
        long transfer = Math.min((long) emc, Math.min(space, tier.transferRate));
        if (transfer > 0) {
            star.insertEmc(stack, transfer, false);
            emc -= transfer;
            chargeProgress = (int) (100 * star.getStoredEmc(stack) / star.getMaxEmc());
        }
    }

    @Override
    protected MenuProvider createMenuProvider() {
        return new SimpleMenuProvider((id, inv, p) -> new RelayMenu(id, inv, worldPosition), getDisplayName());
    }

    @Override
    protected Component getDisplayName() {
        return Component.translatable("container.equivalent_legacy.relay");
    }
}
