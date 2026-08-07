package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.MatterFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;

public class RMFurnaceContainer extends MatterFurnaceContainer {
    public RMFurnaceContainer(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.RM_FURNACE.get(), containerId, playerInventory, pos);
    }

    public RMFurnaceContainer(int containerId, Inventory playerInventory, MatterFurnaceBlockEntity blockEntity) {
        super(ModMenuTypes.RM_FURNACE.get(), containerId, playerInventory, blockEntity);
    }
}
