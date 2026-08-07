package com.skd.equivalentlegacy.chest;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;

public class ChestNetworkSavedData extends SavedData {
    public static final SavedDataType<ChestNetworkSavedData> TYPE = new SavedDataType<>(
            EquivalentLegacy.rl("chest_networks"),
            ChestNetworkSavedData::new,
            RecordCodecBuilder.create(instance -> instance.group(
                    CompoundTag.CODEC.fieldOf("data").forGetter(data -> {
                        CompoundTag tag = new CompoundTag();
                        ListTag networksTag = new ListTag();
                        for (ChestNetwork network : data.networks.values()) {
                            networksTag.add(network.save());
                        }
                        tag.put("Networks", networksTag);
                        return tag;
                    })
            ).apply(instance, tag -> {
                ChestNetworkSavedData data = new ChestNetworkSavedData();
                ListTag networksTag = tag.getList("Networks").orElse(new ListTag());
                for (int i = 0; i < networksTag.size(); i++) {
                    networksTag.getCompound(i).ifPresent(networkTag -> {
                        ChestNetwork network = ChestNetwork.load(networkTag);
                        data.networks.put(network.getNetworkId(), network);
                    });
                }
                return data;
            }))
    );

    private final Map<UUID, ChestNetwork> networks = new LinkedHashMap<>();

    public ChestNetworkSavedData() {}

    public static ChestNetworkSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public Map<UUID, ChestNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(Map<UUID, ChestNetwork> newNetworks) {
        networks.clear();
        networks.putAll(newNetworks);
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
