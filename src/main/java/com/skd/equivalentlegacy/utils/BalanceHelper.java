package com.skd.equivalentlegacy.utils;

import java.util.List;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.config.ServerConfig;
import net.minecraft.world.level.Level;

/**
 * Accessors for the server side survival balance options.
 *
 * <p>Whenever the balance system is disabled ({@code balance.enabled = false}) every accessor returns the legacy value, so call sites can stay one-liners and the
 * pre-balance behaviour is preserved exactly.
 */
public final class BalanceHelper {

	private BalanceHelper() {
	}

	private static ServerConfig.Balance balance() {
		return EquivalentLegacyConfig.server.balance;
	}

	public static boolean enabled() {
		return balance().enabled.getOrDefault();
	}

	public static double effectEmcMultiplier() {
		return enabled() ? balance().effectEmcMultiplier.getOrDefault() : 1.0;
	}

	public static List<? extends String> dimensionBlacklist() {
		return enabled() ? balance().dimensionBlacklist.getOrDefault() : List.<String>of();
	}

	public static boolean isAbilityAllowed(Level level) {
		return !enabled() || !dimensionBlacklist().contains(level.dimension().identifier().toString());
	}

	public static long scaleEmc(long emc) {
		if (emc <= 0) {
			return emc;
		}
		double scaled = emc * effectEmcMultiplier();
		return scaled >= Long.MAX_VALUE ? Long.MAX_VALUE : (long) scaled;
	}

	//Repair Talisman
	public static boolean repairEnabled() {
		return !enabled() || balance().repair.enabled.getOrDefault();
	}

	public static long emcPerRepair() {
		return enabled() ? balance().repair.emcPerRepair.getOrDefault() : 0L;
	}

	public static boolean includeCurios() {
		return !enabled() || balance().repair.includeCurios.getOrDefault();
	}

	public static boolean includeAlchStorage() {
		return !enabled() || balance().repair.includeAlchStorage.getOrDefault();
	}

	//Auto survival (Body/Soul/Life Stone, Gem Helmet heal, Gem Chest feed)
	public static boolean autoSurvivalEnabled() {
		return !enabled() || balance().autoSurvival.enabled.getOrDefault();
	}

	public static long emcPerHeal() {
		return enabled() ? balance().autoSurvival.emcPerHeal.getOrDefault() : 64L;
	}

	public static long emcPerFeed() {
		return enabled() ? balance().autoSurvival.emcPerFeed.getOrDefault() : 64L;
	}

	public static double healAmount() {
		return enabled() ? balance().autoSurvival.healAmount.getOrDefault() : 2.0;
	}

	public static int feedAmount() {
		return enabled() ? balance().autoSurvival.feedAmount.getOrDefault() : 2;
	}

	public static boolean requireCurio() {
		return enabled() && balance().autoSurvival.requireCurio.getOrDefault();
	}

	//Gem armor
	public static boolean gemArmorEnabled() {
		return !enabled() || balance().gemArmor.enabled.getOrDefault();
	}

	public static boolean noFall() {
		return !enabled() || balance().gemArmor.noFall.getOrDefault();
	}

	public static double speedBonus() {
		return enabled() ? balance().gemArmor.speedBonus.getOrDefault() : 1.0;
	}

	//Fire immunity
	public static boolean fireImmunityEnabled() {
		return !enabled() || balance().fireImmunity.enabled.getOrDefault();
	}

	//Walk on fluids
	public static boolean walkOnFluidsEnabled() {
		return !enabled() || balance().walkOnFluids.enabled.getOrDefault();
	}

	//SWRG
	public static boolean swrgEnabled() {
		return !enabled() || balance().swrg.enabled.getOrDefault();
	}

	public static double swrgEmcPerTick() {
		return enabled() ? balance().swrg.emcPerTick.getOrDefault() : 0.32;
	}

	public static double swrgRepelRadius() {
		return enabled() ? balance().swrg.repelRadius.getOrDefault() : 5.0;
	}

	//Armor
	public static double armorReductionMultiplier() {
		return enabled() ? balance().armor.reductionMultiplier.getOrDefault() : 1.0;
	}

	public static double armorAbsorbMultiplier() {
		return enabled() ? balance().armor.absorbMultiplier.getOrDefault() : 1.0;
	}

	public static boolean armorDurabilityLoss() {
		return enabled() && balance().armor.durabilityLoss.getOrDefault();
	}

	//Tools
	public static boolean infiniteDurability() {
		return !enabled() || balance().tools.infiniteDurability.getOrDefault();
	}

	public static int veinMineMaxBlocks() {
		return enabled() ? balance().tools.veinMineMaxBlocks.getOrDefault() : 250;
	}

	public static double aoeRadiusMultiplier() {
		return enabled() ? balance().tools.aoeRadiusMultiplier.getOrDefault() : 1.0;
	}

	//Combat
	public static boolean katarEnabled() {
		return !enabled() || balance().combat.katarEnabled.getOrDefault();
	}

	public static boolean armorPiercing() {
		return !enabled() || balance().combat.armorPiercing.getOrDefault();
	}

	public static boolean attackAoe() {
		return !enabled() || balance().combat.attackAoe.getOrDefault();
	}

	//EMC (wired in A2)
	public static boolean emcEnabled() {
		return !enabled() || balance().emc.enabled.getOrDefault();
	}

	public static double emcGenerationMultiplier() {
		return enabled() ? balance().emc.generationMultiplier.getOrDefault() : 1.0;
	}

	public static double condenserCostMultiplier() {
		return enabled() ? balance().emc.condenserCostMultiplier.getOrDefault() : 1.0;
	}

	//Collectors (wired in A2)
	public static boolean collectorsSunOnly() {
		return enabled() && balance().collectors.sunOnly.getOrDefault();
	}

	//Entropy sinks (wired in A2)
	public static double entropySinkEfficiencyFloor() {
		return enabled() ? balance().entropySinks.efficiencyFloor.getOrDefault() : 0.0;
	}

	//Stellar condenser (wired in A2)
	public static int stellarCondenserSoftCapPerMinute() {
		return enabled() ? balance().stellarCondenser.softCapPerMinute.getOrDefault() : Integer.MAX_VALUE;
	}

	//Matter furnaces (wired in A2)
	public static double dmDoubleOreChance() {
		return enabled() ? balance().matterFurnaces.dmDoubleOreChance.getOrDefault() : 0.5;
	}

	public static double rmDoubleOreChance() {
		return enabled() ? balance().matterFurnaces.rmDoubleOreChance.getOrDefault() : 1.0;
	}

	//Mercurial eye (wired in A2)
	public static int mercurialEyeMaxArea() {
		return enabled() ? balance().mercurialEye.maxArea.getOrDefault() : 0;
	}

	//Catalysts (wired in A2)
	public static long catalystEmcPerBlock() {
		return enabled() ? balance().catalysts.emcPerBlock.getOrDefault() : 8L;
	}

	//Explosive lens (wired in A2)
	public static double lensMaxRadius() {
		return enabled() ? balance().lens.maxRadius.getOrDefault() : 16.0;
	}

	//Philosopher's stone (wired in A2)
	public static boolean philosophersStoneWorldTransmute() {
		return !enabled() || balance().philosophersStone.worldTransmute.getOrDefault();
	}

	public static boolean philosophersStoneMobRandomizer() {
		return !enabled() || balance().philosophersStone.mobRandomizer.getOrDefault();
	}

	//Watch of flowing time (wired in A2)
	public static int timeWatchInventoryBonus() {
		return enabled() ? balance().timeWatch.inventoryBonus.getOrDefault() : 16;
	}
}
