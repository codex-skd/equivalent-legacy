package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.entity.MachineTiers.RelayTier;
import com.skd.equivalentlegacy.emc.EMCNetwork;
import com.skd.equivalentlegacy.gui.RelayMenu;
import com.skd.equivalentlegacy.item.KleinStar;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
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

    @Override
    public long getEmc() {
        return getLevel() != null ? EMCNetwork.getEmc(getLevel()) : 0L;
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
        chargeKleinStar(tier);
        setChanged();
    }

    private void chargeKleinStar(RelayTier tier) {
        ItemStack stack = inventory.getItem(KLEIN_SLOT);
        Level level = getLevel();
        if (level == null || stack.isEmpty() || !(stack.getItem() instanceof KleinStar star)) {
            chargeProgress = 0;
            return;
        }
        long space = star.getSpace(stack);
        if (space <= 0) {
            chargeProgress = 100;
            return;
        }
        long available = EMCNetwork.getEmc(level);
        long transfer = Math.min(available, Math.min(space, tier.transferRate));
        if (transfer > 0) {
            star.insertEmc(stack, transfer, false);
            EMCNetwork.takeEmc(level, transfer);
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
