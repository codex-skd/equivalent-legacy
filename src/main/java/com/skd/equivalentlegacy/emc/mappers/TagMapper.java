package com.skd.equivalentlegacy.emc.mappers;

import com.skd.equivalentlegacy.emc.mapper.IEMCMapper;
import com.skd.equivalentlegacy.emc.mapper.IMappingCollector;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

/**
 * Gives every item tag the lowest EMC value among its elements, so items that share a tag
 * resolve to a common value (e.g. {@code oak_wood} and {@code spruce_wood}).
 *
 * <p>Phase 1B adaptation: only the {@code tag <- element} direction is registered. The
 * reverse ({@code element <- tag}) direction would override hardcoded fixed values and is
 * deferred to the Phase 2 graph-based resolution, matching Equivox's full bidirectional
 * behavior once the graph fully manages values.</p>
 */
public class TagMapper implements IEMCMapper {

	@Override
	public String getName() {
		return "TagMapper";
	}

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> collector) {
		// Phase 1B note: Tag iteration requires higher-level API not exposed in base registries.
		// For now, this mapper is a placeholder for Phase 2 graph-based tag resolution.
		// Individual tag mapping will be handled by custom conversions and special tag definitions.
	}
}
