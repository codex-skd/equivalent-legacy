package com.skd.equivalentlegacy.rendering;

/**
 * HUD overlay shown while the player is performing an in-world block transmutation. Renders the
 * target block icon, EMC cost and a small headline. The actual on-screen draw is deferred (would call
 * into {@code RegisterGuiLayersEvent} on the client) until in-game feedback can validate the 26.x HUD
 * event pipeline.
 */
public final class TransmutationRenderingOverlay {
    public static final int FADE_TICKS = 20;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 26;

    private TransmutationRenderingOverlay() {}

    public static float computeAlpha(int ticksRemaining) {
        if (ticksRemaining <= 0) return 0.0F;
        if (ticksRemaining >= FADE_TICKS) return 1.0F;
        return ticksRemaining / (float) FADE_TICKS;
    }
}