package com.skd.equivalentlegacy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue EMC_TOOLTIPS = BUILDER
            .comment("Show EMC values in item tooltips")
            .define("emcToolTips", true);

    public static final ModConfigSpec.BooleanValue SHIFT_EMC_TOOLTIPS = BUILDER
            .comment("Only show EMC tooltips when holding shift")
            .define("shiftEmcToolTips", false);

    public static final ModConfigSpec.BooleanValue PULSATING_OVERLAY = BUILDER
            .comment("Show pulsating overlay on blocks with EMC storage")
            .define("pulsatingOverlay", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
