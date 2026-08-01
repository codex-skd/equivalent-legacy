package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseMachineBlockEntity extends BlockEntity {
    protected final SimpleContainer inventory;
    protected double emc;

    protected BaseMachineBlockEntity(net.minecraft.world.level.block.entity.BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
        super(type, pos, state);
        this.inventory = new SimpleContainer(inventorySize);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public long getEmc() {
        return (long) emc;
    }

    public void addEmc(long amount) {
        this.emc += amount;
        setChanged();
    }

    public long takeEmc(long max) {
        long available = (long) emc;
        long taken = Math.min(available, max);
        this.emc -= taken;
        return taken;
    }

    public void dropContents() {
        if (level == null || level.isClientSide()) return;
        Containers.dropContents(level, worldPosition, inventory);
        inventory.clearContent();
    }

    public boolean stillValid(Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    public void openMenu(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(createMenuProvider(), buf -> buf.writeBlockPos(worldPosition));
        }
    }

    protected abstract MenuProvider createMenuProvider();

    protected abstract Component getDisplayName();
}
