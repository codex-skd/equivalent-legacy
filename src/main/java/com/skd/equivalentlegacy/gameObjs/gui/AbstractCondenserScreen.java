package com.skd.equivalentlegacy.gameObjs.gui;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.container.CondenserContainer;
import com.skd.equivalentlegacy.gameObjs.container.CondenserMK2Container;
import com.skd.equivalentlegacy.utils.EMCHelper;
import com.skd.equivalentlegacy.utils.TransmutationEMCFormatter;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCondenserScreen<T extends CondenserContainer> extends PEContainerScreen<T> {

	public AbstractCondenserScreen(T condenser, Inventory playerInventory, Component title) {
		super(condenser, playerInventory, title);
		this.imageWidth = 255;
		this.imageHeight = 233;
	}

	protected abstract ResourceLocation getTexture();

	@Override
	protected void peExtractBackground(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		PEGuiGraphics.blit(graphics, getTexture(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
		int progress = menu.getProgressScaled();
		PEGuiGraphics.blit(graphics, getTexture(), leftPos + 33, topPos + 10, 0, 235, progress, 10);
	}

	@Override
	protected void peExtractLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		long display = menu.displayEmc.get();
		long required = menu.requiredEmc.get();
		long toDisplay = required > 0 ? Math.min(display, required) : display;
		Component emc = TransmutationEMCFormatter.formatEMC(toDisplay);
		graphics.drawString(font, emc, 140, 10, PEGuiGraphics.LABEL_COLOR, false);
	}

	@Override
	protected void renderTooltip(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
		long display = menu.displayEmc.get();
		long required = menu.requiredEmc.get();
		long toDisplay = required > 0 ? Math.min(display, required) : display;

		if (toDisplay < 1e12) {
			super.renderTooltip(graphics, mouseX, mouseY);
			return;
		}

		int emcLeft = 140 + leftPos;
		int emcRight = emcLeft + 110;
		int emcTop = 6 + topPos;
		int emcBottom = emcTop + 15;

		if (mouseX > emcLeft && mouseX < emcRight && mouseY > emcTop && mouseY < emcBottom) {
			graphics.renderTooltip(font, PELang.EMC_TOOLTIP.translate(EMCHelper.formatEmc(toDisplay)), mouseX, mouseY);
		} else {
			super.renderTooltip(graphics, mouseX, mouseY);
		}
	}

	public static class MK1 extends AbstractCondenserScreen<CondenserContainer> {

		public MK1(CondenserContainer condenser, Inventory playerInventory, Component title) {
			super(condenser, playerInventory, title);
		}

		@Override
		protected ResourceLocation getTexture() {
			return ELCore.rl("textures/gui/condenser.png");
		}
	}

	public static class MK2 extends AbstractCondenserScreen<CondenserMK2Container> {

		public MK2(CondenserMK2Container condenser, Inventory playerInventory, Component title) {
			super(condenser, playerInventory, title);
		}

		@Override
		protected ResourceLocation getTexture() {
			return ELCore.rl("textures/gui/condenser_mk2.png");
		}
	}
}
