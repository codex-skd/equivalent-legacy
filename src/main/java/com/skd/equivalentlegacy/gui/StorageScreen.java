package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class StorageScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private static final Identifier BG_TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");
    private static final int SLOT_COUNT = 13;

    protected StorageScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int x = leftPos;
        int y = topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        for (int i = 0; i < 7; i++) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x + 8 + i * 18, y + 18, 7, 7, 18, 18, 256, 256);
        }
        for (int i = 0; i < 6; i++) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x + 17 + i * 18, y + 36, 7, 7, 18, 18, 256, 256);
        }
    }
}
