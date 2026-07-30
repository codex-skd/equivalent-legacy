package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

import java.util.*;

public final class ConversionGroup {
    private final Map<NormalizedSimpleStack, Long> values = new HashMap<>();
    private final List<CustomConversion> conversions = new ArrayList<>();

    public void addValue(NormalizedSimpleStack stack, long emc) {
        values.put(stack, emc);
    }

    public void addConversion(CustomConversion conversion) {
        conversions.add(conversion);
    }

    public Map<NormalizedSimpleStack, Long> getValues() {
        return Collections.unmodifiableMap(values);
    }

    public List<CustomConversion> getConversions() {
        return Collections.unmodifiableList(conversions);
    }

    public boolean isEmpty() {
        return values.isEmpty() && conversions.isEmpty();
    }
}
