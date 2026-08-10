package com.skd.equivalentlegacy.emc.mappers;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongSortedMaps;
import java.util.Iterator;
import com.skd.equivalentlegacy.PECore;
import com.skd.equivalentlegacy.api.mapper.EMCMapper;
import com.skd.equivalentlegacy.api.mapper.IEMCMapper;
import com.skd.equivalentlegacy.api.mapper.collector.IMappingCollector;
import com.skd.equivalentlegacy.api.nss.NSSItem;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import com.skd.equivalentlegacy.config.CustomEMCParser;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;

@EMCMapper
public class CustomEMCMapper implements IEMCMapper<NormalizedSimpleStack, Long> {

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, ReloadableServerResources serverResources,
			RegistryAccess registryAccess, ResourceManager resourceManager) {
		for (Iterator<Object2LongMap.Entry<NSSItem>> iterator = Object2LongSortedMaps.fastIterator(CustomEMCParser.currentEntries.entries()); iterator.hasNext(); ) {
			Object2LongMap.Entry<NSSItem> entry = iterator.next();
			NSSItem item = entry.getKey();
			long emc = entry.getLongValue();
			PECore.debugLog("Adding custom EMC value for {}: {}", item, emc);
			//Note: We set it for each of the values in the tag to make sure it is properly taken into account when calculating the individual EMC values
			item.forSelfAndEachElement(mapper, emc, IMappingCollector::setValueBefore);
		}
	}

	@Override
	public String getName() {
		return PEConfigTranslations.MAPPING_CUSTOM_EMC_MAPPER.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.MAPPING_CUSTOM_EMC_MAPPER.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.MAPPING_CUSTOM_EMC_MAPPER.tooltip();
	}
}