package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.api.IEMCStorage;
import com.skd.equivalentlegacy.gui.ChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class AlchemicalChestBlockEntity extends BaseMachineBlockEntity implements LidBlockEntity, IEMCStorage {
    public static final int SLOT_COUNT = 104;
    public static final long MAX_EMC = 10_000_000L;
    private int openNess = 0;
    private int openCount = 0;

    public AlchemicalChestBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST.get(), pos, state, SLOT_COUNT);
    }

    @Override
    public float getOpenNess(float partialTick) {
        return Math.min(1.0F, (openNess + partialTick) / 5.0F);
    }

    public void setOpenNess(int openNess) {
        this.openNess = Math.max(0, Math.min(5, openNess));
    }

    public int getOpenNess() {
        return openNess;
    }

    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            openCount++;
        }
    }

    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            openCount--;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlchemicalChestBlockEntity be) {
        tick(be);
    }

    public static void tick(AlchemicalChestBlockEntity be) {
        int target = be.openCount > 0 ? 5 : 0;
        if (be.openNess < target) {
            be.openNess++;
        } else if (be.openNess > target) {
            be.openNess--;
        }
    }

    @Override
    protected MenuProvider createMenuProvider() {
        return new SimpleMenuProvider((id, inv, p) -> new ChestMenu(id, inv, worldPosition), getDisplayName());
    }

    @Override
    protected Component getDisplayName() {
        return Component.translatable("container.equivalent_legacy.alchemical_chest");
    }

    @Override
    public long getStoredEmc() {
        return (long) emc;
    }

    @Override
    public long getMaximumEmc() {
        return MAX_EMC;
    }

    @Override
    public long receiveEmc(long amount, boolean simulate) {
        long space = MAX_EMC - (long) emc;
        long accepted = Math.min(amount, space);
        if (!simulate) {
            emc += accepted;
            setChanged();
        }
        return accepted;
    }

    @Override
    public long extractEmc(long amount, boolean simulate) {
        long available = (long) emc;
        long extracted = Math.min(amount, available);
        if (!simulate) {
            emc -= extracted;
            setChanged();
        }
        return extracted;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        emc = input.getLongOr("StoredEMC", 0L);
        ContainerHelper.loadAllItems(input, inventory.getItems());
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putLong("StoredEMC", (long) emc);
        ContainerHelper.saveAllItems(output, inventory.getItems());
    }
}
