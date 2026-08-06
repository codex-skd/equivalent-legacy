package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.item.AlchemicalBag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BagMenu extends PEContainer {
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
        addPlayerInventory(playerInventory, 8, 84);
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.player == player && player.getItemInHand(hand).getItem() instanceof AlchemicalBag;
    }
}
