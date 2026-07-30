package com.skd.equivalentlegacy.player;

import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.network.payload.KnowledgeSyncChangePayload;
import com.skd.equivalentlegacy.network.payload.KnowledgeSyncEmcPayload;
import com.skd.equivalentlegacy.network.payload.KnowledgeSyncPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.Set;

public final class PlayerKnowledge {
    private final Player player;
    private final PlayerKnowledgeAttachment attachment;

    private PlayerKnowledge(Player player) {
        this.player = player;
        this.attachment = player.getData(EquivalentLegacyAttachments.PLAYER_KNOWLEDGE);
    }

    public static PlayerKnowledge of(Player player) {
        return new PlayerKnowledge(player);
    }

    public long getEmc() {
        return attachment.getEmc();
    }

    public void setEmc(long emc) {
        attachment.setEmc(emc);
    }

    public void addEmc(long amount) {
        attachment.addEmc(amount);
    }

    public boolean subtractEmc(long amount) {
        return attachment.subtractEmc(amount);
    }

    public boolean hasKnowledge(NSSItem item) {
        return attachment.hasKnowledge(item.getResourceLocation().toString());
    }

    public boolean learnItem(NSSItem item) {
        String id = item.getResourceLocation().toString();
        boolean changed = attachment.addKnowledge(id);
        if (changed && player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new KnowledgeSyncChangePayload(id, true));
        }
        return changed;
    }

    public boolean learnItem(Identifier id) {
        return learnItem(NSSItem.createItem(id));
    }

    public boolean unlearnItem(NSSItem item) {
        String id = item.getResourceLocation().toString();
        boolean changed = attachment.removeKnowledge(id);
        if (changed && player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new KnowledgeSyncChangePayload(id, false));
        }
        return changed;
    }

    public Set<NSSItem> getKnownItems() {
        Set<NSSItem> items = new HashSet<>();
        for (String id : attachment.getKnowledge()) {
            items.add(NSSItem.createItem(Identifier.parse(id)));
        }
        return items;
    }

    public boolean hasFullKnowledge() {
        return attachment.hasFullKnowledge();
    }

    public void setFullKnowledge(boolean full) {
        attachment.setFullKnowledge(full);
    }

    public void clearKnowledge() {
        attachment.clearKnowledge();
    }

    public void sync(ServerPlayer serverPlayer) {
        PacketDistributor.sendToPlayer(serverPlayer, new KnowledgeSyncPayload(attachment));
    }

    public void syncEmc(ServerPlayer serverPlayer) {
        PacketDistributor.sendToPlayer(serverPlayer, new KnowledgeSyncEmcPayload(attachment.getEmc()));
    }
}
