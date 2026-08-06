package com.skd.equivalentlegacy.emc;

import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.emc.nss.NormalizedSimpleStack;
import net.minecraft.resources.Identifier;

/**
 * Central registry of the hardcoded EMC values used to seed the EMC graph.
 *
 * <p>Keys are built from {@link Identifier} strings (not registry lookups) so this
 * class can be safely constructed during common setup and verified by unit tests
 * without bootstrapping the game. The produced {@link NormalizedSimpleStack} keys
 * are equal to the ones produced at runtime from {@code ItemStack}s.</p>
 */
public final class EmcValues {

	private EmcValues() {}

	/**
	 * Builds the default {@link FixedValues} table (200+ entries).
	 *
	 * <p>Values follow the phase plan:
	 * Phase 1 (vanilla basics), Phase 2 (ProcessE materials), Phase 3-4 (ProjectE gems),
	 * Phase 5 (Equivalent Legacy specific).</p>
	 */
	public static FixedValues initializeDefaultValues() {
		FixedValues values = new FixedValues();

		addRawMaterials(values);
		addOres(values);
		addGems(values);
		addBlocks(values);
		addFood(values);
		addDyes(values);
		addTools(values);
		addArmor(values);
		addMobDrops(values);
		addMisc(values);
		addEquivalentLegacyItems(values);

		return values;
	}

	private static void addRawMaterials(FixedValues fv) {
		add(fv, "minecraft:dirt", 0);
		add(fv, "minecraft:oak_log", 2);
		add(fv, "minecraft:oak_wood", 2);
		add(fv, "minecraft:stone", 1);
		add(fv, "minecraft:coal", 32);
		add(fv, "minecraft:charcoal", 32);
		add(fv, "minecraft:cobblestone", 1);
		add(fv, "minecraft:sand", 1);
		add(fv, "minecraft:gravel", 1);
		add(fv, "minecraft:grass_block", 2);
		add(fv, "minecraft:clay_ball", 1);
		add(fv, "minecraft:clay", 4);
		add(fv, "minecraft:flint", 1);
		add(fv, "minecraft:stick", 1);
		add(fv, "minecraft:paper", 1);
	}

	private static void addOres(FixedValues fv) {
		add(fv, "minecraft:iron_ore", 256);
		add(fv, "minecraft:iron_ingot", 256);
		add(fv, "minecraft:deepslate_iron_ore", 256);
		add(fv, "minecraft:gold_ore", 32);
		add(fv, "minecraft:gold_ingot", 32);
		add(fv, "minecraft:deepslate_gold_ore", 32);
		add(fv, "minecraft:copper_ore", 8);
		add(fv, "minecraft:copper_ingot", 8);
		add(fv, "minecraft:deepslate_copper_ore", 8);
		add(fv, "minecraft:raw_iron", 256);
		add(fv, "minecraft:raw_gold", 32);
		add(fv, "minecraft:raw_copper", 8);
		add(fv, "minecraft:lapis_ore", 36);
		add(fv, "minecraft:lapis_lazuli", 36);
		add(fv, "minecraft:deepslate_lapis_ore", 36);
		add(fv, "minecraft:diamond_ore", 2048);
		add(fv, "minecraft:diamond", 2048);
		add(fv, "minecraft:deepslate_diamond_ore", 2048);
		add(fv, "minecraft:emerald_ore", 2048);
		add(fv, "minecraft:emerald", 2048);
		add(fv, "minecraft:deepslate_emerald_ore", 2048);
		add(fv, "minecraft:redstone_ore", 32);
		add(fv, "minecraft:redstone", 32);
		add(fv, "minecraft:deepslate_redstone_ore", 32);
		add(fv, "minecraft:netherite_scrap", 1024);
		add(fv, "minecraft:netherite_ingot", 8192);
		add(fv, "minecraft:ancient_debris", 1024);
	}

	private static void addGems(FixedValues fv) {
		add(fv, "minecraft:diamond", 2048);
		add(fv, "minecraft:emerald", 2048);
		add(fv, "minecraft:nether_star", 139264);
		add(fv, "minecraft:amethyst_shard", 64);
		add(fv, "minecraft:quartz", 16);
		add(fv, "minecraft:nether_quartz_ore", 16);
	}

	private static void addBlocks(FixedValues fv) {
		add(fv, "minecraft:oak_log", 2);
		add(fv, "minecraft:oak_planks", 1);
		add(fv, "minecraft:oak_wood", 2);
		add(fv, "minecraft:oak_leaves", 1);
		add(fv, "minecraft:spruce_log", 2);
		add(fv, "minecraft:spruce_planks", 1);
		add(fv, "minecraft:spruce_wood", 2);
		add(fv, "minecraft:birch_log", 2);
		add(fv, "minecraft:birch_planks", 1);
		add(fv, "minecraft:birch_wood", 2);
		add(fv, "minecraft:jungle_log", 2);
		add(fv, "minecraft:jungle_planks", 1);
		add(fv, "minecraft:jungle_wood", 2);
		add(fv, "minecraft:acacia_log", 2);
		add(fv, "minecraft:acacia_planks", 1);
		add(fv, "minecraft:acacia_wood", 2);
		add(fv, "minecraft:dark_oak_log", 2);
		add(fv, "minecraft:dark_oak_planks", 1);
		add(fv, "minecraft:dark_oak_wood", 2);
		add(fv, "minecraft:mangrove_log", 2);
		add(fv, "minecraft:mangrove_planks", 1);
		add(fv, "minecraft:mangrove_wood", 2);
		add(fv, "minecraft:cherry_log", 2);
		add(fv, "minecraft:cherry_planks", 1);
		add(fv, "minecraft:cherry_wood", 2);
		add(fv, "minecraft:crimson_stem", 2);
		add(fv, "minecraft:crimson_planks", 1);
		add(fv, "minecraft:warped_stem", 2);
		add(fv, "minecraft:warped_planks", 1);
		add(fv, "minecraft:glass", 1);
		add(fv, "minecraft:glowstone_dust", 10);
		add(fv, "minecraft:glowstone", 40);
		add(fv, "minecraft:obsidian", 64);
		add(fv, "minecraft:netherrack", 1);
		add(fv, "minecraft:end_stone", 1);
		add(fv, "minecraft:purpur_block", 4);
		add(fv, "minecraft:soul_sand", 2);
		add(fv, "minecraft:soul_soil", 2);
		add(fv, "minecraft:blackstone", 1);
		add(fv, "minecraft:iron_block", 2304);
		add(fv, "minecraft:gold_block", 288);
		add(fv, "minecraft:diamond_block", 18432);
		add(fv, "minecraft:emerald_block", 18432);
		add(fv, "minecraft:redstone_block", 288);
		add(fv, "minecraft:lapis_block", 324);
		add(fv, "minecraft:netherite_block", 73728);
		add(fv, "minecraft:copper_block", 72);
		add(fv, "minecraft:quartz_block", 64);
		add(fv, "minecraft:quartz_pillar", 64);
	}

	private static void addFood(FixedValues fv) {
		add(fv, "minecraft:wheat", 1);
		add(fv, "minecraft:apple", 8);
		add(fv, "minecraft:golden_apple", 8192);
		add(fv, "minecraft:enchanted_golden_apple", 131072);
		add(fv, "minecraft:carrot", 2);
		add(fv, "minecraft:golden_carrot", 256);
		add(fv, "minecraft:potato", 1);
		add(fv, "minecraft:baked_potato", 1);
		add(fv, "minecraft:poisonous_potato", 1);
		add(fv, "minecraft:pumpkin", 4);
		add(fv, "minecraft:pumpkin_seeds", 1);
		add(fv, "minecraft:melon", 2);
		add(fv, "minecraft:melon_seeds", 1);
		add(fv, "minecraft:sugar_cane", 1);
		add(fv, "minecraft:sugar", 1);
		add(fv, "minecraft:honey_block", 16);
		add(fv, "minecraft:honeycomb", 16);
		add(fv, "minecraft:cocoa_beans", 1);
		add(fv, "minecraft:kelp", 1);
		add(fv, "minecraft:dried_kelp", 1);
		add(fv, "minecraft:sea_pickle", 2);
		add(fv, "minecraft:bread", 4);
		add(fv, "minecraft:egg", 2);
	}

	private static void addDyes(FixedValues fv) {
		add(fv, "minecraft:bone_meal", 1);
		add(fv, "minecraft:ink_sac", 1);
		add(fv, "minecraft:cocoa_beans", 1);
		add(fv, "minecraft:lapis_lazuli", 36);
		add(fv, "minecraft:cactus", 1);
		add(fv, "minecraft:pumpkin", 4);
		add(fv, "minecraft:dandelion", 1);
		add(fv, "minecraft:poppy", 1);
		add(fv, "minecraft:blue_orchid", 1);
		add(fv, "minecraft:allium", 1);
		add(fv, "minecraft:azure_bluet", 1);
		add(fv, "minecraft:red_tulip", 1);
		add(fv, "minecraft:orange_tulip", 1);
		add(fv, "minecraft:white_tulip", 1);
		add(fv, "minecraft:pink_tulip", 1);
		add(fv, "minecraft:oxeye_daisy", 1);
		add(fv, "minecraft:cornflower", 1);
		add(fv, "minecraft:lily_of_the_valley", 1);
	}

	private static void addTools(FixedValues fv) {
		add(fv, "minecraft:wooden_pickaxe", 3);
		add(fv, "minecraft:wooden_axe", 3);
		add(fv, "minecraft:wooden_shovel", 2);
		add(fv, "minecraft:wooden_hoe", 2);
		add(fv, "minecraft:stone_pickaxe", 4);
		add(fv, "minecraft:stone_axe", 4);
		add(fv, "minecraft:stone_shovel", 3);
		add(fv, "minecraft:stone_hoe", 3);
		add(fv, "minecraft:iron_pickaxe", 768);
		add(fv, "minecraft:iron_axe", 768);
		add(fv, "minecraft:iron_shovel", 512);
		add(fv, "minecraft:iron_hoe", 512);
		add(fv, "minecraft:diamond_pickaxe", 2048);
		add(fv, "minecraft:diamond_axe", 2048);
		add(fv, "minecraft:diamond_shovel", 1368);
		add(fv, "minecraft:diamond_hoe", 1368);
		add(fv, "minecraft:netherite_pickaxe", 8192);
		add(fv, "minecraft:netherite_axe", 8192);
		add(fv, "minecraft:netherite_shovel", 5456);
		add(fv, "minecraft:netherite_hoe", 5456);
	}

	private static void addArmor(FixedValues fv) {
		add(fv, "minecraft:leather_helmet", 5);
		add(fv, "minecraft:leather_chestplate", 8);
		add(fv, "minecraft:leather_leggings", 7);
		add(fv, "minecraft:leather_boots", 4);
		add(fv, "minecraft:iron_helmet", 768);
		add(fv, "minecraft:iron_chestplate", 1280);
		add(fv, "minecraft:iron_leggings", 1120);
		add(fv, "minecraft:iron_boots", 512);
		add(fv, "minecraft:diamond_helmet", 2048);
		add(fv, "minecraft:diamond_chestplate", 3456);
		add(fv, "minecraft:diamond_leggings", 3024);
		add(fv, "minecraft:diamond_boots", 1368);
		add(fv, "minecraft:netherite_helmet", 8192);
		add(fv, "minecraft:netherite_chestplate", 13824);
		add(fv, "minecraft:netherite_leggings", 12096);
		add(fv, "minecraft:netherite_boots", 5456);
	}

	private static void addMobDrops(FixedValues fv) {
		add(fv, "minecraft:rotten_flesh", 1);
		add(fv, "minecraft:bone", 2);
		add(fv, "minecraft:gunpowder", 8);
		add(fv, "minecraft:string", 1);
		add(fv, "minecraft:spider_eye", 8);
		add(fv, "minecraft:fermented_spider_eye", 10);
		add(fv, "minecraft:slime_ball", 2);
		add(fv, "minecraft:ender_pearl", 16);
		add(fv, "minecraft:ender_eye", 24);
		add(fv, "minecraft:blaze_rod", 16);
		add(fv, "minecraft:blaze_powder", 4);
		add(fv, "minecraft:magma_cream", 10);
		add(fv, "minecraft:ghast_tear", 32);
		add(fv, "minecraft:shulker_shell", 128);
		add(fv, "minecraft:phantom_membrane", 16);
		add(fv, "minecraft:dragon_breath", 512);
	}

	private static void addMisc(FixedValues fv) {
		add(fv, "minecraft:paper", 1);
		add(fv, "minecraft:book", 4);
		add(fv, "minecraft:enchanted_book", 256);
		add(fv, "minecraft:stick", 1);
		add(fv, "minecraft:leather", 4);
		add(fv, "minecraft:saddle", 32);
		add(fv, "minecraft:name_tag", 32);
		add(fv, "minecraft:ender_chest", 512);
		add(fv, "minecraft:anvil", 456);
		add(fv, "minecraft:bucket", 24);
		add(fv, "minecraft:water_bucket", 24);
		add(fv, "minecraft:lava_bucket", 48);
		add(fv, "minecraft:milk_bucket", 24);
		add(fv, "minecraft:powder_snow_bucket", 24);
		add(fv, "minecraft:piston", 32);
		add(fv, "minecraft:sticky_piston", 40);
		add(fv, "minecraft:redstone_lamp", 32);
		add(fv, "minecraft:redstone_torch", 8);
		add(fv, "minecraft:detector_rail", 24);
		add(fv, "minecraft:powered_rail", 32);
		add(fv, "minecraft:torch", 4);
		add(fv, "minecraft:lantern", 16);
	}

	private static void addEquivalentLegacyItems(FixedValues fv) {
		//Phase 2 (ProcessE tools and materials)
		add(fv, "equivalent_legacy:philosophers_stone", 24576L);
		add(fv, "equivalent_legacy:transmutation_stone", 10000L);
		add(fv, "equivalent_legacy:pe_axe", 9216L);
		add(fv, "equivalent_legacy:pe_pickaxe", 16384L);
		add(fv, "equivalent_legacy:pe_saw", 16384L);
		add(fv, "equivalent_legacy:pedestal", 512L);
		add(fv, "equivalent_legacy:dm_pedestal", 1253376L);
		add(fv, "equivalent_legacy:rm_pedestal", 3760128L);
		add(fv, "equivalent_legacy:destruction_catalyst_block", 4096L);
		add(fv, "equivalent_legacy:alchemical_coal", 1024L);
		add(fv, "equivalent_legacy:mobius_fuel", 3072L);
		add(fv, "equivalent_legacy:aeternalis_fuel", 9216L);
		add(fv, "equivalent_legacy:alchemical_coal_block", 9216L);
		add(fv, "equivalent_legacy:mobius_fuel_block", 27648L);
		add(fv, "equivalent_legacy:aeternalis_fuel_block", 82944L);

		//Phase 3-4 (Matter)
		add(fv, "equivalent_legacy:dark_matter", 139264L);
		add(fv, "equivalent_legacy:red_matter", 417792L);
		add(fv, "equivalent_legacy:dark_matter_block", 1253376L);
		add(fv, "equivalent_legacy:red_matter_block", 3760128L);

		//Phase 5 (Equivalent Legacy specifics)
		add(fv, "equivalent_legacy:low_covalence_dust", 512L);
		add(fv, "equivalent_legacy:medium_covalence_dust", 1024L);
		add(fv, "equivalent_legacy:high_covalence_dust", 2048L);
		add(fv, "equivalent_legacy:tome", 25172992L);
		add(fv, "equivalent_legacy:klein_star_ein", 1000000L);
		add(fv, "equivalent_legacy:klein_star_zwei", 4000000L);
		add(fv, "equivalent_legacy:klein_star_drei", 16000000L);
		add(fv, "equivalent_legacy:klein_star_vier", 64000000L);
		add(fv, "equivalent_legacy:klein_star_sphere", 256000000L);
		add(fv, "equivalent_legacy:klein_star_omega", 1024000000L);
		add(fv, "equivalent_legacy:iron_band", 512L);
	}

	private static void add(FixedValues fv, String itemId, long emc) {
		fv.addSetValueBefore(NSSItem.createItem(Identifier.parse(itemId)), emc);
	}
}
