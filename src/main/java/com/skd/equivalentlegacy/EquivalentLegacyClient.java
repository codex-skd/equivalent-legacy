package com.skd.equivalentlegacy;

import com.skd.equivalentlegacy.gui.BagScreen;
import com.skd.equivalentlegacy.gui.ChestScreen;
import com.skd.equivalentlegacy.gui.CondenserScreen;
import com.skd.equivalentlegacy.gui.CollectorScreen;
import com.skd.equivalentlegacy.gui.MatterFurnaceScreen;
import com.skd.equivalentlegacy.gui.ModMenuTypes;
import com.skd.equivalentlegacy.gui.RelayScreen;
import com.skd.equivalentlegacy.gui.TransmutationScreen;
import com.skd.equivalentlegacy.EquivalentLegacyEntities;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = EquivalentLegacy.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = EquivalentLegacy.MODID, value = Dist.CLIENT)
public class EquivalentLegacyClient {
    public EquivalentLegacyClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EquivalentLegacy.LOGGER.info("Equivalent Legacy client setup");
    }

    @SubscribeEvent
    static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.TRANSMUTATION.get(), TransmutationScreen::new);
        event.register(ModMenuTypes.COLLECTOR.get(), CollectorScreen::new);
        event.register(ModMenuTypes.RELAY.get(), RelayScreen::new);
        event.register(ModMenuTypes.CONDENSER.get(), CondenserScreen::new);
        event.register(ModMenuTypes.BAG.get(), BagScreen::new);
        event.register(ModMenuTypes.CHEST.get(), ChestScreen::new);
        event.register(ModMenuTypes.FURNACE.get(), MatterFurnaceScreen::new);
    }

    @SubscribeEvent
    static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EquivalentLegacyEntities.NOVA.get(), NoopRenderer::new);
        event.registerEntityRenderer(EquivalentLegacyEntities.NOVA_CATALYST.get(), NoopRenderer::new);
        event.registerEntityRenderer(EquivalentLegacyEntities.NOVA_CATACLYSM.get(), NoopRenderer::new);
    }
}
