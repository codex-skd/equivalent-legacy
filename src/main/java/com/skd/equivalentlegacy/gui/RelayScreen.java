package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RelayScreen extends MachineScreen<RelayMenu> {
    public RelayScreen(RelayMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderSlotBoxes(GuiGraphicsExtractor graphics) {
        drawSlotBox(graphics, leftPos + 62, topPos + 17);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        drawEmc(graphics, menu.getEmc());
    }
}
