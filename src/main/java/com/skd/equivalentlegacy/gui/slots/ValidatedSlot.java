package com.skd.equivalentlegacy.gui.slots;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ValidatedSlot extends SlotItemHandler {
    private final Predicate<ItemStack> validator;

    public ValidatedSlot(IItemHandler handler, int index, int x, int y, Predicate<ItemStack> validator) {
        super(handler, index, x, y);
        this.validator = validator;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return super.mayPlace(stack) && validator.test(stack);
    }
}
