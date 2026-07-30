package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.mapper.SimpleGraphMapper;
import com.skd.equivalentlegacy.emc.mapper.LongArithmetic;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EMCHelper {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<NormalizedSimpleStack, Long> EMC_MAP = new ConcurrentHashMap<>();
    private static volatile boolean hasMappings = false;

    private EMCHelper() {}

    public static long getEMC(NormalizedSimpleStack stack) {
        return EMC_MAP.getOrDefault(stack, 0L);
    }

    public static boolean hasEMC(NormalizedSimpleStack stack) {
        return EMC_MAP.containsKey(stack) && EMC_MAP.get(stack) > 0;
    }

    public static void setEMC(NormalizedSimpleStack stack, long value) {
        if (value < 0) value = 0;
        EMC_MAP.put(stack, value);
    }

    public static Map<NormalizedSimpleStack, Long> getEMCMap() {
        return Collections.unmodifiableMap(EMC_MAP);
    }

    public static void registerFixedValues(FixedValues fixedValues) {
        for (var entry : fixedValues.setValueBefore().object2LongEntrySet()) {
            EMC_MAP.put(entry.getKey(), entry.getLongValue());
        }
        for (var entry : fixedValues.setValueAfter().object2LongEntrySet()) {
            EMC_MAP.put(entry.getKey(), entry.getLongValue());
        }
        hasMappings = true;
        LOGGER.info("Registered {} fixed EMC values", fixedValues.setValueBefore().size() + fixedValues.setValueAfter().size());
    }

    public static void calculateFromConversions(FixedValues fixedValues) {
        SimpleGraphMapper<NormalizedSimpleStack, Long, LongArithmetic> mapper =
                new SimpleGraphMapper<>(LongArithmetic.INSTANCE);

        for (var entry : fixedValues.setValueBefore().object2LongEntrySet()) {
            mapper.setValueBefore(entry.getKey(), entry.getLongValue());
        }

        for (var entry : fixedValues.setValueAfter().object2LongEntrySet()) {
            mapper.setValueAfter(entry.getKey(), entry.getLongValue());
        }

        for (CustomConversion conv : fixedValues.conversions()) {
            var ingredients = new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<NormalizedSimpleStack>();
            for (var ing : conv.ingredients().object2IntEntrySet()) {
                ingredients.put(ing.getKey(), ing.getIntValue());
            }
            mapper.addConversion(conv.count(), conv.output(), ingredients);
        }

        var result = mapper.generateValues();
        EMC_MAP.putAll(result);
        hasMappings = true;
        LOGGER.info("Calculated {} EMC values from conversions", result.size());
    }

    public static void clear() {
        EMC_MAP.clear();
        hasMappings = false;
    }

    public static boolean hasMappings() {
        return hasMappings;
    }
}
