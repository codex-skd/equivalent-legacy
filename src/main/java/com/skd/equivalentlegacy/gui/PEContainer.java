package com.skd.equivalentlegacy.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class PEContainer extends MachineMenu {
    protected final List<BoxedLong> longFields = new ArrayList<>();

    protected PEContainer(MenuType<?> type, int windowId) {
        super(type, windowId);
    }

    protected void addPlayerInventory(Inventory playerInventory) {
        addPlayerInventory(playerInventory, 8, 84);
    }

    protected void addPlayerInventory(Inventory playerInventory, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, x + col * 18, y + 58));
        }
    }

    protected void broadcastPE(boolean all) {
        for (BoxedLong field : longFields) {
            field.sync(all);
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        broadcastPE(false);
    }

    @Override
    @NotNull
    public ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);
        ItemStack itemstack = ItemStack.EMPTY;
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotIndex < this.slots.size() - 36) {
                if (!this.moveItemStackTo(itemstack1, this.slots.size() - 36, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.slots.size() - 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
