package com.skd.equivalentlegacy.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ChestScreen extends StorageScreen<ChestMenu> {
    public ChestScreen(ChestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
