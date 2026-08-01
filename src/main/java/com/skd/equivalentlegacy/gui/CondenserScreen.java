package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CondenserScreen extends MachineScreen<CondenserMenu> {
    public CondenserScreen(CondenserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderSlotBoxes(GuiGraphicsExtractor graphics) {
        drawSlotBox(graphics, leftPos + 44, topPos + 17);
        drawSlotBox(graphics, leftPos + 80, topPos + 53);
        drawSlotBox(graphics, leftPos + 116, topPos + 35);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        drawEmc(graphics, menu.getEmc());
        String target = menu.blockEntity.getTargetId();
        graphics.text(font, "Target: " + (target == null ? "none" : target), 8, 16, 0x404040);
    }
}
