package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.AlchemicalChestBlockEntity;
import com.skd.equivalentlegacy.gui.slots.SlotPredicates;
import com.skd.equivalentlegacy.gui.slots.ValidatedContainerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChestMenu extends PEContainer {
    public final AlchemicalChestBlockEntity blockEntity;
    private final DataSlot openness = DataSlot.standalone();

    public ChestMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.CHEST.get(), containerId);
        this.blockEntity = (AlchemicalChestBlockEntity) playerInventory.player.level().getBlockEntity(pos);

        addDataSlot(openness);

        var inventory = blockEntity.getInventory();
        for (int i = 0; i < 7; i++) {
            addSlot(new Slot(inventory, i, 8 + i * 18, 18));
        }
        for (int i = 0; i < 6; i++) {
            addSlot(new Slot(inventory, 7 + i, 17 + i * 18, 36));
        }

        addPlayerInventory(playerInventory, 8, 84);
    }

    @Override
    protected void broadcastPE(boolean all) {
        openness.set(blockEntity.getOpenNess());
        super.broadcastPE(all);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(blockEntity, player);
    }

    public int getOpenness() {
        return openness.get();
    }
}
