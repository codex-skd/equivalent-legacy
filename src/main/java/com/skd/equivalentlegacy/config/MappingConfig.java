package com.skd.equivalentlegacy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class MappingConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DUMP_TO_FILE = BUILDER
            .comment("Dump EMC map to a JSON file on world load for debugging")
            .define("dumpToFile", false);

    public static final ModConfigSpec.BooleanValue USE_PREGENERATED = BUILDER
            .comment("Use pregenerated EMC values from datapack instead of calculating at runtime")
            .define("usePregenerated", false);

    public static final ModConfigSpec.BooleanValue LOG_EXPLOITS = BUILDER
            .comment("Log potential EMC exploit loops to the console")
            .define("logExploits", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
