package com.skd.equivalentlegacy.gameObjs.gui;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.container.MercurialEyeContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GUIMercurialEye extends PEContainerScreen<MercurialEyeContainer> {

	private static final ResourceLocation texture = ELCore.rl("textures/gui/mercurial_eye.png");

	public GUIMercurialEye(MercurialEyeContainer container, Inventory invPlayer, Component title) {
		super(container, invPlayer, title);
		this.imageWidth = 171;
		this.imageHeight = 134;
	}

	@Override
	protected void peExtractBackground(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		PEGuiGraphics.blit(graphics, texture, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}
}
