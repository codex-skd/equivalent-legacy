package com.skd.equivalentlegacy.integration.recipe_viewer.alias;

import java.util.ArrayList;
import java.util.List;
import com.skd.equivalentlegacy.gameObjs.PETags;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlocks;
import com.skd.equivalentlegacy.gameObjs.registries.PEItems;
import net.minecraft.world.item.Items;

public final class EquivalentLegacyAliasMapping implements IAliasMapping {

	@Override
	public <ITEM> void addAliases(RVAliasHelper<ITEM> rv) {
		addBlockAliases(rv);
		addGearAliases(rv);
		addMiscAliases(rv);
	}

	private <ITEM> void addBlockAliases(RVAliasHelper<ITEM> rv) {
		rv.addAliases(PEBlocks.ALCHEMICAL_CHEST, EquivalentLegacyAliases.ITEM_STORAGE);
		rv.addAliases(PETags.Items.COLLECTORS, EquivalentLegacyAliases.EMC_GENERATOR);
		rv.addAliases(PEBlocks.DARK_MATTER_PEDESTAL, EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG);
		rv.addAliases(PETags.Items.RELAYS, EquivalentLegacyAliases.EMC_CHARGER, EquivalentLegacyAliases.EMC_TRANSFER);

		rv.addAliases(PEBlocks.ALCHEMICAL_COAL, EquivalentLegacyAliases.BLOCK_ALCHEMICAL_COAL);
		rv.addAliases(PEBlocks.MOBIUS_FUEL, EquivalentLegacyAliases.BLOCK_MOBIUS_FUEL);
		rv.addAliases(PEBlocks.AETERNALIS_FUEL, EquivalentLegacyAliases.BLOCK_AETERNALIS_FUEL);
		rv.addAliases(PEBlocks.DARK_MATTER, EquivalentLegacyAliases.BLOCK_DARK_MATTER);
		rv.addAliases(PEBlocks.RED_MATTER, EquivalentLegacyAliases.BLOCK_RED_MATTER);
	}

	private <ITEM> void addGearAliases(RVAliasHelper<ITEM> rv) {
		addArmorAliases(rv);
		addToolAliases(rv);
		rv.addAliases(PETags.Items.ALCHEMICAL_BAGS, EquivalentLegacyAliases.BACKPACK, EquivalentLegacyAliases.ITEM_STORAGE);
		rv.addAliases(List.of(
				PEItems.LOW_DIVINING_ROD,
				PEItems.MEDIUM_DIVINING_ROD,
				PEItems.HIGH_DIVINING_ROD
		), EquivalentLegacyAliases.EMC_DETECTOR);
		rv.addAliases(PEItems.MERCURIAL_EYE, EquivalentLegacyAliases.BUILDING_WAND);
		rv.addAliases(PEItems.MIND_STONE, EquivalentLegacyAliases.XP_STORAGE);
		rv.addAliases(PEItems.PHILOSOPHERS_STONE, EquivalentLegacyAliases.PORTABLE_CRAFTING_TABLE, EquivalentLegacyAliases.PORTABLE_WORKBENCH, EquivalentLegacyAliases.WORD_TRANSMUTATION);
		rv.addAliases(PEItems.TRANSMUTATION_TABLET, EquivalentLegacyAliases.PORTABLE_TRANSMUTATION);
		rv.addAliases(PEItems.BODY_STONE, EquivalentLegacyAliases.AUTO_HEALER);
		rv.addAliases(PEItems.SOUL_STONE, EquivalentLegacyAliases.AUTO_FEEDER);
		rv.addAliases(PEItems.LIFE_STONE, EquivalentLegacyAliases.AUTO_FEEDER, EquivalentLegacyAliases.AUTO_HEALER);
		rv.addAliases(PEItems.WATCH_OF_FLOWING_TIME, EquivalentLegacyAliases.TICK_ACCELERATOR, EquivalentLegacyAliases.TIME_CONTROL, EquivalentLegacyAliases.SLOW_HOSTILE, EquivalentLegacyAliases.SLOW_MOBS);

		rv.addAliases(PEItems.EVERTIDE_AMULET, EquivalentLegacyAliases.INFINITE_WATER, EquivalentLegacyAliases.WATER_WALKING, EquivalentLegacyAliases.WEATHER_CONTROL, EquivalentLegacyAliases.TOOL_RANGED);
		rv.addAliases(PEItems.VOLCANITE_AMULET, EquivalentLegacyAliases.INFINITE_LAVA, EquivalentLegacyAliases.LAVA_WALKING, EquivalentLegacyAliases.WEATHER_CONTROL, EquivalentLegacyAliases.TOOL_RANGED,
				EquivalentLegacyAliases.FIRE_PROTECTION);

		rv.addAliases(PEItems.ARCHANGEL_SMITE, EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG, EquivalentLegacyAliases.TOOL_WEAPON, EquivalentLegacyAliases.TOOL_RANGED,
				Items.ARROW::getDescriptionId);

		rv.addAliases(PEItems.BLACK_HOLE_BAND, EquivalentLegacyAliases.VOID_FLUID, EquivalentLegacyAliases.FLUID_REMOVER);
		rv.addAliases(PEItems.VOID_RING, EquivalentLegacyAliases.TELEPORATION, EquivalentLegacyAliases.SELF_TELEPORTER, Items.ENDER_PEARL::getDescriptionId);
		rv.addAliases(List.of(
				PEItems.BLACK_HOLE_BAND,
				PEItems.VOID_RING
		), EquivalentLegacyAliases.MAGNET, EquivalentLegacyAliases.TOOL_RANGED);
		rv.addAliases(List.of(
				PEBlocks.CONDENSER,
				PEBlocks.CONDENSER_MK2,
				PEItems.GEM_OF_ETERNAL_DENSITY,
				PEItems.VOID_RING
		), EquivalentLegacyAliases.CONDENSER_ITEMS, EquivalentLegacyAliases.CONDENSER_MATTER);

		rv.addAliases(List.of(
				PEItems.DESTRUCTION_CATALYST,
				PEItems.CATALYTIC_LENS
		), EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG);
		rv.addAliases(List.of(
				PEItems.HYPERKINETIC_LENS,
				PEItems.CATALYTIC_LENS
		), EquivalentLegacyAliases.EXPLOSIVE, EquivalentLegacyAliases.TOOL_RANGED);

		rv.addAliases(List.of(
				PEItems.ARCANA_RING,
				PEItems.ZERO_RING
		), EquivalentLegacyAliases.FREEZE, EquivalentLegacyAliases.TOOL_RANGED);
		rv.addAliases(List.of(
				PEItems.IGNITION_RING,
				PEItems.ZERO_RING
		), EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG, EquivalentLegacyAliases.FIRE_EXTINGUISHER);
		rv.addAliases(List.of(
				PEItems.ARCANA_RING,
				PEItems.IGNITION_RING
		), Items.FLINT_AND_STEEL::getDescriptionId, EquivalentLegacyAliases.FIRE_STARTER, EquivalentLegacyAliases.FIRE_PROTECTION, EquivalentLegacyAliases.TOOL_RANGED);
		rv.addAliases(List.of(
				PEItems.ARCANA_RING,
				PEItems.HARVEST_GODDESS_BAND
		), EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG, EquivalentLegacyAliases.PLANT_ACCELERATOR, EquivalentLegacyAliases.PLANT_GROWER);
		rv.addAliases(List.of(
				PEItems.ARCANA_RING,
				PEItems.SWIFTWOLF_RENDING_GALE
		), EquivalentLegacyAliases.REPEL_HOSTILE, EquivalentLegacyAliases.REPEL_MOB, EquivalentLegacyAliases.REPEL_PROJECTILE, EquivalentLegacyAliases.CREATIVE_FLIGHT, EquivalentLegacyAliases.LIGHTNING,
				EquivalentLegacyAliases.TOOL_RANGED);

		List<ITEM> repairItems = new ArrayList<>(rv.tagContents(PETags.Items.COVALENCE_DUST));
		repairItems.add(rv.ingredient(PEItems.REPAIR_TALISMAN));
		rv.addAliases(repairItems, EquivalentLegacyAliases.ITEM_REPAIR);
	}

	private <ITEM> void addArmorAliases(RVAliasHelper<ITEM> rv) {
		rv.addAliases(PEItems.GEM_BOOTS, EquivalentLegacyAliases.AUTO_STEP, EquivalentLegacyAliases.STEP_ASSIST, EquivalentLegacyAliases.MOVEMENT_SPEED);
		rv.addAliases(List.of(
				PEItems.GEM_LEGGINGS,
				PEBlocks.INTERDICTION_TORCH
		), EquivalentLegacyAliases.REPEL_HOSTILE, EquivalentLegacyAliases.REPEL_MOB, EquivalentLegacyAliases.REPEL_PROJECTILE);
		rv.addAliases(PEItems.GEM_CHESTPLATE, EquivalentLegacyAliases.EXPLOSIVE, EquivalentLegacyAliases.AUTO_FEEDER, EquivalentLegacyAliases.FIRE_PROTECTION);
		rv.addAliases(PEItems.GEM_HELMET, EquivalentLegacyAliases.NIGHT_VISION, EquivalentLegacyAliases.AUTO_HEALER, EquivalentLegacyAliases.LIGHTNING, EquivalentLegacyAliases.TOOL_RANGED);
	}

	private <ITEM> void addToolAliases(RVAliasHelper<ITEM> rv) {
		rv.addAliases(PEItems.RED_MATTER_KATAR, EquivalentLegacyAliases.TOOL_AXE, EquivalentLegacyAliases.TOOL_HOE, EquivalentLegacyAliases.TOOL_SHEARS, EquivalentLegacyAliases.TOOL_SWORD,
				EquivalentLegacyAliases.TOOL_WEAPON, EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG, EquivalentLegacyAliases.TOOL_RANGED);
		rv.addAliases(PEItems.RED_MATTER_MORNING_STAR, EquivalentLegacyAliases.TOOL_HAMMER, EquivalentLegacyAliases.TOOL_SHOVEL, EquivalentLegacyAliases.TOOL_PICKAXE,
				EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG);
		rv.addAliases(List.of(
				PEItems.DARK_MATTER_SWORD,
				PEItems.RED_MATTER_SWORD
		), EquivalentLegacyAliases.TOOL_WEAPON);
		rv.addAliases(List.of(
				PEItems.DARK_MATTER_HAMMER,
				PEItems.RED_MATTER_HAMMER
		), EquivalentLegacyAliases.AOE, EquivalentLegacyAliases.AOE_LONG, EquivalentLegacyAliases.TOOL_PICKAXE);
	}

	private <ITEM> void addMiscAliases(RVAliasHelper<ITEM> rv) {
		rv.addAliases(PEItems.IRON_BAND, EquivalentLegacyAliases.RING_BASE);
		rv.addAliases(PETags.Items.KLEIN_STARS, EquivalentLegacyAliases.EMC_STORAGE, EquivalentLegacyAliases.EMC_BATTERY);
		rv.addAliases(List.of(
				PEBlocks.NOVA_CATALYST,
				PEBlocks.NOVA_CATACLYSM
		), Items.TNT::getDescriptionId, EquivalentLegacyAliases.EXPLOSIVE);
	}
}