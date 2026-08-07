package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class DMFurnaceScreen extends AbstractFurnaceBaseScreen<MatterFurnaceContainer> {
    private static final Identifier BG_TEXTURE = EquivalentLegacy.rl("textures/gui/dmfurnace.png");

    public DMFurnaceScreen(MatterFurnaceContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, BG_TEXTURE);
    }
}
