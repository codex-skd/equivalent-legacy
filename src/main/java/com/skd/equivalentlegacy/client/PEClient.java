package com.skd.equivalentlegacy.client;

import mezz.jei.api.runtime.IRecipesGui;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.container.DMFurnaceContainer;
import com.skd.equivalentlegacy.gameObjs.entity.EntitySWRGProjectile;
import com.skd.equivalentlegacy.gameObjs.gui.AbstractCollectorScreen;
import com.skd.equivalentlegacy.gameObjs.gui.AbstractCondenserScreen;
import com.skd.equivalentlegacy.gameObjs.gui.AlchBagScreen;
import com.skd.equivalentlegacy.gameObjs.gui.AlchChestScreen;
import com.skd.equivalentlegacy.gameObjs.gui.GUIDMFurnace;
import com.skd.equivalentlegacy.gameObjs.gui.GUIEternalDensity;
import com.skd.equivalentlegacy.gameObjs.gui.GUIMercurialEye;
import com.skd.equivalentlegacy.gameObjs.gui.GUIRMFurnace;
import com.skd.equivalentlegacy.gameObjs.gui.GUIRelay.GUIRelayMK1;
import com.skd.equivalentlegacy.gameObjs.gui.GUIRelay.GUIRelayMK2;
import com.skd.equivalentlegacy.gameObjs.gui.GUIRelay.GUIRelayMK3;
import com.skd.equivalentlegacy.gameObjs.gui.GUIArcaneTablet;
import com.skd.equivalentlegacy.gameObjs.gui.GUITransmutation;
import com.skd.equivalentlegacy.gameObjs.gui.PEContainerScreen;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlocks;
import com.skd.equivalentlegacy.gameObjs.registries.PEContainerTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEEntityTypes;
import com.skd.equivalentlegacy.gameObjs.sound.MovingSoundSWRG;
import com.skd.equivalentlegacy.network.commands.client.DumpMissingEmc;
import com.skd.equivalentlegacy.rendering.ChestRenderer;
import com.skd.equivalentlegacy.rendering.EntitySpriteRenderer;
import com.skd.equivalentlegacy.rendering.LayerYue;
import com.skd.equivalentlegacy.rendering.PedestalRenderer;
import com.skd.equivalentlegacy.rendering.TransmutationRenderingOverlay;
import com.skd.equivalentlegacy.utils.ClientKeyHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@Mod(value = ELCore.MODID, dist = Dist.CLIENT)
public class PEClient {

	public PEClient(ModContainer container, IEventBus modEventBus) {
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		modEventBus.addListener(this::registerScreens);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::registerKeybindings);
		modEventBus.addListener(this::registerOverlays);
		modEventBus.addListener(this::registerRenderers);
		modEventBus.addListener(this::addLayers);

		NeoForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
		NeoForge.EVENT_BUS.addListener(this::registerClientCommands);
	}

	private void onEntityJoinWorld(EntityJoinLevelEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (event.getEntity() instanceof EntitySWRGProjectile projectile && mc.mouseHandler.isMouseGrabbed()) {
			mc.getSoundManager().play(new MovingSoundSWRG(projectile, event.getLevel().random));
		}
	}

	private void registerClientCommands(RegisterClientCommandsEvent event) {
		CommandBuildContext context = event.getBuildContext();
		event.getDispatcher().register(Commands.literal("equivalent_legacy")
				.then(DumpMissingEmc.register(context))
		);
	}

	private void registerScreens(RegisterMenuScreensEvent event) {
		event.register(PEContainerTypes.RM_FURNACE_CONTAINER.get(), GUIRMFurnace::new);
		//noinspection RedundantTypeArguments (necessary for it to actually compile)
		event.<DMFurnaceContainer, GUIDMFurnace<DMFurnaceContainer>>register(PEContainerTypes.DM_FURNACE_CONTAINER.get(), GUIDMFurnace::new);
		event.register(PEContainerTypes.CONDENSER_CONTAINER.get(), AbstractCondenserScreen.MK1::new);
		event.register(PEContainerTypes.CONDENSER_MK2_CONTAINER.get(), AbstractCondenserScreen.MK2::new);
		event.register(PEContainerTypes.ALCH_CHEST_CONTAINER.get(), AlchChestScreen::new);
		event.register(PEContainerTypes.ALCH_BAG_CONTAINER.get(), AlchBagScreen::new);
		event.register(PEContainerTypes.ETERNAL_DENSITY_CONTAINER.get(), GUIEternalDensity::new);
		event.register(PEContainerTypes.TRANSMUTATION_CONTAINER.get(), GUITransmutation::new);
		event.register(PEContainerTypes.ARCANE_TABLET_CONTAINER.get(), GUIArcaneTablet::new);
		event.register(PEContainerTypes.RELAY_MK1_CONTAINER.get(), GUIRelayMK1::new);
		event.register(PEContainerTypes.RELAY_MK2_CONTAINER.get(), GUIRelayMK2::new);
		event.register(PEContainerTypes.RELAY_MK3_CONTAINER.get(), GUIRelayMK3::new);
		event.register(PEContainerTypes.COLLECTOR_MK1_CONTAINER.get(), AbstractCollectorScreen.MK1::new);
		event.register(PEContainerTypes.COLLECTOR_MK2_CONTAINER.get(), AbstractCollectorScreen.MK2::new);
		event.register(PEContainerTypes.COLLECTOR_MK3_CONTAINER.get(), AbstractCollectorScreen.MK3::new);
		event.register(PEContainerTypes.MERCURIAL_EYE_CONTAINER.get(), GUIMercurialEye::new);
	}

	private void clientSetup(FMLClientSetupEvent evt) {
		if (ModList.get().isLoaded("jei")) {
			//Note: This listener is only registered if JEI is loaded
			NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, (ScreenEvent.Opening event) -> {
				if (event.getCurrentScreen() instanceof PEContainerScreen<?> screen) {
					//If JEI is loaded and our current screen is a EquivalentLegacy gui,
					// check if the new screen is a JEI recipe screen
					if (event.getNewScreen() instanceof IRecipesGui) {
						//If it is mark on our current screen that we are switching to JEI
						screen.switchingToJEI = true;
					}
				}
			});
		}
	}

	private void registerKeybindings(RegisterKeyMappingsEvent event) {
		ClientKeyHelper.registerKeyBindings(event);
	}

	private void registerOverlays(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CROSSHAIR, ELCore.rl("transmutation_result"), new TransmutationRenderingOverlay());
	}

	private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		//Block Entity
		event.registerBlockEntityRenderer(PEBlockEntityTypes.ALCHEMICAL_CHEST.get(), context -> new ChestRenderer(context, ELCore.rl("textures/block/alchemical_chest.png"), PEBlocks.ALCHEMICAL_CHEST));
		event.registerBlockEntityRenderer(PEBlockEntityTypes.CONDENSER.get(), context -> new ChestRenderer(context, ELCore.rl("textures/block/condenser_mk1.png"), PEBlocks.CONDENSER));
		event.registerBlockEntityRenderer(PEBlockEntityTypes.CONDENSER_MK2.get(), context -> new ChestRenderer(context, ELCore.rl("textures/block/condenser_mk2.png"), PEBlocks.CONDENSER_MK2));
		event.registerBlockEntityRenderer(PEBlockEntityTypes.DARK_MATTER_PEDESTAL.get(), PedestalRenderer::new);

		//Entities
		event.registerEntityRenderer(PEEntityTypes.WATER_PROJECTILE.get(), context -> new EntitySpriteRenderer<>(context, ELCore.rl("textures/entity/water_orb.png")));
		event.registerEntityRenderer(PEEntityTypes.LAVA_PROJECTILE.get(), context -> new EntitySpriteRenderer<>(context, ELCore.rl("textures/entity/lava_orb.png")));
		event.registerEntityRenderer(PEEntityTypes.MOB_RANDOMIZER.get(), context -> new EntitySpriteRenderer<>(context, ELCore.rl("textures/entity/randomizer.png")));
		event.registerEntityRenderer(PEEntityTypes.LENS_PROJECTILE.get(), context -> new EntitySpriteRenderer<>(context, ELCore.rl("textures/entity/lens_explosive.png")));
		event.registerEntityRenderer(PEEntityTypes.FIRE_PROJECTILE.get(), context -> new EntitySpriteRenderer<>(context, ELCore.rl("textures/entity/fireball.png")));
		event.registerEntityRenderer(PEEntityTypes.SWRG_PROJECTILE.get(), context -> new EntitySpriteRenderer<>(context, ELCore.rl("textures/entity/lightning.png")));
		event.registerEntityRenderer(PEEntityTypes.NOVA_CATALYST_PRIMED.get(), TntRenderer::new);
		event.registerEntityRenderer(PEEntityTypes.NOVA_CATACLYSM_PRIMED.get(), TntRenderer::new);
		@SuppressWarnings({"unchecked", "rawtypes"})
		EntityRendererProvider genericProvider = (EntityRendererProvider) TippableArrowRenderer::new;
		event.registerEntityRenderer(PEEntityTypes.HOMING_ARROW.get(), genericProvider);
	}

	private void addLayers(EntityRenderersEvent.AddLayers event) {
		for (PlayerSkin.Model model : event.getSkins()) {
			if (event.getSkin(model) instanceof PlayerRenderer skin) {
				skin.addLayer(new LayerYue(skin));
			}
		}
	}
}
