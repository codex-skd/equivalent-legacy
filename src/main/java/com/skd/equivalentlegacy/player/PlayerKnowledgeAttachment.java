package com.skd.equivalentlegacy.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PlayerKnowledgeAttachment {
    private long emc;
    private final Set<String> knowledge;
    private boolean fullKnowledge;

    public PlayerKnowledgeAttachment() {
        this.emc = 0L;
        this.knowledge = new HashSet<>();
        this.fullKnowledge = false;
    }

    public PlayerKnowledgeAttachment(long emc, Set<String> knowledge, boolean fullKnowledge) {
        this.emc = Math.max(0, emc);
        this.knowledge = new HashSet<>(knowledge);
        this.fullKnowledge = fullKnowledge;
    }

    public PlayerKnowledgeAttachment copy() {
        return new PlayerKnowledgeAttachment(this.emc, this.knowledge, this.fullKnowledge);
    }

    public long getEmc() {
        return emc;
    }

    public void setEmc(long emc) {
        this.emc = Math.max(0, emc);
    }

    public void addEmc(long amount) {
        this.emc += amount;
    }

    public boolean subtractEmc(long amount) {
        if (this.emc < amount) return false;
        this.emc -= amount;
        return true;
    }

    public Set<String> getKnowledge() {
        return Collections.unmodifiableSet(knowledge);
    }

    public boolean hasKnowledge(String itemId) {
        return fullKnowledge || knowledge.contains(itemId);
    }

    public boolean addKnowledge(String itemId) {
        if (fullKnowledge) return false;
        return knowledge.add(itemId);
    }

    public boolean removeKnowledge(String itemId) {
        if (fullKnowledge) {
            fullKnowledge = false;
            knowledge.clear();
            return true;
        }
        return knowledge.remove(itemId);
    }

    public void clearKnowledge() {
        knowledge.clear();
        fullKnowledge = false;
    }

    public boolean hasFullKnowledge() {
        return fullKnowledge;
    }

    public void setFullKnowledge(boolean fullKnowledge) {
        this.fullKnowledge = fullKnowledge;
    }

    public static final MapCodec<PlayerKnowledgeAttachment> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.LONG.optionalFieldOf("emc", 0L).forGetter(o -> o.emc),
            Codec.STRING.listOf().optionalFieldOf("knowledge", List.of()).forGetter(o -> List.copyOf(o.knowledge)),
            Codec.BOOL.optionalFieldOf("full_knowledge", false).forGetter(o -> o.fullKnowledge)
        ).apply(instance, (emc, knowledgeList, fullKnowledge) ->
            new PlayerKnowledgeAttachment(emc, new HashSet<>(knowledgeList), fullKnowledge))
    );

    public static final StreamCodec<ByteBuf, PlayerKnowledgeAttachment> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public PlayerKnowledgeAttachment decode(ByteBuf buf) {
            long emc = ByteBufCodecs.VAR_LONG.decode(buf);
            int count = ByteBufCodecs.VAR_INT.decode(buf);
            Set<String> knowledge = new HashSet<>();
            for (int i = 0; i < count; i++) {
                knowledge.add(ByteBufCodecs.STRING_UTF8.decode(buf));
            }
            boolean fullKnowledge = ByteBufCodecs.BOOL.decode(buf);
            return new PlayerKnowledgeAttachment(emc, knowledge, fullKnowledge);
        }

        @Override
        public void encode(ByteBuf buf, PlayerKnowledgeAttachment obj) {
            ByteBufCodecs.VAR_LONG.encode(buf, obj.emc);
            ByteBufCodecs.VAR_INT.encode(buf, obj.knowledge.size());
            for (String s : obj.knowledge) {
                ByteBufCodecs.STRING_UTF8.encode(buf, s);
            }
            ByteBufCodecs.BOOL.encode(buf, obj.fullKnowledge);
        }
    };
}
