package com.skd.equivalentlegacy.gameObjs.gui;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.container.AlchChestContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class AlchChestScreen extends PEContainerScreen<AlchChestContainer> {

	private static final ResourceLocation texture = ELCore.rl("textures/gui/alchchest.png");

	public AlchChestScreen(AlchChestContainer container, Inventory invPlayer, Component title) {
		super(container, invPlayer, title);
		this.imageWidth = 255;
		this.imageHeight = 230;
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(font, title, (imageWidth - font.width(title)) / 2, 4, PEGuiGraphics.LABEL_COLOR, false);
		graphics.drawString(font, playerInventoryTitle, 48, 140, PEGuiGraphics.LABEL_COLOR, false);
	}

	@Override
	protected void peExtractBackground(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		PEGuiGraphics.blit(graphics, texture, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}
}
