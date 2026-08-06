package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.CondenserBlockEntity;
import com.skd.equivalentlegacy.gui.slots.SlotPredicates;
import com.skd.equivalentlegacy.gui.slots.ValidatedContainerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CondenserMenu extends PEContainer {
    public final CondenserBlockEntity blockEntity;
    public final BoxedLong condenseEmc = new BoxedLong();
    private final DataSlot chargeData = DataSlot.standalone();

    public CondenserMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.CONDENSER.get(), containerId);
        this.blockEntity = (CondenserBlockEntity) playerInventory.player.level().getBlockEntity(pos);

        longFields.add(condenseEmc);
        addDataSlot(chargeData);
        addDataSlot(condenseEmc.highSlot);
        addDataSlot(condenseEmc.lowSlot);

        var inventory = blockEntity.getInventory();
        addSlot(new ValidatedContainerSlot(inventory, CondenserBlockEntity.TARGET_SLOT, 44, 17, SlotPredicates.HAS_EMC));
        addSlot(new ValidatedContainerSlot(inventory, CondenserBlockEntity.KLEIN_SLOT, 80, 53, SlotPredicates.HAS_EMC));
        addSlot(new Slot(inventory, CondenserBlockEntity.OUTPUT_SLOT, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
    }

    @Override
    protected void broadcastPE(boolean all) {
        condenseEmc.set(blockEntity.getEmc());
        chargeData.set(blockEntity.getChargeProgress());
        super.broadcastPE(all);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(blockEntity, player);
    }

    public long getEmc() {
        return condenseEmc.get();
    }

    public int getChargeProgress() {
        return chargeData.get();
    }
}
