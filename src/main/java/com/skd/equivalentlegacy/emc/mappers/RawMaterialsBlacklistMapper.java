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
 * Marks every raw material (raw iron, raw gold, raw copper, ...) as having no EMC value.
 * Raw materials are a step between ores and ingots and are intentionally not transmutable.
 */
public class RawMaterialsBlacklistMapper implements IEMCMapper {

	@Override
	public String getName() {
		return "RawMaterialsBlacklistMapper";
	}

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> collector) {
		for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.RAW_MATERIALS)) {
			NSSItem rawOre = NSSItem.createItem(holder.value());
			collector.setValueBefore(rawOre, 0L);
			collector.setValueAfter(rawOre, 0L);
		}
	}
}
