package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractFurnaceBaseScreen<T extends MatterFurnaceContainer> extends AbstractContainerScreen<T> {
    private static final Identifier BG_TEXTURE_FALLBACK = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");
    private final Identifier bgTexture;

    protected AbstractFurnaceBaseScreen(T menu, Inventory playerInventory, Component title, Identifier bgTexture) {
        super(menu, playerInventory, title, 176, 192);
        this.bgTexture = bgTexture;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int x = leftPos;
        int y = topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE_FALLBACK, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderSlotBoxes(graphics);
        renderProgressArrow(graphics);
    }

    protected void renderSlotBoxes(GuiGraphicsExtractor graphics) {
        int x = leftPos;
        int y = topPos;
        drawSlotBox(graphics, x + 26, y + 17);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                drawSlotBox(graphics, x + 8 + col * 18, y + 44 + row * 18);
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                drawSlotBox(graphics, x + 116 + col * 18, y + 44 + row * 18);
            }
        }
    }

    protected void renderProgressArrow(GuiGraphicsExtractor graphics) {
        int progress = menu.getCookingProgress();
        int total = menu.getCookingTotal();
        if (total > 0 && progress > 0) {
            int arrowWidth = (int) (22f * progress / total);
            int x = leftPos + 80;
            int y = topPos + 45;
            graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE_FALLBACK, x, y, 14, 7, arrowWidth, 8, 256, 256);
        }
    }

    protected void drawSlotBox(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE_FALLBACK, x, y, 7, 7, 18, 18, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        graphics.text(font, Component.translatable("gui.equivalent_legacy.furnace.fuel_emc"),
                leftPos + 8, topPos + 6, 0x404040);
        graphics.text(font, Component.translatable("gui.equivalent_legacy.furnace.emc_cost", menu.getEmcCost()),
                leftPos + 62, topPos + 6, 0x404040);
        graphics.text(font, Component.translatable("gui.equivalent_legacy.furnace.input"),
                leftPos + 8, topPos + 32, 0x404040);
        graphics.text(font, Component.translatable("gui.equivalent_legacy.furnace.output"),
                leftPos + 116, topPos + 32, 0x404040);
    }
}
