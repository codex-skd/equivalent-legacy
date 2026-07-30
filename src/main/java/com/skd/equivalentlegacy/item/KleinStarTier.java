package com.skd.equivalentlegacy.item;

public enum KleinStarTier {
    EIN("ein", 500_000L),
    ZWEI("zwei", 1_000_000L),
    DREI("drei", 2_000_000L),
    VIER("vier", 4_000_000L),
    SPHERE("sphere", 8_000_000L),
    OMEGA("omega", 16_000_000L);

    private final String name;
    private final long maxEmc;

    KleinStarTier(String name, long maxEmc) {
        this.name = name;
        this.maxEmc = maxEmc;
    }

    public String getName() {
        return name;
    }

    public long getMaxEmc() {
        return maxEmc;
    }
}
