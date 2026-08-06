package com.skd.equivalentlegacy.api;

import com.skd.equivalentlegacy.emc.nss.NSSItem;
import net.minecraft.server.level.ServerPlayer;

/**
 * Public provider for transmutation knowledge of a player. External mods may read or extend which
 * items a player has learned.
 */
public interface IKnowledgeProvider {
    boolean hasKnowledge(ServerPlayer player, NSSItem item);

    boolean learnItem(ServerPlayer player, NSSItem item);

    boolean forgetItem(ServerPlayer player, NSSItem item);
}