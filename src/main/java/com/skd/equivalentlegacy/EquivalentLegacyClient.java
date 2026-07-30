package com.skd.equivalentlegacy;

import com.skd.equivalentlegacy.gui.ModMenuTypes;
import com.skd.equivalentlegacy.gui.TransmutationScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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
    }
}
