package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.RelayBlockEntity;
import com.skd.equivalentlegacy.gui.slots.SlotPredicates;
import com.skd.equivalentlegacy.gui.slots.ValidatedContainerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import org.jetbrains.annotations.NotNull;

public class RelayMenu extends PEContainer {
    public final RelayBlockEntity blockEntity;
    public final BoxedLong relayEmc = new BoxedLong();
    private final DataSlot chargeData = DataSlot.standalone();

    public RelayMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.RELAY.get(), containerId);
        this.blockEntity = (RelayBlockEntity) playerInventory.player.level().getBlockEntity(pos);

        longFields.add(relayEmc);
        addDataSlot(chargeData);
        addDataSlot(relayEmc.highSlot);
        addDataSlot(relayEmc.lowSlot);

        addSlot(new ValidatedContainerSlot(blockEntity.getInventory(), RelayBlockEntity.KLEIN_SLOT, 62, 17, SlotPredicates.HAS_EMC));
        addPlayerInventory(playerInventory);
    }

    @Override
    protected void broadcastPE(boolean all) {
        relayEmc.set(blockEntity.getEmc());
        chargeData.set(blockEntity.getChargeProgress());
        super.broadcastPE(all);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(blockEntity, player);
    }

    public long getEmc() {
        return relayEmc.get();
    }

    public int getChargeProgress() {
        return chargeData.get();
    }
}
