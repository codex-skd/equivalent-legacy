package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.entity.MachineTiers.CollectorTier;
import com.skd.equivalentlegacy.emc.EMCNetwork;
import com.skd.equivalentlegacy.gui.CollectorMenu;
import com.skd.equivalentlegacy.item.KleinStar;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class CollectorBlockEntity extends BaseMachineBlockEntity {
    public static final int KLEIN_SLOT = 0;

    private int sunLevel;
    private int chargeProgress;

    public CollectorBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.COLLECTOR.get(), pos, state, 1);
    }

    public int getSunLevel() {
        return sunLevel;
    }

    public int getChargeProgress() {
        return chargeProgress;
    }

    @Override
    public long getEmc() {
        return getLevel() != null ? EMCNetwork.getEmc(getLevel()) : 0L;
    }

    public static BlockEntityTicker<CollectorBlockEntity> ticker() {
        return (level, pos, state, be) -> tick(be);
    }

    private static void tick(CollectorBlockEntity be) {
        Level level = be.getLevel();
        if (level == null || level.isClientSide()) return;
        be.tickServer(level);
    }

    private void tickServer(Level level) {
        CollectorTier tier = CollectorTier.of(getBlockState().getBlock());
        sunLevel = level.getMaxLocalRawBrightness(worldPosition.above());
        if (sunLevel > 0) {
            long generated = tier.rate * sunLevel / 300;
            if (generated > 0) {
                EMCNetwork.addEmc(level, generated);
            }
        }
        chargeKleinStar(tier);
        setChanged();
    }

    private void chargeKleinStar(CollectorTier tier) {
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
        long transfer = Math.min(available, Math.min(space, tier.rate));
        if (transfer > 0) {
            star.insertEmc(stack, transfer, false);
            EMCNetwork.takeEmc(level, transfer);
            chargeProgress = (int) (100 * star.getStoredEmc(stack) / star.getMaxEmc());
        }
    }

    @Override
    protected MenuProvider createMenuProvider() {
        return new SimpleMenuProvider((id, inv, p) -> new CollectorMenu(id, inv, worldPosition), getDisplayName());
    }

    @Override
    protected Component getDisplayName() {
        return Component.translatable("container.equivalent_legacy.collector");
    }
}
