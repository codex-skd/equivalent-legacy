package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class EquivalentLegacyTags {
    public static final TagKey<Item> DARK_MATTER = itemTag("dark_matter");
    public static final TagKey<Item> RED_MATTER = itemTag("red_matter");

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, EquivalentLegacy.rl(path));
    }

    private EquivalentLegacyTags() {}
}
