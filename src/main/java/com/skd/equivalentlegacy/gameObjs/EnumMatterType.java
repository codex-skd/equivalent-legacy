package com.skd.equivalentlegacy.gameObjs;

public enum EnumMatterType {
    DARK_MATTER(0, 14, 3, 12, "dark_matter", 1.0F),
    RED_MATTER(1, 16, 4, 14, "red_matter", 0.8F);

    private final int tier;
    private final int efficiency;
    private final int attack;
    private final int chargeModifier;
    private final String serializedName;
    private final float fuelMultiplier;

    EnumMatterType(int tier, int efficiency, int attack, int chargeModifier, String serializedName, float fuelMultiplier) {
        this.tier = tier;
        this.efficiency = efficiency;
        this.attack = attack;
        this.chargeModifier = chargeModifier;
        this.serializedName = serializedName;
        this.fuelMultiplier = fuelMultiplier;
    }

    public int getTier() {
        return tier;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public int getAttackDamageBonus() {
        return attack;
    }

    public int getChargeModifier() {
        return chargeModifier;
    }

    public String getSerializedName() {
        return serializedName;
    }

    public float getFuelMultiplier() {
        return fuelMultiplier;
    }

    public int getSpeed() {
        return efficiency;
    }
}
