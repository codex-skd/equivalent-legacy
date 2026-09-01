package com.skd.equivalentlegacy.gameObjs.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public abstract class PEContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

	public boolean switchingToJEI;

	public PEContainerScreen(T container, Inventory invPlayer, Component title) {
		super(container, invPlayer, title);
	}

	@Override
	public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		peExtractBackground(graphics, partialTicks, mouseX, mouseY);
	}

	protected void peExtractBackground(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		peExtractLabels(graphics, mouseX, mouseY);
	}

	protected void peExtractLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
	}

	@Override
	public void removed() {
		if (!switchingToJEI) {
			super.removed();
		}
	}

	@Override
	public void init() {
		switchingToJEI = false;
		super.init();
	}
}
