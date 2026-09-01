package com.skd.equivalentlegacy.integration.crafttweaker.mappers;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.List;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.api.mapper.EMCMapper;
import com.skd.equivalentlegacy.api.mapper.IEMCMapper;
import com.skd.equivalentlegacy.api.mapper.collector.IMappingCollector;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

@EMCMapper(requiredMods = "crafttweaker")
public class CrTConversionEMCMapper implements IEMCMapper<NormalizedSimpleStack, Long> {

	private static final List<CrTConversion> storedConversions = new ArrayList<>();

	public static void addConversion(@NotNull CrTConversion conversion) {
		storedConversions.add(conversion);
	}

	public static void removeConversion(@NotNull CrTConversion conversion) {
		storedConversions.remove(conversion);
	}

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, ReloadableServerResources serverResources,
			RegistryAccess registryAccess, ResourceManager resourceManager) {
		for (CrTConversion apiConversion : storedConversions) {
			TriConsumer<IMappingCollector<NormalizedSimpleStack, Long>, NormalizedSimpleStack, CrTConversion> consumer;
			if (apiConversion.set) {
				consumer = (collector, nss, conversion) ->
						collector.setValueFromConversion(conversion.amount, nss, conversion.ingredients);
			} else {
				consumer = (collector, nss, conversion) ->
						collector.addConversion(conversion.amount, nss, conversion.ingredients);
			}
			if (apiConversion.propagateTags) {
				apiConversion.output.forSelfAndEachElement(mapper, apiConversion, consumer);
			} else {
				consumer.accept(mapper, apiConversion.output, apiConversion);
			}
			ELCore.debugLog("CraftTweaker adding conversion for {}", apiConversion.output);
		}
	}

	@Override
	public String getName() {
		return PEConfigTranslations.MAPPING_CRT_CONVERSION_MAPPER.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.MAPPING_CRT_CONVERSION_MAPPER.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.MAPPING_CRT_CONVERSION_MAPPER.tooltip();
	}

	public record CrTConversion(NormalizedSimpleStack output, int amount, boolean propagateTags, boolean set, Object2IntMap<NormalizedSimpleStack> ingredients) {}
}