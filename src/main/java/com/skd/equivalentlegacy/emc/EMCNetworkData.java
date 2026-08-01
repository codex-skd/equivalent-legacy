package com.skd.equivalentlegacy.emc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class EMCNetworkData extends SavedData {
    public long emc;

    public static final SavedDataType<EMCNetworkData> TYPE = new SavedDataType<>(
            EquivalentLegacy.rl("emc_network"),
            EMCNetworkData::new,
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.LONG.fieldOf("emc").forGetter(data -> data.emc)
            ).apply(instance, EMCNetworkData::new))
    );

    public EMCNetworkData() {
        this(0L);
    }

    public EMCNetworkData(long emc) {
        this.emc = emc;
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
