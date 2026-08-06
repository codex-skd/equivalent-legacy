package com.skd.equivalentlegacy.emc.mappers;

import com.skd.equivalentlegacy.emc.ConversionGroup;
import com.skd.equivalentlegacy.emc.CustomConversion;
import com.skd.equivalentlegacy.emc.mapper.IEMCMapper;
import com.skd.equivalentlegacy.emc.mapper.IMappingCollector;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contributes player/mod defined custom conversions to the EMC mapping.
 *
 * <p>Conversions can be registered at runtime via {@link #registerConversion(CustomConversion)}
 * or {@link #registerGroup(ConversionGroup)}. Invalid conversions (as returned by
 * {@link CustomConversion#getFor}) are skipped.</p>
 */
public class CustomConversionMapper implements IEMCMapper {

	private static final List<ConversionGroup> GROUPS = new ArrayList<>();
	private static final List<CustomConversion> CONVERSIONS = new ArrayList<>();

	@Override
	public String getName() {
		return "CustomConversionMapper";
	}

	/**
	 * Registers a custom conversion group (set of values + conversions).
	 */
	public static void registerGroup(ConversionGroup group) {
		if (group != null && !group.isEmpty()) {
			GROUPS.add(group);
		}
	}

	/**
	 * Registers a single custom conversion.
	 */
	public static void registerConversion(CustomConversion conversion) {
		if (conversion != null && conversion != CustomConversion.INVALID) {
			CONVERSIONS.add(conversion);
		}
	}

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> collector) {
		for (ConversionGroup group : GROUPS) {
			for (var entry : group.getValues().entrySet()) {
				collector.setValueBefore(entry.getKey(), entry.getValue());
			}
			for (CustomConversion conversion : group.getConversions()) {
				if (conversion.output() != null && conversion.ingredients() != null && !conversion.ingredients().isEmpty()) {
					collector.addConversion(conversion.count(), conversion.output(), conversion.ingredients());
				}
			}
		}
		for (CustomConversion conversion : CONVERSIONS) {
			if (conversion.output() != null && conversion.ingredients() != null && !conversion.ingredients().isEmpty()) {
				collector.addConversion(conversion.count(), conversion.output(), conversion.ingredients());
			}
		}
	}

	public static List<ConversionGroup> getGroups() {
		return Collections.unmodifiableList(GROUPS);
	}

	public static List<CustomConversion> getConversions() {
		return Collections.unmodifiableList(CONVERSIONS);
	}
}
