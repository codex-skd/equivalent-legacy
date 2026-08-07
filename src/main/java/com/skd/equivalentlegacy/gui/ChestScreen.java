package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class ChestScreen extends AbstractContainerScreen<ChestMenu> {
    private static final Identifier BG_TEXTURE = Identifier.parse("equivalent_legacy:textures/gui/alchchest");
    private static final int COLS = 13;
    private static final int ROWS = 8;
    private static final int TEX_WIDTH = 256;
    private static final int TEX_HEIGHT = 256;

    public ChestScreen(ChestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 250, 256);
        this.inventoryLabelX = ChestMenu.PLAYER_X;
        this.inventoryLabelY = ChestMenu.PLAYER_Y - 11;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int x = leftPos;
        int y = topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, TEX_WIDTH, TEX_HEIGHT);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE,
                        x + ChestMenu.CHEST_X + col * 18, y + ChestMenu.CHEST_Y + row * 18,
                        7, 7, 18, 18, TEX_WIDTH, TEX_HEIGHT);
            }
        }
    }
}
