package com.skd.equivalentlegacy.rendering;

import net.minecraft.world.phys.Vec3;

/**
 * Renders an item floating above a pedestal block entity, with slow rotation around the Y axis and
 * a gentle bobbing motion. This is a placeholder renderer descriptor; the actual /submitNodeCollector/
 * pipeline of NeoForge 26.x is intentionally not wired in this beta. Visual tuning requires in-game
 * verification which is deferred to a future build.
 * <p>Companion methods: {@code PedestalBlockEntity.getItemRenderPos()} /
 * {@code PedestalBlockEntity.getItemRenderRotation()}.</p>
 */
public final class PedestalRenderer {
    private PedestalRenderer() {}

    public static final float BASE_OFFSET = 0.75F;
    public static final float BOB_AMPLITUDE = 0.06F;
    public static final float ROTATION_PER_TICK = 2.0F;
    public static final Vec3 ROTATION_AXIS = new Vec3(0.0, 1.0, 0.0);

    public static Vec3 computeItemOffset(int tickCounter, Vec3 blockCenter) {
        double bob = BOB_AMPLITUDE * Math.sin(tickCounter * 0.05);
        return blockCenter.add(0.0, BASE_OFFSET + bob, 0.0);
    }

    public static Vec3 rotationAxis() {
        return ROTATION_AXIS;
    }
}