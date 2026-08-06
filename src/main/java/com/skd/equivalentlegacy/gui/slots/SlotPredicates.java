package com.skd.equivalentlegacy.gui.slots;

import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public final class SlotPredicates {

    public static final Predicate<ItemStack> ALWAYS_FALSE = input -> false;

    public static final Predicate<ItemStack> HAS_EMC = input -> !input.isEmpty();

    public static final Predicate<ItemStack> COLLECTOR_INV = input -> !input.isEmpty();

    public static final Predicate<ItemStack> CONDENSER_LOCK = HAS_EMC;

    public static final Predicate<ItemStack> RELAY_INV = HAS_EMC;

    private SlotPredicates() {
    }
}
