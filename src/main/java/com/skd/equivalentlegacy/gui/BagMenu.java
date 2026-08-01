package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.item.AlchemicalBag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BagMenu extends AbstractContainerMenu {
    private final ItemStackHandler inventory;
    private final Player player;
    private final InteractionHand hand;

    public BagMenu(int containerId, Inventory playerInventory, InteractionHand hand) {
        super(ModMenuTypes.BAG.get(), containerId);
        this.player = playerInventory.player;
        this.hand = hand;
        this.inventory = AlchemicalBag.createInventory(player.getItemInHand(hand));

        for (int i = 0; i < 7; i++) {
            addSlot(new SlotItemHandler(inventory, i, 8 + i * 18, 18));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new SlotItemHandler(inventory, 7 + i, 17 + i * 18, 36));
        }
        addPlayerInventory(playerInventory);
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = slots.get(slotIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        if (slotIndex < AlchemicalBag.SLOT_COUNT) {
            if (!moveItemStackTo(stack, AlchemicalBag.SLOT_COUNT, 49, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, AlchemicalBag.SLOT_COUNT, false)) return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.player == player && player.getItemInHand(hand).getItem() instanceof AlchemicalBag;
    }
}
