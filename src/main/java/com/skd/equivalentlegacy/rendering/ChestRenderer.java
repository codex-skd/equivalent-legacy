package com.skd.equivalentlegacy.rendering;

/**
 * Renders the alchemical chest with an open/close animation. Like PedestalRenderer, this is a
 * placeholder descriptor; the actual /submitNodeCollector/ pipeline is intentionally not wired in
 * this beta.
 */
public final class ChestRenderer {
    private ChestRenderer() {}

    public static final float LID_OPEN_DEGREES = 90.0F;
    public static final int ANIMATION_TICKS = 5;

    public static float computeLidAngle(int ticksOpen) {
        float t = Math.min(1.0F, ticksOpen / (float) ANIMATION_TICKS);
        return LID_OPEN_DEGREES * t;
    }
}