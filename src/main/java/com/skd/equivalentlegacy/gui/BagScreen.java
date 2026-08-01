package com.skd.equivalentlegacy.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BagScreen extends StorageScreen<BagMenu> {
    public BagScreen(BagMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
