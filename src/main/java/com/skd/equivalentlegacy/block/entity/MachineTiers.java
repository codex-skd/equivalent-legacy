package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
import net.minecraft.world.level.block.Block;

public final class MachineTiers {
    public enum CollectorTier {
        MK1(4, 10_000L),
        MK2(12, 60_000L),
        MK3(40, 200_000L);

        public final int rate;
        public final long emcCapacity;

        CollectorTier(int rate, long emcCapacity) {
            this.rate = rate;
            this.emcCapacity = emcCapacity;
        }

        public static CollectorTier of(Block block) {
            if (block == EquivalentLegacyBlocks.COLLECTOR_MK3.get()) return MK3;
            if (block == EquivalentLegacyBlocks.COLLECTOR_MK2.get()) return MK2;
            return MK1;
        }
    }

    public enum RelayTier {
        MK1(64, 100_000L),
        MK2(128, 1_000_000L),
        MK3(256, 10_000_000L);

        public final int transferRate;
        public final long emcCapacity;

        RelayTier(int transferRate, long emcCapacity) {
            this.transferRate = transferRate;
            this.emcCapacity = emcCapacity;
        }

        public static RelayTier of(Block block) {
            if (block == EquivalentLegacyBlocks.RELAY_MK3.get()) return MK3;
            if (block == EquivalentLegacyBlocks.RELAY_MK2.get()) return MK2;
            return MK1;
        }
    }

    public enum CondenserTier {
        MK1(32, 500_000L),
        MK2(64, 2_000_000L);

        public final int transferRate;
        public final long emcCapacity;

        CondenserTier(int transferRate, long emcCapacity) {
            this.transferRate = transferRate;
            this.emcCapacity = emcCapacity;
        }

        public static CondenserTier of(Block block) {
            if (block == EquivalentLegacyBlocks.CONDENSER_MK2.get()) return MK2;
            return MK1;
        }
    }

    private MachineTiers() {}
}
