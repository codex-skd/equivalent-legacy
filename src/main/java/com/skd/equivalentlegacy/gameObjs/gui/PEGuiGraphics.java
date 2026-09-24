package com.skd.equivalentlegacy.gameObjs.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class PEGuiGraphics {

	private static final int DEFAULT_TEXTURE_SIZE = 256;
	public static final int LABEL_COLOR = 0x404040;

	private PEGuiGraphics() {
	}

	public static void blit(@NotNull GuiGraphics graphics, @NotNull ResourceLocation texture, int x, int y, int u, int v, int width, int height) {
		graphics.blit(texture, x, y, u, v, width, height, DEFAULT_TEXTURE_SIZE, DEFAULT_TEXTURE_SIZE);
	}

	public static void blit(@NotNull GuiGraphics graphics, @NotNull ResourceLocation texture, int x, int y, int u, int v, int width, int height,
			int textureWidth, int textureHeight) {
		graphics.blit(texture, x, y, u, v, width, height, textureWidth, textureHeight);
	}
}
