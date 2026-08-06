package com.skd.equivalentlegacy.gui.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ValidatedContainerSlot extends Slot {
    private final Predicate<ItemStack> validator;

    public ValidatedContainerSlot(Container container, int index, int x, int y, Predicate<ItemStack> validator) {
        super(container, index, x, y);
        this.validator = validator;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return super.mayPlace(stack) && validator.test(stack);
    }
}
