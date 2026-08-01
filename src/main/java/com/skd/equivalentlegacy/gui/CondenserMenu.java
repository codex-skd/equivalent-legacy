package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.CondenserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CondenserMenu extends MachineMenu {
    public final CondenserBlockEntity blockEntity;
    private final DataSlot emcData = DataSlot.standalone();
    private final DataSlot chargeData = DataSlot.standalone();

    public CondenserMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.CONDENSER.get(), containerId);
        this.blockEntity = (CondenserBlockEntity) playerInventory.player.level().getBlockEntity(pos);
        addSlot(new Slot(blockEntity.getInventory(), CondenserBlockEntity.TARGET_SLOT, 44, 17));
        addSlot(new Slot(blockEntity.getInventory(), CondenserBlockEntity.KLEIN_SLOT, 80, 53));
        addSlot(new Slot(blockEntity.getInventory(), CondenserBlockEntity.OUTPUT_SLOT, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        addPlayerInventory(playerInventory);
        addDataSlot(emcData);
        addDataSlot(chargeData);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        var level = blockEntity.getLevel();
        if (level != null && !level.isClientSide()) {
            emcData.set((int) Math.min(Integer.MAX_VALUE, blockEntity.getEmc()));
            chargeData.set(blockEntity.getChargeProgress());
        }
    }

    public long getEmc() {
        return emcData.get();
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
        if (slotIndex < 3) {
            if (!moveItemStackTo(stack, 3, 39, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, CondenserBlockEntity.TARGET_SLOT, CondenserBlockEntity.KLEIN_SLOT + 1, false)) return ItemStack.EMPTY;
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
