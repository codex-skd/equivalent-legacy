package com.skd.equivalentlegacy.rendering;

/**
 * Front-door for registering all custom client-side renderers used by Equivalent Legacy: pedestal
 * BlockEntity renderer, alchemical chest BlockEntity renderer, and the world transmutation HUD overlay.
 * <p>This beta intentionally /does not/ wire the renderers into the NeoForge 26.x
 * {@code BlockEntityRenderer}/{@code SubmitNodeCollector} pipeline or the HUD
 * {@code RegisterGuiLayersEvent} because visual tuning needs in-game validation that is deferred
 * to a future release. Callers should treat {@code init()} as a no-op until visual tuning is done.</p>
 */
public final class EquivalentLegacyRenderers {
    private EquivalentLegacyRenderers() {}

    public static void init() {
    }
}