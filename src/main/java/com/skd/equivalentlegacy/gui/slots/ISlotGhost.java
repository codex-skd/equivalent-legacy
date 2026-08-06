package com.skd.equivalentlegacy.gui.slots;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface ISlotGhost {
    default Slot self() {
        return (Slot) this;
    }

    default boolean tryClear() {
        if (!self().getItem().isEmpty()) {
            self().set(ItemStack.EMPTY);
            return true;
        }
        return false;
    }
}
