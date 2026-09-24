package com.skd.equivalentlegacy.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class LevelHelper {

	private LevelHelper() {
	}

	public static void setWeather(@NotNull ServerLevel level, int durationTicks, boolean raining, boolean thundering) {
		level.setWeatherParameters(durationTicks, durationTicks, raining, thundering);
	}

	public static long getDefaultClockTime(@NotNull ServerLevel level) {
		return level.getDayTime();
	}

	public static void setDefaultClockTime(@NotNull ServerLevel level, long totalTicks) {
		level.setDayTime(totalTicks);
	}

	public static void addDefaultClockTicks(@NotNull ServerLevel level, long ticks) {
		setDefaultClockTime(level, Math.addExact(getDefaultClockTime(level), ticks));
	}

	@NotNull
	public static LightningBolt createLightning(@NotNull ServerLevel level, @NotNull Vec3 pos) {
		LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
		if (lightning == null) {
			throw new IllegalStateException("Failed to create lightning bolt");
		}
		lightning.setPos(pos.x, pos.y, pos.z);
		return lightning;
	}
}
