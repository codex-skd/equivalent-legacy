package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.CollectorBlockEntity;
import com.skd.equivalentlegacy.gui.slots.SlotPredicates;
import com.skd.equivalentlegacy.gui.slots.ValidatedContainerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import org.jetbrains.annotations.NotNull;

public class CollectorMenu extends PEContainer {
    public final CollectorBlockEntity blockEntity;
    public final DataSlot sunLevel = DataSlot.standalone();
    public final BoxedLong networkEmc = new BoxedLong();
    private final DataSlot kleinChargeProgress = DataSlot.standalone();

    public CollectorMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.COLLECTOR.get(), containerId);
        this.blockEntity = (CollectorBlockEntity) playerInventory.player.level().getBlockEntity(pos);

        longFields.add(networkEmc);
        addDataSlot(sunLevel);
        addDataSlot(kleinChargeProgress);
        addDataSlot(networkEmc.highSlot);
        addDataSlot(networkEmc.lowSlot);

        addSlot(new ValidatedContainerSlot(blockEntity.getInventory(), CollectorBlockEntity.KLEIN_SLOT, 62, 17, SlotPredicates.HAS_EMC));
        addPlayerInventory(playerInventory);
    }

    @Override
    protected void broadcastPE(boolean all) {
        networkEmc.set(blockEntity.getEmc());
        sunLevel.set(blockEntity.getSunLevel());
        kleinChargeProgress.set(blockEntity.getChargeProgress());
        super.broadcastPE(all);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(blockEntity, player);
    }

    public long getNetworkEmc() {
        return networkEmc.get();
    }

    public int getSunLevel() {
        return sunLevel.get();
    }

    public int getKleinChargeProgress() {
        return kleinChargeProgress.get();
    }
}
