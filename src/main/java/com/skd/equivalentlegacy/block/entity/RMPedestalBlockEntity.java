package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RMPedestalBlockEntity extends PedestalBlockEntity {
    public static final int MODE_DISPLAY = 0;
    public static final int MODE_LINK = 1;

    private int mode = MODE_DISPLAY;
    private BlockPos lastSelectedChest;

    public RMPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.RM_PEDESTAL.get(), pos, state);
    }

    public int getMode() {
        return mode;
    }

    public int toggleMode() {
        mode = (mode == MODE_DISPLAY) ? MODE_LINK : MODE_DISPLAY;
        lastSelectedChest = null;
        setChanged();
        sync();
        return mode;
    }

    public BlockPos getLastSelectedChest() {
        return lastSelectedChest;
    }

    public void setLastSelectedChest(BlockPos pos) {
        this.lastSelectedChest = pos;
        setChanged();
    }

    public void clearSelection() {
        this.lastSelectedChest = null;
        setChanged();
    }

    public boolean isLinkMode() {
        return mode == MODE_LINK;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        mode = input.getIntOr("PedestalMode", MODE_DISPLAY);
        int selX = input.getIntOr("SelChestX", 0);
        int selY = input.getIntOr("SelChestY", -1);
        int selZ = input.getIntOr("SelChestZ", 0);
        lastSelectedChest = (selY >= 0) ? new BlockPos(selX, selY, selZ) : null;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("PedestalMode", mode);
        if (lastSelectedChest != null) {
            output.putInt("SelChestX", lastSelectedChest.getX());
            output.putInt("SelChestY", lastSelectedChest.getY());
            output.putInt("SelChestZ", lastSelectedChest.getZ());
        } else {
            output.putInt("SelChestY", -1);
        }
    }
}
