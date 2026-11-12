package com.skd.equivalentlegacy.config;

import com.skd.equivalentlegacy.config.value.CachedBooleanValue;
import com.skd.equivalentlegacy.config.value.CachedDoubleValue;
import com.skd.equivalentlegacy.config.value.CachedFloatValue;
import com.skd.equivalentlegacy.config.value.CachedIntValue;
import com.skd.equivalentlegacy.config.value.CachedLongValue;
import com.skd.equivalentlegacy.config.value.CachedStringListValue;
import java.util.List;
import net.minecraft.SharedConstants;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * For config options that the server has absolute say over
 */
public final class ServerConfig extends BasePEConfig {

	private final ModConfigSpec configSpec;

	public final Balance balance;
	public final Difficulty difficulty;
	public final Items items;
	public final Effects effects;
	public final Misc misc;
	public final Cooldown cooldown;

	ServerConfig() {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		balance = new Balance(this, builder);
		cooldown = new Cooldown(this, builder);
		difficulty = new Difficulty(this, builder);
		effects = new Effects(this, builder);
		items = new Items(this, builder);
		misc = new Misc(this, builder);
		configSpec = builder.build();
	}

	@Override
	public String getFileName() {
		return "server";
	}

	@Override
	public String getTranslation() {
		return "Server Config";
	}

	@Override
	public ModConfigSpec getConfigSpec() {
		return configSpec;
	}

	@Override
	public ModConfig.Type getConfigType() {
		return ModConfig.Type.SERVER;
	}

	public static class Balance {

		public final CachedBooleanValue enabled;
		public final CachedDoubleValue effectEmcMultiplier;
		public final CachedStringListValue dimensionBlacklist;

		public final Repair repair;
		public final AutoSurvival autoSurvival;
		public final GemArmor gemArmor;
		public final FireImmunity fireImmunity;
		public final WalkOnFluids walkOnFluids;
		public final Swrg swrg;
		public final Armor armor;
		public final Tools tools;
		public final Combat combat;
		public final Emc emc;
		public final Collectors collectors;
		public final EntropySinks entropySinks;
		public final StellarCondenser stellarCondenser;
		public final MatterFurnaces matterFurnaces;
		public final MercurialEye mercurialEye;
		public final Catalysts catalysts;
		public final Lens lens;
		public final PhilosophersStone philosophersStone;
		public final TimeWatch timeWatch;

		private Balance(IPEConfig config, ModConfigSpec.Builder builder) {
			PEConfigTranslations.SERVER_BALANCE.applyToBuilder(builder).push("balance");
			// wired in A1a (used by BalanceHelper itself)
			enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_ENABLED.applyToBuilder(builder)
					.define("enabled", true));
			// wired
			effectEmcMultiplier = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_EFFECT_EMC_MULTIPLIER.applyToBuilder(builder)
					.defineInRange("effectEmcMultiplier", 1.0, 0.0, 100.0));
			// wired
			dimensionBlacklist = CachedStringListValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_DIMENSION_BLACKLIST.applyToBuilder(builder)
					.defineListAllowEmpty("dimensionBlacklist", List.<String>of(), () -> "minecraft:overworld", o -> o instanceof String));
			repair = new Repair(config, builder);
			autoSurvival = new AutoSurvival(config, builder);
			gemArmor = new GemArmor(config, builder);
			fireImmunity = new FireImmunity(config, builder);
			walkOnFluids = new WalkOnFluids(config, builder);
			swrg = new Swrg(config, builder);
			armor = new Armor(config, builder);
			tools = new Tools(config, builder);
			combat = new Combat(config, builder);
			emc = new Emc(config, builder);
			collectors = new Collectors(config, builder);
			entropySinks = new EntropySinks(config, builder);
			stellarCondenser = new StellarCondenser(config, builder);
			matterFurnaces = new MatterFurnaces(config, builder);
			mercurialEye = new MercurialEye(config, builder);
			catalysts = new Catalysts(config, builder);
			lens = new Lens(config, builder);
			philosophersStone = new PhilosophersStone(config, builder);
			timeWatch = new TimeWatch(config, builder);
			builder.pop();
		}

		public static class Repair {

			public final CachedBooleanValue enabled;
			public final CachedLongValue emcPerRepair;
			public final CachedBooleanValue includeCurios;
			public final CachedBooleanValue includeAlchStorage;

			private Repair(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_REPAIR.applyToBuilder(builder).push("repair");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_REPAIR_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				// wired
				emcPerRepair = CachedLongValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_REPAIR_EMC_PER_REPAIR.applyToBuilder(builder)
						.defineInRange("emcPerRepair", 4L, 0L, Long.MAX_VALUE));
				// wired
				includeCurios = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_REPAIR_INCLUDE_CURIOS.applyToBuilder(builder)
						.define("includeCurios", true));
				// wired
				includeAlchStorage = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_REPAIR_INCLUDE_ALCH_STORAGE.applyToBuilder(builder)
						.define("includeAlchStorage", true));
				builder.pop();
			}
		}

		public static class AutoSurvival {

			public final CachedBooleanValue enabled;
			public final CachedLongValue emcPerHeal;
			public final CachedLongValue emcPerFeed;
			public final CachedDoubleValue healAmount;
			public final CachedIntValue feedAmount;
			public final CachedBooleanValue requireCurio;

			private AutoSurvival(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL.applyToBuilder(builder).push("autoSurvival");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				// wired
				emcPerHeal = CachedLongValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL_EMC_PER_HEAL.applyToBuilder(builder)
						.defineInRange("emcPerHeal", 256L, 0L, Long.MAX_VALUE));
				// wired
				emcPerFeed = CachedLongValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL_EMC_PER_FEED.applyToBuilder(builder)
						.defineInRange("emcPerFeed", 256L, 0L, Long.MAX_VALUE));
				// wired
				healAmount = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL_HEAL_AMOUNT.applyToBuilder(builder)
						.defineInRange("healAmount", 1.0, 0.0, 20.0));
				// wired
				feedAmount = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL_FEED_AMOUNT.applyToBuilder(builder)
						.defineInRange("feedAmount", 1, 0, 20));
				// wired
				requireCurio = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_AUTO_SURVIVAL_REQUIRE_CURIO.applyToBuilder(builder)
						.define("requireCurio", true));
				builder.pop();
			}
		}

		public static class GemArmor {

			public final CachedBooleanValue enabled;
			public final CachedBooleanValue noFall;
			public final CachedDoubleValue speedBonus;

			private GemArmor(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_GEM_ARMOR.applyToBuilder(builder).push("gemArmor");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_GEM_ARMOR_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				// wired
				noFall = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_GEM_ARMOR_NO_FALL.applyToBuilder(builder)
						.define("noFall", false));
				// wired
				speedBonus = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_GEM_ARMOR_SPEED_BONUS.applyToBuilder(builder)
						.defineInRange("speedBonus", 0.5, 0.0, 2.0));
				builder.pop();
			}
		}

		public static class FireImmunity {

			public final CachedBooleanValue enabled;

			private FireImmunity(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_FIRE_IMMUNITY.applyToBuilder(builder).push("fireImmunity");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_FIRE_IMMUNITY_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				builder.pop();
			}
		}

		public static class WalkOnFluids {

			public final CachedBooleanValue enabled;

			private WalkOnFluids(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_WALK_ON_FLUIDS.applyToBuilder(builder).push("walkOnFluids");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_WALK_ON_FLUIDS_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				builder.pop();
			}
		}

		public static class Swrg {

			public final CachedBooleanValue enabled;
			public final CachedDoubleValue emcPerTick;
			public final CachedDoubleValue repelRadius;

			private Swrg(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_SWRG.applyToBuilder(builder).push("swrg");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_SWRG_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				// wired
				emcPerTick = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_SWRG_EMC_PER_TICK.applyToBuilder(builder)
						.defineInRange("emcPerTick", 1.0, 0.0, 100.0));
				// wired
				repelRadius = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_SWRG_REPEL_RADIUS.applyToBuilder(builder)
						.defineInRange("repelRadius", 3.0, 0.0, 32.0));
				builder.pop();
			}
		}

		public static class Armor {

			public final CachedDoubleValue reductionMultiplier;
			public final CachedDoubleValue absorbMultiplier;
			public final CachedBooleanValue durabilityLoss;

			private Armor(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_ARMOR.applyToBuilder(builder).push("armor");
				// wired
				reductionMultiplier = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_ARMOR_REDUCTION_MULTIPLIER.applyToBuilder(builder)
						.defineInRange("reductionMultiplier", 0.7, 0.0, 1.0));
				// wired
				absorbMultiplier = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_ARMOR_ABSORB_MULTIPLIER.applyToBuilder(builder)
						.defineInRange("absorbMultiplier", 1.0, 0.0, 10.0));
				// wired
				durabilityLoss = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_ARMOR_DURABILITY_LOSS.applyToBuilder(builder)
						.define("durabilityLoss", false));
				builder.pop();
			}
		}

		public static class Tools {

			public final CachedBooleanValue infiniteDurability;
			public final CachedIntValue veinMineMaxBlocks;
			public final CachedDoubleValue aoeRadiusMultiplier;

			private Tools(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_TOOLS.applyToBuilder(builder).push("tools");
				// wired
				infiniteDurability = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_TOOLS_INFINITE_DURABILITY.applyToBuilder(builder)
						.define("infiniteDurability", true));
				// wired
				veinMineMaxBlocks = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_TOOLS_VEIN_MINE_MAX_BLOCKS.applyToBuilder(builder)
						.defineInRange("veinMineMaxBlocks", 64, 0, 1000));
				// wired
				aoeRadiusMultiplier = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_TOOLS_AOE_RADIUS_MULTIPLIER.applyToBuilder(builder)
						.defineInRange("aoeRadiusMultiplier", 0.5, 0.0, 1.0));
				builder.pop();
			}
		}

		public static class Combat {

			public final CachedBooleanValue katarEnabled;
			public final CachedBooleanValue armorPiercing;
			public final CachedBooleanValue attackAoe;

			private Combat(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_COMBAT.applyToBuilder(builder).push("combat");
				// wired
				katarEnabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_COMBAT_KATAR_ENABLED.applyToBuilder(builder)
						.define("katarEnabled", true));
				// wired
				armorPiercing = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_COMBAT_ARMOR_PIERCING.applyToBuilder(builder)
						.define("armorPiercing", false));
				// wired
				attackAoe = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_COMBAT_ATTACK_AOE.applyToBuilder(builder)
						.define("attackAoe", true));
				builder.pop();
			}
		}

		public static class Emc {

			public final CachedBooleanValue enabled;
			public final CachedDoubleValue generationMultiplier;
			public final CachedDoubleValue condenserCostMultiplier;

			private Emc(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_EMC.applyToBuilder(builder).push("emc");
				// wired
				enabled = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_EMC_ENABLED.applyToBuilder(builder)
						.define("enabled", true));
				generationMultiplier = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_EMC_GENERATION_MULTIPLIER.applyToBuilder(builder)
						.defineInRange("generationMultiplier", 0.25, 0.0, 100.0));
				condenserCostMultiplier = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_EMC_CONDENSER_COST_MULTIPLIER.applyToBuilder(builder)
						.defineInRange("condenserCostMultiplier", 2.0, 1.0, 100.0));
				builder.pop();
			}
		}

		public static class Collectors {

			public final CachedBooleanValue sunOnly;

			private Collectors(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_COLLECTORS.applyToBuilder(builder).push("collectors");
				// wired
				sunOnly = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_COLLECTORS_SUN_ONLY.applyToBuilder(builder)
						.define("sunOnly", true));
				builder.pop();
			}
		}

		public static class EntropySinks {

			public final CachedDoubleValue efficiencyFloor;

			private EntropySinks(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_ENTROPY_SINKS.applyToBuilder(builder).push("entropySinks");
				// wired
				efficiencyFloor = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_ENTROPY_SINKS_EFFICIENCY_FLOOR.applyToBuilder(builder)
						.defineInRange("efficiencyFloor", 0.05, 0.0, 1.0));
				builder.pop();
			}
		}

		public static class StellarCondenser {

			public final CachedIntValue softCapPerMinute;

			private StellarCondenser(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_STELLAR_CONDENSER.applyToBuilder(builder).push("stellarCondenser");
				// wired
				softCapPerMinute = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_STELLAR_CONDENSER_SOFT_CAP_PER_MINUTE.applyToBuilder(builder)
						.defineInRange("softCapPerMinute", 512, 0, Integer.MAX_VALUE));
				builder.pop();
			}
		}

		public static class MatterFurnaces {

			public final CachedDoubleValue dmDoubleOreChance;
			public final CachedDoubleValue rmDoubleOreChance;

			private MatterFurnaces(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_MATTER_FURNACES.applyToBuilder(builder).push("matterFurnaces");
				// wired
				dmDoubleOreChance = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_MATTER_FURNACES_DM_DOUBLE_ORE_CHANCE.applyToBuilder(builder)
						.defineInRange("dmDoubleOreChance", 0.25, 0.0, 1.0));
				rmDoubleOreChance = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_MATTER_FURNACES_RM_DOUBLE_ORE_CHANCE.applyToBuilder(builder)
						.defineInRange("rmDoubleOreChance", 0.0, 0.0, 1.0));
				builder.pop();
			}
		}

		public static class MercurialEye {

			public final CachedIntValue maxArea;

			private MercurialEye(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_MERCURIAL_EYE.applyToBuilder(builder).push("mercurialEye");
				// wired
				maxArea = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_MERCURIAL_EYE_MAX_AREA.applyToBuilder(builder)
						.defineInRange("maxArea", 32, 0, Integer.MAX_VALUE));
				builder.pop();
			}
		}

		public static class Catalysts {

			public final CachedLongValue emcPerBlock;

			private Catalysts(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_CATALYSTS.applyToBuilder(builder).push("catalysts");
				// wired
				emcPerBlock = CachedLongValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_CATALYSTS_EMC_PER_BLOCK.applyToBuilder(builder)
						.defineInRange("emcPerBlock", 32L, 0L, Long.MAX_VALUE));
				builder.pop();
			}
		}

		public static class Lens {

			public final CachedDoubleValue maxRadius;

			private Lens(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_LENS.applyToBuilder(builder).push("lens");
				// wired
				maxRadius = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_LENS_MAX_RADIUS.applyToBuilder(builder)
						.defineInRange("maxRadius", 8.0, 0.0, 16.0));
				builder.pop();
			}
		}

		public static class PhilosophersStone {

			public final CachedBooleanValue worldTransmute;
			public final CachedBooleanValue mobRandomizer;

			private PhilosophersStone(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_PHILOSOPHERS_STONE.applyToBuilder(builder).push("philosophersStone");
				// wired
				worldTransmute = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_PHILOSOPHERS_STONE_WORLD_TRANSMUTE.applyToBuilder(builder)
						.define("worldTransmute", true));
				mobRandomizer = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_PHILOSOPHERS_STONE_MOB_RANDOMIZER.applyToBuilder(builder)
						.define("mobRandomizer", true));
				builder.pop();
			}
		}

		public static class TimeWatch {

			public final CachedIntValue inventoryBonus;

			private TimeWatch(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_BALANCE_TIME_WATCH.applyToBuilder(builder).push("timeWatch");
				// wired
				inventoryBonus = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_BALANCE_TIME_WATCH_INVENTORY_BONUS.applyToBuilder(builder)
						.defineInRange("inventoryBonus", 16, 0, 16));
				builder.pop();
			}
		}
	}

	public static class Cooldown {

		public final Pedestal pedestal;
		public final Player player;

		private Cooldown(IPEConfig config, ModConfigSpec.Builder builder) {
			PEConfigTranslations.SERVER_COOLDOWN.applyToBuilder(builder).push("cooldown");
			pedestal = new Pedestal(config, builder);
			player = new Player(config, builder);
			builder.pop();
		}

		public static class Pedestal {

			public final CachedIntValue archangel;
			public final CachedIntValue body;
			public final CachedIntValue evertide;
			public final CachedIntValue harvest;
			public final CachedIntValue ignition;
			public final CachedIntValue life;
			public final CachedIntValue repair;
			public final CachedIntValue swrg;
			public final CachedIntValue soul;
			public final CachedIntValue volcanite;
			public final CachedIntValue zero;

			private Pedestal(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL.applyToBuilder(builder).push("pedestal");
				archangel = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_ARCHANGEL.applyToBuilder(builder)
						.defineInRange("archangel", 4 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				body = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_BODY_STONE.applyToBuilder(builder)
						.defineInRange("body", SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				evertide = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_EVERTIDE.applyToBuilder(builder)
						.defineInRange("evertide", 2 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				harvest = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_HARVEST.applyToBuilder(builder)
						.defineInRange("harvest", SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				ignition = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_IGNITION.applyToBuilder(builder)
						.defineInRange("ignition", 4 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				life = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_LIFE_STONE.applyToBuilder(builder)
						.defineInRange("life", SharedConstants.TICKS_PER_SECOND / 2, -1, Integer.MAX_VALUE));
				repair = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_REPAIR.applyToBuilder(builder)
						.defineInRange("repair", 2 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				swrg = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_SWRG.applyToBuilder(builder)
						.defineInRange("swrg", 7 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				soul = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_SOUL_STONE.applyToBuilder(builder)
						.defineInRange("soul", SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				volcanite = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_VOLCANITE.applyToBuilder(builder)
						.defineInRange("volcanite", 2 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				zero = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PEDESTAL_ZERO.applyToBuilder(builder)
						.defineInRange("zero", 4 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				builder.pop();
			}
		}

		public static class Player {

			public final CachedIntValue projectile;
			public final CachedIntValue gemChest;
			public final CachedIntValue repair;
			public final CachedIntValue heal;
			public final CachedIntValue feed;

			private Player(IPEConfig config, ModConfigSpec.Builder builder) {
				PEConfigTranslations.SERVER_COOLDOWN_PLAYER.applyToBuilder(builder).push("player");
				projectile = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PLAYER_PROJECTILE.applyToBuilder(builder)
						.defineInRange("projectile", SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				gemChest = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PLAYER_GEM_CHESTPLATE.applyToBuilder(builder)
						.defineInRange("gemChest", 0, -1, Integer.MAX_VALUE));
				repair = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PLAYER_REPAIR.applyToBuilder(builder)
						.defineInRange("repair", 10 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				heal = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PLAYER_HEAL.applyToBuilder(builder)
						.defineInRange("heal", 10 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				feed = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_COOLDOWN_PLAYER_FEED.applyToBuilder(builder)
						.defineInRange("feed", 10 * SharedConstants.TICKS_PER_SECOND, -1, Integer.MAX_VALUE));
				builder.pop();
			}
		}
	}

	public static class Difficulty {

		public final CachedBooleanValue offensiveAbilities;
		public final CachedFloatValue katarDeathAura;
		public final CachedDoubleValue covalenceLoss;
		public final CachedBooleanValue covalenceLossRounding;

		private Difficulty(IPEConfig config, ModConfigSpec.Builder builder) {
			PEConfigTranslations.SERVER_DIFFICULTY.applyToBuilder(builder).push("difficulty");
			offensiveAbilities = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_DIFFICULTY_OFFENSIVE_ABILITIES.applyToBuilder(builder)
					.define("offensiveAbilities", false));
			katarDeathAura = CachedFloatValue.wrap(config, PEConfigTranslations.SERVER_DIFFICULTY_KATAR_DEATH_AURA.applyToBuilder(builder)
					.defineInRange("katarDeathAura", 12.0F, 0, Integer.MAX_VALUE));
			covalenceLoss = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_DIFFICULTY_COVALENCE_LOSS.applyToBuilder(builder)
					.defineInRange("covalenceLoss", 0.5, 0.1, 1.0));
			covalenceLossRounding = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_DIFFICULTY_COVALENCE_LOSS_ROUNDING.applyToBuilder(builder)
					.define("covalenceLossRounding", true));
			builder.pop();
		}
	}

	public static class Effects {

		public final CachedIntValue timePedBonus;
		public final CachedDoubleValue timePedMobSlowness;
		public final CachedBooleanValue interdictionMode;

		private Effects(IPEConfig config, ModConfigSpec.Builder builder) {
			PEConfigTranslations.SERVER_EFFECTS.applyToBuilder(builder).push("effects");
			timePedBonus = CachedIntValue.wrap(config, PEConfigTranslations.SERVER_EFFECTS_TIME_PEDESTAL_BONUS.applyToBuilder(builder)
					.defineInRange("timePedBonus", 4, 0, 15));
			timePedMobSlowness = CachedDoubleValue.wrap(config, PEConfigTranslations.SERVER_EFFECTS_TIME_PEDESTAL_MOB_SLOWNESS.applyToBuilder(builder)
					.defineInRange("timePedMobSlowness", 0.5, 0, 1));
			interdictionMode = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_EFFECTS_INTERDICTION_MODE.applyToBuilder(builder)
					.define("interdictionMode", true));
			builder.pop();
		}
	}

	public static class Items {

		public final CachedBooleanValue pickaxeAoeVeinMining;
		public final CachedBooleanValue harvBandIndirect;
		public final CachedBooleanValue disableAllRadiusMining;
		public final CachedBooleanValue enableTimeWatch;
		public final CachedBooleanValue opEvertide;

		private Items(IPEConfig config, ModConfigSpec.Builder builder) {
			PEConfigTranslations.SERVER_ITEMS.applyToBuilder(builder).push("items");
			pickaxeAoeVeinMining = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_ITEMS_PICKAXE_AOE_VEIN_MINING.applyToBuilder(builder)
					.define("pickaxeAoeVeinMining", false));
			harvBandIndirect = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_ITEMS_HARVEST_BAND_INDIRECT.applyToBuilder(builder)
					.define("harvBandIndirect", false));
			disableAllRadiusMining = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_ITEMS_DISABLE_ALL_RADIUS_MINING.applyToBuilder(builder)
					.define("disableAllRadiusMining", false));
			enableTimeWatch = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_ITEMS_TIME_WATCH.applyToBuilder(builder)
					.define("enableTimeWatch", false));
			opEvertide = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_ITEMS_OP_EVERTIDE.applyToBuilder(builder)
					.define("opEvertide", false));
			builder.pop();
		}
	}

	public static class Misc {

		public final CachedBooleanValue unsafeKeyBinds;
		public final CachedBooleanValue lookingAtDisplay;

		private Misc(IPEConfig config, ModConfigSpec.Builder builder) {
			PEConfigTranslations.SERVER_MISC.applyToBuilder(builder).push("misc");
			unsafeKeyBinds = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_MISC_UNSAFE_KEY_BINDS.applyToBuilder(builder)
					.define("unsafeKeyBinds", false));
			lookingAtDisplay = CachedBooleanValue.wrap(config, PEConfigTranslations.SERVER_MISC_LOOKING_AT_DISPLAY.applyToBuilder(builder)
					.define("lookingAtDisplay", true));
			builder.pop();
		}
	}
}