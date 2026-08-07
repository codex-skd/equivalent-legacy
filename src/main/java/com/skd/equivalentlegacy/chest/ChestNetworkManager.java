package com.skd.equivalentlegacy.chest;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.entity.AlchemicalChestBlockEntity;
import com.skd.equivalentlegacy.network.payload.SyncNetworkDataPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public final class ChestNetworkManager {
    private static final ChestNetworkManager INSTANCE = new ChestNetworkManager();
    private static final long LINK_WINDOW_MS = 30_000L;

    private final Map<ResourceKey<Level>, Map<UUID, ChestNetwork>> dimensionNetworks = new HashMap<>();
    private final Map<UUID, LinkSession> pendingLinks = new HashMap<>();

    private record LinkSession(UUID networkId, BlockPos firstChest, long timestamp) {}

    private ChestNetworkManager() {}

    public static ChestNetworkManager getInstance() {
        return INSTANCE;
    }

    public void onLevelLoad(ServerLevel level) {
        ChestNetworkSavedData data = ChestNetworkSavedData.get(level);
        Map<UUID, ChestNetwork> networks = data.getNetworks();
        dimensionNetworks.put(level.dimension(), networks);
        EquivalentLegacy.LOGGER.info("Loaded {} chest networks for dimension {}",
                networks.size(), level.dimension());
    }

    public void onLevelSave(ServerLevel level) {
        Map<UUID, ChestNetwork> networks = dimensionNetworks.get(level.dimension());
        if (networks != null) {
            ChestNetworkSavedData data = ChestNetworkSavedData.get(level);
            data.setNetworks(networks);
            data.setDirty();
        }
    }

    private Map<UUID, ChestNetwork> getNetworksFor(Level level) {
        return dimensionNetworks.computeIfAbsent(level.dimension(), k -> new HashMap<>());
    }

    public ChestNetwork getNetworkFor(Level level, BlockPos pos) {
        Map<UUID, ChestNetwork> networks = getNetworksFor(level);
        for (ChestNetwork net : networks.values()) {
            if (net.contains(pos)) {
                return net;
            }
        }
        return null;
    }

    public ChestNetwork getNetworkById(Level level, UUID networkId) {
        return getNetworksFor(level).get(networkId);
    }

    public ChestNetwork createNetwork(Level level, BlockPos pos) {
        Map<UUID, ChestNetwork> networks = getNetworksFor(level);

        ChestNetwork existing = getNetworkFor(level, pos);
        if (existing != null) {
            return existing;
        }

        ChestNetwork network = new ChestNetwork(UUID.randomUUID());
        network.addChest(pos);
        networks.put(network.getNetworkId(), network);
        return network;
    }

    public boolean addChestToNetwork(Level level, UUID networkId, BlockPos pos) {
        ChestNetwork network = getNetworkById(level, networkId);
        if (network == null || !network.canAddChest()) {
            return false;
        }
        ChestNetwork existing = getNetworkFor(level, pos);
        if (existing != null) {
            return false;
        }
        boolean added = network.addChest(pos);
        if (added) {
            syncNetworkToClients(level, network);
        }
        return added;
    }

    public boolean removeChestFromNetwork(Level level, BlockPos pos) {
        ChestNetwork network = getNetworkFor(level, pos);
        if (network == null) {
            return false;
        }
        boolean removed = network.removeChest(pos);
        if (removed && network.getChestCount() == 0) {
            getNetworksFor(level).remove(network.getNetworkId());
        }
        if (removed) {
            syncNetworkToClients(level, network);
        }
        return removed;
    }

    public ChestNetwork getOrCreatePendingLink(ServerPlayer player, Level level, BlockPos pos) {
        ChestNetwork existing = getNetworkFor(level, pos);
        if (existing != null) {
            return existing;
        }

        UUID playerId = player.getUUID();
        LinkSession session = pendingLinks.get(playerId);
        long now = System.currentTimeMillis();

        if (session != null && (now - session.timestamp) < LINK_WINDOW_MS) {
            ChestNetwork network = getNetworkById(level, session.networkId);
            if (network != null && network.contains(session.firstChest)) {
                if (network.canAddChest() && !pos.equals(session.firstChest)) {
                    network.addChest(pos);
                    pendingLinks.remove(playerId);
                    syncNetworkToClients(level, network);
                    return network;
                }
            }
            pendingLinks.remove(playerId);
        }

        ChestNetwork network = createNetwork(level, pos);
        if (network != null) {
            pendingLinks.put(playerId, new LinkSession(network.getNetworkId(), pos, now));
            syncNetworkToClients(level, network);
        }
        return network;
    }

    public void clearPendingLink(ServerPlayer player) {
        pendingLinks.remove(player.getUUID());
    }

    private void syncNetworkToClients(Level level, ChestNetwork network) {
        if (level instanceof ServerLevel serverLevel) {
            List<BlockPos> positions = network.getChestPositions();
            long totalEmc = network.getTotalEmc();

            for (ServerPlayer player : serverLevel.players()) {
                PacketDistributor.sendToPlayer(player,
                        new SyncNetworkDataPayload(network.getNetworkId(), positions, totalEmc));
            }
        }
    }

    public void sendNetworkSyncTo(ServerLevel level, ChestNetwork network) {
        List<BlockPos> positions = network.getChestPositions();
        long totalEmc = network.getTotalEmc();
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player,
                    new SyncNetworkDataPayload(network.getNetworkId(), positions, totalEmc));
        }
    }

    public void sendAllNetworksTo(ServerPlayer player) {
        Map<UUID, ChestNetwork> networks = getNetworksFor((ServerLevel) player.level());
        for (ChestNetwork network : networks.values()) {
            PacketDistributor.sendToPlayer(player,
                    new SyncNetworkDataPayload(network.getNetworkId(),
                            network.getChestPositions(), network.getTotalEmc()));
        }
    }

    public Collection<ChestNetwork> getAllNetworks(Level level) {
        return Collections.unmodifiableCollection(getNetworksFor(level).values());
    }
}
