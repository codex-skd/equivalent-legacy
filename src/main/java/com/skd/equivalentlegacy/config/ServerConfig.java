package com.skd.equivalentlegacy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue COOLDOWN_PLAYER = BUILDER
            .comment("Tick cooldown between player EMC actions")
            .defineInRange("cooldown.player", 0, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue COOLDOWN_PEDESTAL = BUILDER
            .comment("Tick cooldown between pedestal EMC actions")
            .defineInRange("cooldown.pedestal", 0, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue MAX_VOXEL_PER_BREAK = BUILDER
            .comment("Maximum blocks broken per action with hammers/destruction catalyst")
            .defineInRange("maxVoxelPerBreak", 4096, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue COLLECTOR_SPEED = BUILDER
            .comment("Base EMC generation speed for collectors (MK1, MK2, MK3)")
            .defineInRange("collectorSpeed", 4, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
