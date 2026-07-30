package com.skd.equivalentlegacy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class CommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING = BUILDER
            .comment("Enable debug logging for the EMC system")
            .define("debugLogging", false);

    public static final ModConfigSpec.BooleanValue CRAFTABLE_TOME = BUILDER
            .comment("Whether the Tome (unlocks all knowledge) is craftable")
            .define("craftableTome", false);

    public static final ModConfigSpec.BooleanValue FULL_KLEIN_STARS = BUILDER
            .comment("Whether klein stars drop at their full EMC value when destroyed")
            .define("fullKleinStars", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
