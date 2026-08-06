package com.skd.equivalentlegacy.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SpawnerConfigScreen extends Screen {
    private int delay = 20;
    private int spawnCount = 4;
    private int maxNearby = 6;

    public SpawnerConfigScreen() {
        super(Component.literal("Spawner Config"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.text(font, this.title.getString(), centerX - 30, centerY - 100, 0xFFFFFF);
        graphics.text(font, "Delay: " + delay, centerX - 100, centerY - 50, 0xFFFFFF);
        graphics.text(font, "Spawn Count: " + spawnCount, centerX - 100, centerY - 20, 0xFFFFFF);
        graphics.text(font, "Max Nearby: " + maxNearby, centerX - 100, centerY + 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
