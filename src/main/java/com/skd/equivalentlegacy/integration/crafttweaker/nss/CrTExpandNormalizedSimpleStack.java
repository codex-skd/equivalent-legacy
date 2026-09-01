package com.skd.equivalentlegacy.integration.crafttweaker.nss;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.api.codec.IPECodecHelper;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a "stack" to be used by the EMC mapper.
 */
@ZenRegister
@Document("mods/EquivalentLegacy/NormalizedSimpleStack")
@NativeTypeRegistration(value = NormalizedSimpleStack.class, zenCodeName = "mods.equivalent_legacy.NormalizedSimpleStack")
public class CrTExpandNormalizedSimpleStack {

	private static final Logger CRT_LOGGER = CraftTweakerAPI.getLogger(ELCore.MODNAME);

	private CrTExpandNormalizedSimpleStack() {
	}

	/**
	 * Allows casting a {@link NormalizedSimpleStack} to a human-readable output.
	 */
	@ZenCodeType.Caster
	public static String asString(NormalizedSimpleStack internal) {
		return internal.toString();
	}

	/**
	 * Allows casting a {@link NormalizedSimpleStack} to a human-readable output.
	 */
	@ZenCodeType.Caster
	public static IData asData(NormalizedSimpleStack internal) {
		return IPECodecHelper.INSTANCE.nssCodec()
				.encode(internal, IDataOps.INSTANCE.withRegistryAccess(), new MapData())
				.getOrThrow();
	}
}