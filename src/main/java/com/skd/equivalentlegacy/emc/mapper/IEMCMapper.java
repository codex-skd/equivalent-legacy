package com.skd.equivalentlegacy.emc.mapper;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;

/**
 * A mapper that contributes fixed values and/or conversions to the EMC mapping graph.
 * Registered with {@link EMCMappingHandler#registerMapper(IEMCMapper)}.
 *
 * <p>Mappers are consulted in registration order (earlier registrations are treated as
 * higher priority and cannot be overridden by later ones).</p>
 */
@FunctionalInterface
public interface IEMCMapper {

	/**
	 * Contributes mappings to the shared {@link IMappingCollector}.
	 *
	 * @param collector collector used to register fixed values and conversions
	 */
	void addMappings(IMappingCollector<NormalizedSimpleStack, Long> collector);

	default String getName() {
		return getClass().getSimpleName();
	}
}
