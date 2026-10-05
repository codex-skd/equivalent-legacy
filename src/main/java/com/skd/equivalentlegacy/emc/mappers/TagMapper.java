package com.skd.equivalentlegacy.emc.mappers;

import com.skd.equivalentlegacy.api.mapper.IEMCMapper;
import com.skd.equivalentlegacy.api.mapper.collector.IMappingCollector;
import com.skd.equivalentlegacy.api.nss.AbstractNSSTag;
import com.skd.equivalentlegacy.api.nss.NSSTag;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import com.skd.equivalentlegacy.utils.EMCHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;

public class TagMapper implements IEMCMapper<NormalizedSimpleStack, Long> {

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, ReloadableServerResources serverResources,
			RegistryAccess registryAccess, ResourceManager resourceManager) {
		for (NSSTag stack : AbstractNSSTag.getAllCreatedTags()) {
			stack.forEachElement(mapper, stack, (collector, normalizedSimpleStack, tag) -> {
				//Tag -> element
				collector.addConversion(1, tag, EMCHelper.intMapOf(normalizedSimpleStack, 1));
				//Element -> tag
				collector.addConversion(1, normalizedSimpleStack, EMCHelper.intMapOf(tag, 1));
			});
		}
	}

	@Override
	public String getName() {
		return PEConfigTranslations.MAPPING_TAG_MAPPER.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.MAPPING_TAG_MAPPER.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.MAPPING_TAG_MAPPER.tooltip();
	}
}