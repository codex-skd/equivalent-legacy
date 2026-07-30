package com.skd.equivalentlegacy.emc.nss;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractNSSTag<TYPE> implements NSSTag {
    private final Identifier resourceLocation;
    private final boolean isTag;
    private final int hashCode;

    protected AbstractNSSTag(Identifier resourceLocation, boolean isTag) {
        this.resourceLocation = resourceLocation;
        this.isTag = isTag;
        this.hashCode = Objects.hash(resourceLocation, isTag);
    }

    public Identifier getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public boolean representsTag() {
        return isTag;
    }

    protected abstract Registry<TYPE> getRegistry();

    @Override
    public void forEachElement(Consumer<NormalizedSimpleStack> consumer) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNSSTag<?> that)) return false;
        return isTag == that.isTag && Objects.equals(resourceLocation, that.resourceLocation);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return (isTag ? "#" : "") + resourceLocation;
    }
}
