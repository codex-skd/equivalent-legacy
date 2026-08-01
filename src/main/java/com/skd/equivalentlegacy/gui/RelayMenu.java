package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.RelayBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RelayMenu extends MachineMenu {
    public final RelayBlockEntity blockEntity;
    private final DataSlot emcData = DataSlot.standalone();
    private final DataSlot chargeData = DataSlot.standalone();

    public RelayMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.RELAY.get(), containerId);
        this.blockEntity = (RelayBlockEntity) playerInventory.player.level().getBlockEntity(pos);
        addSlot(new Slot(blockEntity.getInventory(), RelayBlockEntity.KLEIN_SLOT, 62, 17));
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
        if (slotIndex == RelayBlockEntity.KLEIN_SLOT) {
            if (!moveItemStackTo(stack, 1, 37, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, RelayBlockEntity.KLEIN_SLOT, RelayBlockEntity.KLEIN_SLOT + 1, false)) return ItemStack.EMPTY;
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
