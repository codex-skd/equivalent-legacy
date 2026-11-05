package com.skd.equivalentlegacy.gameObjs.registries;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.api.EquivalentLegacyRegistries;
import com.skd.equivalentlegacy.api.nss.NSSFake;
import com.skd.equivalentlegacy.api.nss.NSSFluid;
import com.skd.equivalentlegacy.api.nss.NSSItem;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import com.skd.equivalentlegacy.gameObjs.registration.DeferredCodecHolder;
import com.skd.equivalentlegacy.gameObjs.registration.DeferredCodecRegister;

public class PENormalizedSimpleStacks {

	private PENormalizedSimpleStacks() {
	}

	public static final DeferredCodecRegister<NormalizedSimpleStack> NSS_SERIALIZERS = new DeferredCodecRegister<>(EquivalentLegacyRegistries.NSS_SERIALIZER_NAME, ELCore.MODID);

	public static final DeferredCodecHolder<NormalizedSimpleStack, NSSItem> ITEM = NSS_SERIALIZERS.registerCodec("item", () -> NSSItem.CODEC);
	public static final DeferredCodecHolder<NormalizedSimpleStack, NSSFluid> FLUID = NSS_SERIALIZERS.registerCodec("fluid", () -> NSSFluid.CODEC);
	public static final DeferredCodecHolder<NormalizedSimpleStack, NSSFake> FAKE = NSS_SERIALIZERS.registerCodec("fake", () -> NSSFake.CODEC);
}