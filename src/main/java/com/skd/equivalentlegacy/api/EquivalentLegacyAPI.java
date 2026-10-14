package com.skd.equivalentlegacy.api;

public final class EquivalentLegacyAPI {

	public static final String EQUIVALENT_LEGACY_MODID = "equivalent_legacy";
	/** Former ProjectE / ProjectEE mod id; registry objects alias {@code projecte:*} → {@code equivalent_legacy:*}. */
	public static final String LEGACY_MODID = "projecte";
	/** Former Equivalence 1.4.x mod id; registry objects alias {@code equivalence:*} → {@code equivalent_legacy:*}. */
	public static final String LEGACY_MODID_EQUIVALENCE = "equivalence";
	/** All prior mod namespaces that alias into {@link #EQUIVALENT_LEGACY_MODID}. */
	public static final String[] LEGACY_MODIDS = { LEGACY_MODID, LEGACY_MODID_EQUIVALENCE };
	public static final long FREE_ARITHMETIC_VALUE = Long.MIN_VALUE;

	private EquivalentLegacyAPI() {
	}
}
