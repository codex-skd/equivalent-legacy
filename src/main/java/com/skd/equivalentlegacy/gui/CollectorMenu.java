package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.CollectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CollectorMenu extends MachineMenu {
    public final CollectorBlockEntity blockEntity;
    private final DataSlot emcData = DataSlot.standalone();
    private final DataSlot sunData = DataSlot.standalone();
    private final DataSlot chargeData = DataSlot.standalone();

    public CollectorMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.COLLECTOR.get(), containerId);
        this.blockEntity = (CollectorBlockEntity) playerInventory.player.level().getBlockEntity(pos);
        addSlot(new Slot(blockEntity.getInventory(), CollectorBlockEntity.KLEIN_SLOT, 62, 17));
        addPlayerInventory(playerInventory);
        addDataSlot(emcData);
        addDataSlot(sunData);
        addDataSlot(chargeData);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        var level = blockEntity.getLevel();
        if (level != null && !level.isClientSide()) {
            emcData.set((int) Math.min(Integer.MAX_VALUE, blockEntity.getEmc()));
            sunData.set(blockEntity.getSunLevel());
            chargeData.set(blockEntity.getChargeProgress());
        }
    }

    public long getEmc() {
        return emcData.get();
    }

    public int getSunLevel() {
        return sunData.get();
    }

    public int getChargeProgress() {
        return chargeData.get();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = slots.get(slotIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        if (slotIndex == CollectorBlockEntity.KLEIN_SLOT) {
            if (!moveItemStackTo(stack, 1, 37, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, CollectorBlockEntity.KLEIN_SLOT, CollectorBlockEntity.KLEIN_SLOT + 1, false)) return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }
}
