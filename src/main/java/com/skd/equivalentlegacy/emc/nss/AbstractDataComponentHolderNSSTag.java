package com.skd.equivalentlegacy.emc.nss;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.resources.Identifier;

import java.util.Objects;

public abstract class AbstractDataComponentHolderNSSTag<TYPE> extends AbstractNSSTag<TYPE> implements NSSDataComponentHolder {
    private final DataComponentPatch componentsPatch;
    private boolean hasCachedHash;
    private int cachedHashCode;

    protected AbstractDataComponentHolderNSSTag(Identifier id, boolean isTag, DataComponentPatch componentsPatch) {
        super(id, isTag);
        this.componentsPatch = componentsPatch;
    }

    @Override
    public DataComponentPatch getComponentsPatch() {
        return componentsPatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (hasCachedHash && o instanceof AbstractDataComponentHolderNSSTag<?> that
                && cachedHashCode != 0 && that.cachedHashCode != 0
                && cachedHashCode != that.cachedHashCode) {
            return false;
        }
        if (!super.equals(o)) return false;
        if (!(o instanceof AbstractDataComponentHolderNSSTag<?> that)) return false;
        return Objects.equals(componentsPatch, that.componentsPatch);
    }

    @Override
    public int hashCode() {
        if (!hasCachedHash) {
            cachedHashCode = 31 * super.hashCode() + (componentsPatch != null ? componentsPatch.hashCode() : 0);
            hasCachedHash = true;
        }
        return cachedHashCode;
    }

    @Override
    public String toString() {
        return (representsTag() ? "#" : "") + getResourceLocation()
                + (componentsPatch != null && !componentsPatch.isEmpty() ? ":" + componentsPatch : "");
    }
}
