package com.skd.equivalentlegacy.emc.mapper;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;

/**
 * Result wrapper for a single EMC lookup against the mapping handler.
 *
 * @param stack    the normalized stack that was queried
 * @param value    the resolved EMC value (may be zero when {@code resolved} is {@code false})
 * @param source   description of where the value came from (e.g. "fixed", "graph", mapper name)
 * @param resolved whether the stack actually resolved to a value in the mapping
 */
public record MappingResult(NormalizedSimpleStack stack, long value, String source, boolean resolved) {

	public static MappingResult absent(NormalizedSimpleStack stack) {
		return new MappingResult(stack, 0L, "none", false);
	}

	public static MappingResult of(NormalizedSimpleStack stack, long value, String source) {
		return new MappingResult(stack, value, source, true);
	}

	public boolean hasValue() {
		return resolved && value > 0;
	}
}
