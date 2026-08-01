package com.skd.equivalentlegacy.gui;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;

public class MatterFurnaceMenu extends AbstractFurnaceMenu {
    public MatterFurnaceMenu(int containerId, Inventory inventory) {
        super(ModMenuTypes.FURNACE.get(), RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, inventory);
    }

    public MatterFurnaceMenu(int containerId, Inventory inventory, Container container, ContainerData data) {
        super(ModMenuTypes.FURNACE.get(), RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, inventory, container, data);
    }
}
