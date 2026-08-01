package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public abstract class MachineScreen<T extends MachineMenu> extends AbstractContainerScreen<T> {
    private static final Identifier BG_TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    protected MachineScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int x = leftPos;
        int y = topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderSlotBoxes(graphics);
    }

    protected abstract void renderSlotBoxes(GuiGraphicsExtractor graphics);

    protected void drawSlotBox(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x, y, 7, 7, 18, 18, 256, 256);
    }

    protected void drawEmc(GuiGraphicsExtractor graphics, long emc) {
        graphics.text(font, "EMC: " + emc, 8, 6, 0x404040);
    }
}
