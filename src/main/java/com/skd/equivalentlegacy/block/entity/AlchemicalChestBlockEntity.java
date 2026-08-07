package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.api.IEMCStorage;
import com.skd.equivalentlegacy.chest.ChestNetwork;
import com.skd.equivalentlegacy.chest.ChestNetworkManager;
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

import java.util.Optional;
import java.util.UUID;

public class AlchemicalChestBlockEntity extends BaseMachineBlockEntity implements LidBlockEntity, IEMCStorage {
    public static final int SLOT_COUNT = 104;
    public static final long MAX_EMC = 10_000_000L;
    private int openNess = 0;
    private int openCount = 0;
    private Optional<UUID> networkId = Optional.empty();

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

    public Optional<UUID> getNetworkId() {
        return networkId;
    }

    public void setNetworkId(UUID id) {
        this.networkId = Optional.ofNullable(id);
        setChanged();
    }

    public void clearNetworkId() {
        this.networkId = Optional.empty();
        setChanged();
    }

    public ChestNetwork getNetwork() {
        if (level == null || level.isClientSide() || networkId.isEmpty()) {
            return null;
        }
        return ChestNetworkManager.getInstance().getNetworkById(level, networkId.get());
    }

    public boolean isInNetwork() {
        return networkId.isPresent() && getNetwork() != null;
    }

    public int getNetworkChestCount() {
        ChestNetwork network = getNetwork();
        return network != null ? network.getChestCount() : 0;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide() && networkId.isPresent()) {
            ChestNetwork network = ChestNetworkManager.getInstance().getNetworkById(level, networkId.get());
            if (network != null && !network.contains(worldPosition)) {
                network.addChest(worldPosition);
            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null && !level.isClientSide() && networkId.isPresent()) {
            ChestNetworkManager.getInstance().removeChestFromNetwork(level, worldPosition);
        }
    }

    @Override
    public long getStoredEmc() {
        ChestNetwork network = getNetwork();
        if (network != null) {
            return network.getTotalEmc();
        }
        return (long) emc;
    }

    @Override
    public long getMaximumEmc() {
        ChestNetwork network = getNetwork();
        if (network != null) {
            return network.getMaxEmc();
        }
        return MAX_EMC;
    }

    @Override
    public long receiveEmc(long amount, boolean simulate) {
        ChestNetwork network = getNetwork();
        if (network != null) {
            long accepted = network.receiveEmc(amount, simulate);
            if (!simulate && accepted > 0) {
                setChanged();
            }
            return accepted;
        }
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
        ChestNetwork network = getNetwork();
        if (network != null) {
            long extracted = network.extractEmc(amount, simulate);
            if (!simulate && extracted > 0) {
                setChanged();
            }
            return extracted;
        }
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
        long msb = input.getLongOr("NetIdMsb", 0L);
        long lsb = input.getLongOr("NetIdLsb", 0L);
        networkId = (msb == 0L && lsb == 0L) ? Optional.empty() : Optional.of(new UUID(msb, lsb));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putLong("StoredEMC", (long) emc);
        ContainerHelper.saveAllItems(output, inventory.getItems());
        networkId.ifPresentOrElse(id -> {
            output.putLong("NetIdMsb", id.getMostSignificantBits());
            output.putLong("NetIdLsb", id.getLeastSignificantBits());
        }, () -> {
            output.putLong("NetIdMsb", 0L);
            output.putLong("NetIdLsb", 0L);
        });
    }
}
