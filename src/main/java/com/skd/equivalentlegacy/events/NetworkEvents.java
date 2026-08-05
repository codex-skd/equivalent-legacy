package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.EMCNetwork;
import com.skd.equivalentlegacy.emc.EMCNetworkData;
import com.skd.equivalentlegacy.emc.FixedValues;
import com.skd.equivalentlegacy.emc.RecipeMapper;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public final class NetworkEvents {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static boolean emcInitialized = false;

    private NetworkEvents() {}

    @SubscribeEvent
    static void onServerStarting(ServerStartingEvent event) {
        if (!emcInitialized && EMCHelper.hasMappings()) {
            try {
                FixedValues recipeValues = RecipeMapper.mapRecipes(
                    event.getServer().getRecipeManager().getRecipes()
                );
                if (!recipeValues.isEmpty()) {
                    EMCHelper.calculateFromConversions(recipeValues);
                    LOGGER.info("Calculated EMC values from recipes");
                }
                emcInitialized = true;
            } catch (Exception e) {
                LOGGER.warn("Failed to calculate EMC from recipes", e);
            }
        }

        for (ServerLevel level : event.getServer().getAllLevels()) {
            EMCNetworkData data = level.getDataStorage().computeIfAbsent(EMCNetworkData.TYPE);
            EMCNetwork.setEmc(level.dimension(), data.emc);
        }
    }

    @SubscribeEvent
    static void onServerStopping(ServerStoppingEvent event) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            EMCNetworkData data = level.getDataStorage().computeIfAbsent(EMCNetworkData.TYPE);
            data.emc = EMCNetwork.getEmc(level.dimension());
            data.setDirty();
        }
    }
}
