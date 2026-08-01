package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.AlchemicalChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ChestMenu extends AbstractContainerMenu {
    public final AlchemicalChestBlockEntity blockEntity;

    public ChestMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.CHEST.get(), containerId);
        this.blockEntity = (AlchemicalChestBlockEntity) playerInventory.player.level().getBlockEntity(pos);
        for (int i = 0; i < 7; i++) {
            addSlot(new Slot(blockEntity.getInventory(), i, 8 + i * 18, 18));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new Slot(blockEntity.getInventory(), 7 + i, 17 + i * 18, 36));
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = slots.get(slotIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        if (slotIndex < AlchemicalChestBlockEntity.SLOT_COUNT) {
            if (!moveItemStackTo(stack, AlchemicalChestBlockEntity.SLOT_COUNT, 49, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, AlchemicalChestBlockEntity.SLOT_COUNT, false)) return ItemStack.EMPTY;
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
