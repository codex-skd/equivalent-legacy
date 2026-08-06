package com.skd.equivalentlegacy.rendering;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

/**
 * Central registration point for Equivalent Legacy's client-side renderers:
 * <ul>
 *   <li>{@link PedestalRenderer} is registered for the base, dark-matter and red-matter pedestal block
 *       entities (floating, rotating items).</li>
 *   <li>The world-transmutation HUD overlay is registered as a {@code GuiLayer} via
 *       {@link RegisterGuiLayersEvent} and handled by {@link TransmutationRenderingOverlay}.</li>
 * </ul>
 * <p>The alchemical-chest lid animation ({@link ChestRenderer}) intentionally remains a placeholder:
 * animating the lid requires {@code AlchemicalChestBlockEntity} to implement {@code LidBlockEntity}
 * plus a custom chest-texture atlas sprite that the current asset pipeline does not provide yet.
 * See {@code docs/WORKFLOW_EQUIVALENT_LEGACY_26-2.md} and the Phase 2.2 plan for the deferred items.</p>
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = EquivalentLegacy.MODID, value = Dist.CLIENT)
public final class EquivalentLegacyRenderers {
    private EquivalentLegacyRenderers() {}

    @SubscribeEvent
    static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EquivalentLegacyBlockEntities.PEDESTAL.get(), PedestalRenderer::new);
        event.registerBlockEntityRenderer(EquivalentLegacyBlockEntities.DM_PEDESTAL.get(), PedestalRenderer::new);
        event.registerBlockEntityRenderer(EquivalentLegacyBlockEntities.RM_PEDESTAL.get(), PedestalRenderer::new);
        event.registerBlockEntityRenderer(EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST.get(), ChestRenderer::new);
    }

    @SubscribeEvent
    static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
                EquivalentLegacy.rl("transmutation_overlay"),
                TransmutationRenderingOverlay::render
        );
    }
}