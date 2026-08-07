package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.AlchemicalChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChestMenu extends PEContainer {
    public static final int COLS = 13;
    public static final int ROWS = 8;
    public static final int CHEST_SLOTS = COLS * ROWS;
    public static final int CHEST_X = 8;
    public static final int CHEST_Y = 18;
    public static final int PLAYER_X = 44;
    public static final int PLAYER_Y = 176;
    public static final int HOTBAR_Y = 234;

    public final AlchemicalChestBlockEntity blockEntity;
    private final DataSlot openness = DataSlot.standalone();

    public ChestMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.CHEST.get(), containerId);
        this.blockEntity = (AlchemicalChestBlockEntity) playerInventory.player.level().getBlockEntity(pos);

        addDataSlot(openness);

        if (blockEntity != null) {
            var inventory = blockEntity.getInventory();
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    addSlot(new Slot(inventory, row * COLS + col, CHEST_X + col * 18, CHEST_Y + row * 18));
                }
            }

            if (!playerInventory.player.level().isClientSide()) {
                blockEntity.startOpen(playerInventory.player);
            }
        }

        addPlayerInventory(playerInventory, PLAYER_X, PLAYER_Y, HOTBAR_Y);
    }

    private void addPlayerInventory(Inventory playerInventory, int invX, int invY, int hotbarY) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, invX + col * 18, invY + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, invX + col * 18, hotbarY));
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (blockEntity != null && !player.level().isClientSide()) {
            blockEntity.stopOpen(player);
        }
    }

    @Override
    protected void broadcastPE(boolean all) {
        if (blockEntity != null) {
            openness.set(blockEntity.getOpenNess());
        }
        super.broadcastPE(all);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return blockEntity != null && Container.stillValidBlockEntity(blockEntity, player);
    }

    @Override
    @NotNull
    public ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (slotIndex < CHEST_SLOTS) {
            if (!this.moveItemStackTo(stack, CHEST_SLOTS, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.moveItemStackTo(stack, 0, CHEST_SLOTS, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;
    }

    public int getOpenness() {
        return openness.get();
    }
}
