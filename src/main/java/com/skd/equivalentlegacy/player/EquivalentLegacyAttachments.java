package com.skd.equivalentlegacy.player;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class EquivalentLegacyAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, EquivalentLegacy.MODID);

    public static final Supplier<AttachmentType<PlayerKnowledgeAttachment>> PLAYER_KNOWLEDGE =
            ATTACHMENT_TYPES.register("player_knowledge", () ->
                    AttachmentType.builder(PlayerKnowledgeAttachment::new)
                            .serialize(PlayerKnowledgeAttachment.MAP_CODEC)
                            .copyHandler((attachment, holder, provider) -> attachment.copy())
                            .copyOnDeath()
                            .build());

    private EquivalentLegacyAttachments() {}
}
