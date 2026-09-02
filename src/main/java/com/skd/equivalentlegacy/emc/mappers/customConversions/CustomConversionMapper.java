package com.skd.equivalentlegacy.emc.mappers.customConversions;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongSortedMaps;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.api.conversion.ConversionGroup;
import com.skd.equivalentlegacy.api.conversion.CustomConversion;
import com.skd.equivalentlegacy.api.conversion.CustomConversionFile;
import com.skd.equivalentlegacy.api.mapper.EMCMapper;
import com.skd.equivalentlegacy.api.mapper.IEMCMapper;
import com.skd.equivalentlegacy.api.mapper.collector.IMappingCollector;
import com.skd.equivalentlegacy.api.nss.NSSFake;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.apache.logging.log4j.util.TriConsumer;

@EMCMapper
public class CustomConversionMapper implements IEMCMapper<NormalizedSimpleStack, Long> {

	private static final FileToIdConverter CONVERSION_LISTER = FileToIdConverter.json("pe_custom_conversions");
	private static final TriConsumer<IMappingCollector<NormalizedSimpleStack, Long>, NormalizedSimpleStack, CustomConversion> CONVERSION_CONSUMER =
			(collector, nss, conversion) ->
			collector.setValueFromConversion(conversion.count(), nss, conversion.ingredients());

	@Override
	public String getName() {
		return PEConfigTranslations.MAPPING_CUSTOM_CONVERSION_MAPPER.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.MAPPING_CUSTOM_CONVERSION_MAPPER.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.MAPPING_CUSTOM_CONVERSION_MAPPER.tooltip();
	}

	@Override
	public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, ReloadableServerResources serverResources,
			RegistryAccess registryAccess, ResourceManager resourceManager) {
		Map<ResourceLocation, CustomConversionFile> files = load(registryAccess, resourceManager);
		for (CustomConversionFile file : files.values()) {
			addMappingsFromFile(file, mapper);
		}
	}

	private static Map<ResourceLocation, CustomConversionFile> load(RegistryAccess registryAccess, ResourceManager resourceManager) {
		Map<ResourceLocation, CustomConversionFile> loading = new HashMap<>();

		// Wrap in ConditionalOps so CONDITIONAL_CODEC can evaluate "neoforge:conditions" (mod_loaded gates on the ATM/Powah compat files).
		RegistryOps<JsonElement> serializationContext = new ConditionalOps<>(
				registryAccess.createSerializationContext(JsonOps.INSTANCE), ICondition.IContext.EMPTY);
		// Find all data/<domain>/pe_custom_conversions/foo/bar.json
		for (Map.Entry<ResourceLocation, List<Resource>> entry : CONVERSION_LISTER.listMatchingResourceStacks(resourceManager).entrySet()) {
			ResourceLocation file = entry.getKey();//<domain>:foo/bar
			ResourceLocation conversionId = CONVERSION_LISTER.fileToId(file);

			ELCore.debugLog("Considering file {}, ID {}", file, conversionId);
			NSSFake.setCurrentNamespace(conversionId.toString());

			// Iterate through all copies of this conversion, from lowest to highest priority datapack, merging the results together
			for (Resource resource : entry.getValue()) {
				try (Reader reader = resource.openAsReader()) {
					JsonElement json = JsonParser.parseReader(reader);
					DataResult<Optional<WithConditions<CustomConversionFile>>> result = CustomConversionFile.CONDITIONAL_CODEC.parse(serializationContext, json);
					if (result.isSuccess()) {
						Optional<WithConditions<CustomConversionFile>> decoded = result.getOrThrow();
						if (decoded.isPresent()) {
							loading.merge(conversionId, decoded.get().carrier(), CustomConversionFile::merge);
						} else {
							ELCore.debugLog("Skipping loading custom conversion file {} as its conditions were not met", file);
						}
					} else {
						result.ifError(error -> ELCore.LOGGER.error("Parsing error loading custom conversion file {}: {}", file, error.message()));
					}
				} catch (IOException e) {
					ELCore.LOGGER.error("Could not load resource {}", file, e);
				}
			}
		}
		NSSFake.resetNamespace();
		return loading;
	}

	private static void addMappingsFromFile(CustomConversionFile file, IMappingCollector<NormalizedSimpleStack, Long> mapper) {
		for (Map.Entry<String, ConversionGroup> entry : file.groups().entrySet()) {
			ConversionGroup group = entry.getValue();
			ELCore.debugLog("Adding conversions from group '{}' with comment '{}'", entry.getKey(), group.comment());
			for (CustomConversion conversion : group.conversions()) {
				mapper.addConversion(conversion.count(), conversion.output(), conversion.ingredients());
			}
		}

		//Note: We set it for each of the values in the tag to make sure it is properly taken into account when calculating the individual EMC values
		for (Iterator<Object2LongMap.Entry<NormalizedSimpleStack>> iterator = Object2LongSortedMaps.fastIterator(file.values().setValueBefore()); iterator.hasNext(); ) {
			Object2LongMap.Entry<NormalizedSimpleStack> entry = iterator.next();
			entry.getKey().forSelfAndEachElement(mapper, entry.getLongValue(), IMappingCollector::setValueBefore);
		}

		//Note: We set it for each of the values in the tag to make sure it is properly taken into account when calculating the individual EMC values
		for (Iterator<Object2LongMap.Entry<NormalizedSimpleStack>> iterator = Object2LongSortedMaps.fastIterator(file.values().setValueAfter()); iterator.hasNext(); ) {
			Object2LongMap.Entry<NormalizedSimpleStack> entry = iterator.next();
			entry.getKey().forSelfAndEachElement(mapper, entry.getLongValue(), IMappingCollector::setValueAfter);
		}

		for (CustomConversion customConversion : file.values().conversions()) {
			if (customConversion.propagateTags()) {
				customConversion.output().forSelfAndEachElement(mapper, customConversion, CONVERSION_CONSUMER);
			} else {
				CONVERSION_CONSUMER.accept(mapper, customConversion.output(), customConversion);
			}
		}
	}
}