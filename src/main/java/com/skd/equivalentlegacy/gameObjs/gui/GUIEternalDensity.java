package com.skd.equivalentlegacy.gameObjs.gui;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.container.EternalDensityContainer;
import com.skd.equivalentlegacy.network.packets.to_server.UpdateGemModePKT;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class GUIEternalDensity extends PEContainerScreen<EternalDensityContainer> {

	private static final ResourceLocation texture = ELCore.rl("textures/gui/eternal_density.png");

	public GUIEternalDensity(EternalDensityContainer container, Inventory inv, Component title) {
		super(container, inv, title);
		this.imageWidth = 180;
		this.imageHeight = 180;
	}

	@Override
	public void init() {
		super.init();
		addRenderableWidget(Button.builder((menu.isWhitelistMode() ? PELang.WHITELIST : PELang.BLACKLIST).translate(), b -> {
					boolean isWhitelistMode = !menu.isWhitelistMode();
					PacketDistributor.sendToServer(new UpdateGemModePKT(menu.hand, isWhitelistMode));
					b.setMessage(isWhitelistMode ? PELang.WHITELIST.translate() : PELang.BLACKLIST.translate());
				}).pos(leftPos + 62, topPos + 4)
				.size(52, 20)
				.build());
	}

	@Override
	protected void peExtractBackground(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		PEGuiGraphics.blit(graphics, texture, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}
}
