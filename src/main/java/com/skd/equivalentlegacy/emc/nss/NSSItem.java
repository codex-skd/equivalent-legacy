package com.skd.equivalentlegacy.emc.nss;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class NSSItem extends AbstractDataComponentHolderNSSTag<Item> {
    private static final ResourceKey<Item> DEFAULT_KEY = null;

    public static final MapCodec<NSSItem> CODEC = null;

    private NSSItem(Identifier id, boolean isTag, DataComponentPatch componentsPatch) {
        super(id, isTag, componentsPatch);
    }

    public static NSSItem createItem(ItemStack stack) {
        return new NSSItem(
                BuiltInRegistries.ITEM.getKey(stack.getItem()),
                false,
                stack.getComponentsPatch()
        );
    }

    public static NSSItem createItem(ItemLike itemLike) {
        return new NSSItem(
                BuiltInRegistries.ITEM.getKey(itemLike.asItem()),
                false,
                DataComponentPatch.EMPTY
        );
    }

    public static NSSItem createItem(Identifier id) {
        return new NSSItem(id, false, DataComponentPatch.EMPTY);
    }

    public static NSSItem createItem(Identifier id, DataComponentPatch components) {
        return new NSSItem(id, false, components);
    }

    public static NSSItem createTag(Identifier id) {
        return new NSSItem(id, true, DataComponentPatch.EMPTY);
    }

    @Override
    protected Registry<Item> getRegistry() {
        return BuiltInRegistries.ITEM;
    }

    @Override
    public MapCodec<? extends NormalizedSimpleStack> codec() {
        return CODEC;
    }
}
