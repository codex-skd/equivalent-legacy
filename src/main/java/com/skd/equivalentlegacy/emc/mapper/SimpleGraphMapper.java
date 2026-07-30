package com.skd.equivalentlegacy.emc.mapper;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.*;

public class SimpleGraphMapper<T, V extends Comparable<V>, A extends IValueArithmetic<V>>
        extends MappingCollector<T, V, A> implements IValueGenerator<T, V> {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final boolean OVERWRITE_FIXED_VALUES = false;

    private final V ZERO;
    private static boolean logFoundExploits = false;

    public SimpleGraphMapper(A arithmetic) {
        super(arithmetic);
        this.ZERO = arithmetic.getZero();
    }

    public static void setLogFoundExploits(boolean value) {
        logFoundExploits = value;
    }

    @Override
    public Map<T, V> generateValues() {
        Map<T, V> values = new HashMap<>();

        for (Map.Entry<T, V> entry : valueBefore.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }

        boolean changed = true;
        int iterations = 0;
        while (changed && iterations < 1000) {
            changed = false;
            iterations++;
            for (Conversion<T> conv : conversions) {
                T output = conv.output;
                V currentValue = values.get(output);
                if (currentValue != null && !canOverride(output, currentValue)) {
                    continue;
                }

                V totalIngredients = ZERO;
                boolean allIngredientsKnown = true;
                for (var entry : conv.ingredients.object2IntEntrySet()) {
                    T ingredient = entry.getKey();
                    int count = entry.getIntValue();
                    V ingValue = values.get(ingredient);
                    if (ingValue == null) {
                        allIngredientsKnown = false;
                        break;
                    }
                    totalIngredients = arithmetic.add(totalIngredients, arithmetic.multiply(ingValue, count));
                }

                if (!allIngredientsKnown) continue;

                V totalOutputValue = arithmetic.divide(totalIngredients, conv.count);

                if (currentValue == null || arithmetic.compare(totalOutputValue, currentValue) < 0) {
                    values.put(output, totalOutputValue);
                    changed = true;
                }
            }
        }

        for (Map.Entry<T, V> entry : valueAfter.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }

        return values;
    }

    private boolean canOverride(T key, V currentValue) {
        if (OVERWRITE_FIXED_VALUES) return true;
        V fixed = valueBefore.get(key);
        if (fixed != null && !arithmetic.isZero(fixed)) {
            return arithmetic.compare(currentValue, fixed) <= 0;
        }
        return true;
    }
}
