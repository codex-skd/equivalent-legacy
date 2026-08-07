package com.skd.equivalentlegacy.chest;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.*;

public class ChestNetwork {
    public static final int MAX_CHESTS = 16;
    public static final long MAX_EMC_PER_CHEST = 10_000_000L;

    private final UUID networkId;
    private final List<BlockPos> chestPositions;
    private long networkEmc;

    public ChestNetwork(UUID networkId) {
        this.networkId = networkId;
        this.chestPositions = new ArrayList<>();
        this.networkEmc = 0L;
    }

    public UUID getNetworkId() {
        return networkId;
    }

    public List<BlockPos> getChestPositions() {
        return Collections.unmodifiableList(chestPositions);
    }

    public int getChestCount() {
        return chestPositions.size();
    }

    public boolean contains(BlockPos pos) {
        return chestPositions.contains(pos);
    }

    public boolean canAddChest() {
        return chestPositions.size() < MAX_CHESTS;
    }

    public boolean addChest(BlockPos pos) {
        if (chestPositions.size() >= MAX_CHESTS || chestPositions.contains(pos)) {
            return false;
        }
        chestPositions.add(pos);
        return true;
    }

    public boolean removeChest(BlockPos pos) {
        return chestPositions.remove(pos);
    }

    public long getTotalEmc() {
        return networkEmc;
    }

    public long getMaxEmc() {
        return (long) chestPositions.size() * MAX_EMC_PER_CHEST;
    }

    public long receiveEmc(long amount, boolean simulate) {
        long space = getMaxEmc() - networkEmc;
        long accepted = Math.min(amount, space);
        if (!simulate) {
            networkEmc += accepted;
        }
        return accepted;
    }

    public long extractEmc(long amount, boolean simulate) {
        long extracted = Math.min(amount, networkEmc);
        if (!simulate) {
            networkEmc -= extracted;
        }
        return extracted;
    }

    public void setNetworkEmc(long emc) {
        this.networkEmc = Math.min(emc, getMaxEmc());
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("NetworkIdMost", networkId.getMostSignificantBits());
        tag.putLong("NetworkIdLeast", networkId.getLeastSignificantBits());
        tag.putLong("NetworkEmc", networkEmc);

        ListTag positionsTag = new ListTag();
        for (BlockPos pos : chestPositions) {
            CompoundTag posTag = new CompoundTag();
            posTag.putInt("x", pos.getX());
            posTag.putInt("y", pos.getY());
            posTag.putInt("z", pos.getZ());
            positionsTag.add(posTag);
        }
        tag.put("ChestPositions", positionsTag);
        return tag;
    }

    public static ChestNetwork load(CompoundTag tag) {
        long most = tag.getLong("NetworkIdMost").orElse(0L);
        long least = tag.getLong("NetworkIdLeast").orElse(0L);
        UUID id = new UUID(most, least);
        ChestNetwork network = new ChestNetwork(id);
        network.networkEmc = tag.getLong("NetworkEmc").orElse(0L);

        ListTag positionsTag = tag.getList("ChestPositions").orElse(new ListTag());
        for (int i = 0; i < positionsTag.size(); i++) {
            positionsTag.getCompound(i).ifPresent(posTag -> {
                int x = posTag.getInt("x").orElse(0);
                int y = posTag.getInt("y").orElse(0);
                int z = posTag.getInt("z").orElse(0);
                network.chestPositions.add(new BlockPos(x, y, z));
            });
        }
        return network;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChestNetwork that)) return false;
        return networkId.equals(that.networkId);
    }

    @Override
    public int hashCode() {
        return networkId.hashCode();
    }
}
