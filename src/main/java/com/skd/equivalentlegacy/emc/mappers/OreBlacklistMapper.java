package com.skd.equivalentlegacy.emc.mappers;

import com.skd.equivalentlegacy.emc.mapper.IEMCMapper;
import com.skd.equivalentlegacy.emc.mapper.IMappingCollector;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

/**
 * Marks every ore as having no EMC value, which prevents circular conversion loops
 * between ores and their smelted products (e.g. ore {@code ->} ingot recipes).
 *
 * <p>Ores are intentionally not transmutable, mirroring the ProjectE/Equivox behavior.</p>
 */
public class OreBlacklistMapper implements IEMCMapper {

	@Override
	public String getName() {
		return "OreBlacklistMapper";
	}

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> collector) {
		for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.ORES)) {
			NSSItem ore = NSSItem.createItem(holder.value());
			collector.setValueBefore(ore, 0L);
			collector.setValueAfter(ore, 0L);
		}
	}
}
