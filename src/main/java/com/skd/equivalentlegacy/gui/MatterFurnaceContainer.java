package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.block.entity.MatterFurnaceBlockEntity;
import com.skd.equivalentlegacy.gui.slots.ValidatedContainerSlot;
import com.skd.equivalentlegacy.item.KleinStar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class MatterFurnaceContainer extends PEContainer {
    private static final Predicate<ItemStack> KLEIN_STAR_FILTER = stack ->
            !stack.isEmpty() && stack.getItem() instanceof KleinStar;

    public final MatterFurnaceBlockEntity blockEntity;
    public final BoxedLong furnaceEmcCost = new BoxedLong();
    private final DataSlot litTimeSlot = DataSlot.standalone();
    private final DataSlot litDurationSlot = DataSlot.standalone();
    private final DataSlot cookingProgressSlot = DataSlot.standalone();
    private final DataSlot cookingTotalSlot = DataSlot.standalone();

    protected MatterFurnaceContainer(MenuType<?> type, int containerId, Inventory playerInventory, MatterFurnaceBlockEntity blockEntity) {
        super(type, containerId);
        this.blockEntity = blockEntity;
        initDataSlots();
        initSlots();
        addPlayerInventory(playerInventory);
    }

    protected MatterFurnaceContainer(MenuType<?> type, int containerId, Inventory playerInventory, BlockPos pos) {
        super(type, containerId);
        this.blockEntity = (MatterFurnaceBlockEntity) playerInventory.player.level().getBlockEntity(pos);
        initDataSlots();
        initSlots();
        addPlayerInventory(playerInventory);
    }

    public MatterFurnaceContainer(int containerId, Inventory playerInventory, BlockPos pos) {
        this(ModMenuTypes.DM_FURNACE.get(), containerId, playerInventory, pos);
    }

    public MatterFurnaceContainer(int containerId, Inventory playerInventory, MatterFurnaceBlockEntity blockEntity) {
        this(ModMenuTypes.DM_FURNACE.get(), containerId, playerInventory, blockEntity);
    }

    private void initDataSlots() {
        longFields.add(furnaceEmcCost);
        addDataSlot(litTimeSlot);
        addDataSlot(litDurationSlot);
        addDataSlot(cookingProgressSlot);
        addDataSlot(cookingTotalSlot);
        addDataSlot(furnaceEmcCost.highSlot);
        addDataSlot(furnaceEmcCost.lowSlot);
    }

    private void initSlots() {
        addSlot(new ValidatedContainerSlot(blockEntity, 0, 26, 17, KLEIN_STAR_FILTER));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int idx = 1 + row * 3 + col;
                addSlot(new Slot(blockEntity, idx, 8 + col * 18, 44 + row * 18));
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final int idx = 10 + row * 3 + col;
                addSlot(new Slot(blockEntity, idx, 116 + col * 18, 44 + row * 18) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return false;
                    }
                });
            }
        }
    }

    @Override
    protected void broadcastPE(boolean all) {
        var data = blockEntity.getContainerData();
        furnaceEmcCost.set(blockEntity.getEmcCost());
        litTimeSlot.set(data.get(0));
        litDurationSlot.set(data.get(1));
        cookingProgressSlot.set(data.get(2));
        cookingTotalSlot.set(data.get(3));
        super.broadcastPE(all);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(blockEntity, player);
    }

    public long getEmcCost() {
        return furnaceEmcCost.get();
    }

    public int getLitTime() {
        return litTimeSlot.get();
    }

    public int getLitDuration() {
        return litDurationSlot.get();
    }

    public int getCookingProgress() {
        return cookingProgressSlot.get();
    }

    public int getCookingTotal() {
        return cookingTotalSlot.get();
    }
}
