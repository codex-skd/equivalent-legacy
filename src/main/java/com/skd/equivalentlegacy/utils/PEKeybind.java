package com.skd.equivalentlegacy.utils;

import io.netty.buffer.ByteBuf;
import java.util.Locale;
import java.util.function.IntFunction;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.utils.text.IHasTranslationKey.IHasEnumNameTranslationKey;
import net.minecraft.Util;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

public enum PEKeybind implements IHasEnumNameTranslationKey {
	HELMET_TOGGLE,
	BOOTS_TOGGLE,
	CHARGE,
	EXTRA_FUNCTION,
	FIRE_PROJECTILE,
	MODE;

	public static final IntFunction<PEKeybind> BY_ID = ByIdMap.continuous(PEKeybind::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
	public static final StreamCodec<ByteBuf, PEKeybind> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, PEKeybind::ordinal);

	private final String translationKey;

	PEKeybind() {
		this.translationKey = Util.makeDescriptionId("key", ELCore.rl(name().toLowerCase(Locale.ROOT)));
	}

	@Override
	public String getTranslationKey() {
		return translationKey;
	}
}