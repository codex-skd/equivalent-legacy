package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquivalentLegacyItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EquivalentLegacy.MODID);

    public static final DeferredItem<PhilosophersStone> PHILOSOPHERS_STONE = ITEMS.register("philosophers_stone",
            () -> new PhilosophersStone(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<DarkMatter> DARK_MATTER = ITEMS.register("dark_matter",
            () -> new DarkMatter(new Item.Properties()));

    public static final DeferredItem<AlchemicalCoal> ALCHEMICAL_COAL = ITEMS.register("alchemical_coal",
            () -> new AlchemicalCoal(new Item.Properties()));

    public static final DeferredItem<MobiusFuel> MOBIUS_FUEL = ITEMS.register("mobius_fuel",
            () -> new MobiusFuel(new Item.Properties()));

    public static final DeferredItem<AeternalisFuel> AETERNALIS_FUEL = ITEMS.register("aeternalis_fuel",
            () -> new AeternalisFuel(new Item.Properties()));

    public static final DeferredItem<RedMatter> RED_MATTER = ITEMS.register("red_matter",
            () -> new RedMatter(new Item.Properties()));

    public static final DeferredItem<BlockItem> ALCHEMICAL_COAL_BLOCK = ITEMS.registerSimpleBlockItem("alchemical_coal_block", EquivalentLegacyBlocks.ALCHEMICAL_COAL_BLOCK);

    public static final DeferredItem<BlockItem> MOBIUS_FUEL_BLOCK = ITEMS.registerSimpleBlockItem("mobius_fuel_block", EquivalentLegacyBlocks.MOBIUS_FUEL_BLOCK);

    public static final DeferredItem<BlockItem> AETERNALIS_FUEL_BLOCK = ITEMS.registerSimpleBlockItem("aeternalis_fuel_block", EquivalentLegacyBlocks.AETERNALIS_FUEL_BLOCK);

    public static final DeferredItem<BlockItem> DARK_MATTER_BLOCK = ITEMS.registerSimpleBlockItem("dark_matter_block", EquivalentLegacyBlocks.DARK_MATTER_BLOCK);

    public static final DeferredItem<BlockItem> RED_MATTER_BLOCK = ITEMS.registerSimpleBlockItem("red_matter_block", EquivalentLegacyBlocks.RED_MATTER_BLOCK);

    public static final DeferredItem<BlockItem> COLLECTOR_MK1 = ITEMS.registerSimpleBlockItem("collector_mk1", EquivalentLegacyBlocks.COLLECTOR_MK1);

    public static final DeferredItem<BlockItem> COLLECTOR_MK2 = ITEMS.registerSimpleBlockItem("collector_mk2", EquivalentLegacyBlocks.COLLECTOR_MK2);

    public static final DeferredItem<BlockItem> COLLECTOR_MK3 = ITEMS.registerSimpleBlockItem("collector_mk3", EquivalentLegacyBlocks.COLLECTOR_MK3);

    public static final DeferredItem<BlockItem> RELAY_MK1 = ITEMS.registerSimpleBlockItem("relay_mk1", EquivalentLegacyBlocks.RELAY_MK1);

    public static final DeferredItem<BlockItem> RELAY_MK2 = ITEMS.registerSimpleBlockItem("relay_mk2", EquivalentLegacyBlocks.RELAY_MK2);

    public static final DeferredItem<BlockItem> RELAY_MK3 = ITEMS.registerSimpleBlockItem("relay_mk3", EquivalentLegacyBlocks.RELAY_MK3);

    public static final DeferredItem<BlockItem> CONDENSER_MK1 = ITEMS.registerSimpleBlockItem("condenser_mk1", EquivalentLegacyBlocks.CONDENSER_MK1);

    public static final DeferredItem<BlockItem> CONDENSER_MK2 = ITEMS.registerSimpleBlockItem("condenser_mk2", EquivalentLegacyBlocks.CONDENSER_MK2);

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_BLACK = registerBag("black_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_BLUE = registerBag("blue_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_BROWN = registerBag("brown_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_CYAN = registerBag("cyan_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_GRAY = registerBag("gray_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_GREEN = registerBag("green_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_LIGHT_BLUE = registerBag("light_blue_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_LIGHT_GRAY = registerBag("light_gray_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_LIME = registerBag("lime_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_MAGENTA = registerBag("magenta_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_ORANGE = registerBag("orange_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_PINK = registerBag("pink_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_PURPLE = registerBag("purple_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_RED = registerBag("red_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_WHITE = registerBag("white_alchemical_bag");

    public static final DeferredItem<AlchemicalBag> ALCHEMICAL_BAG_YELLOW = registerBag("yellow_alchemical_bag");

    private static DeferredItem<AlchemicalBag> registerBag(String name) {
        return ITEMS.register(name, () -> new AlchemicalBag(new Item.Properties().stacksTo(1)
                .component(net.minecraft.core.component.DataComponents.CONTAINER, net.minecraft.world.item.component.ItemContainerContents.EMPTY)));
    }

    public static final DeferredItem<BlockItem> ALCHEMICAL_CHEST = ITEMS.registerSimpleBlockItem("alchemical_chest", EquivalentLegacyBlocks.ALCHEMICAL_CHEST);

    public static final DeferredItem<CovalenceDust> LOW_COVALENCE_DUST = ITEMS.register("low_covalence_dust",
            () -> new CovalenceDust(new Item.Properties()));

    public static final DeferredItem<CovalenceDust> MEDIUM_COVALENCE_DUST = ITEMS.register("medium_covalence_dust",
            () -> new CovalenceDust(new Item.Properties()));

    public static final DeferredItem<CovalenceDust> HIGH_COVALENCE_DUST = ITEMS.register("high_covalence_dust",
            () -> new CovalenceDust(new Item.Properties()));

    public static final DeferredItem<Tome> TOME = ITEMS.register("tome",
            () -> new Tome(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> IRON_BAND = ITEMS.register("iron_band",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<CurioItem> BLACK_HOLE_BAND = registerCurio("black_hole_band", CurioItem.Type.BLACK_HOLE_BAND);

    public static final DeferredItem<CurioItem> BODY_STONE = registerCurio("body_stone", CurioItem.Type.BODY_STONE);

    public static final DeferredItem<CurioItem> EVERTIDE_AMULET = registerCurio("evertide_amulet", CurioItem.Type.EVERTIDE_AMULET);

    public static final DeferredItem<CurioItem> GEM_OF_ETERNAL_DENSITY = registerCurio("gem_of_eternal_density", CurioItem.Type.GEM_OF_ETERNAL_DENSITY);

    public static final DeferredItem<CurioItem> HARVEST_GODDESS_BAND = registerCurio("harvest_goddess_band", CurioItem.Type.HARVEST_GODDESS_BAND);

    public static final DeferredItem<CurioItem> IGNITION_RING = registerCurio("ignition_ring", CurioItem.Type.IGNITION_RING);

    public static final DeferredItem<CurioItem> LIFE_STONE = registerCurio("life_stone", CurioItem.Type.LIFE_STONE);

    public static final DeferredItem<CurioItem> MIND_STONE = registerCurio("mind_stone", CurioItem.Type.MIND_STONE);

    public static final DeferredItem<CurioItem> REPAIR_TALISMAN = registerCurio("repair_talisman", CurioItem.Type.REPAIR_TALISMAN);

    public static final DeferredItem<CurioItem> SOUL_STONE = registerCurio("soul_stone", CurioItem.Type.SOUL_STONE);

    public static final DeferredItem<CurioItem> SWIFTWOLF_RENDING_GALE = registerCurio("swiftwolf_rending_gale", CurioItem.Type.SWIFTWOLF_RENDING_GALE);

    public static final DeferredItem<CurioItem> VOID_RING = registerCurio("void_ring", CurioItem.Type.VOID_RING);

    public static final DeferredItem<CurioItem> VOLCANITE_AMULET = registerCurio("volcanite_amulet", CurioItem.Type.VOLCANITE_AMULET);

    public static final DeferredItem<CurioItem> WATCH_OF_FLOWING_TIME = registerCurio("watch_of_flowing_time", CurioItem.Type.WATCH_OF_FLOWING_TIME);

    public static final DeferredItem<CurioItem> ZERO_RING = registerCurio("zero_ring", CurioItem.Type.ZERO_RING);

    public static final DeferredItem<Item> ARCANA_RING = ITEMS.register("arcana_ring",
            () -> new Item(new Item.Properties().stacksTo(1)));

    private static DeferredItem<CurioItem> registerCurio(String name, CurioItem.Type type) {
        return ITEMS.register(name, () -> new CurioItem(new Item.Properties().stacksTo(1), type));
    }

    public static final DeferredItem<KleinStar> KLEIN_STAR_EIN = ITEMS.register("klein_star_ein",
            () -> new KleinStar(new Item.Properties(), KleinStarTier.EIN));

    public static final DeferredItem<KleinStar> KLEIN_STAR_ZWEI = ITEMS.register("klein_star_zwei",
            () -> new KleinStar(new Item.Properties(), KleinStarTier.ZWEI));

    public static final DeferredItem<KleinStar> KLEIN_STAR_DREI = ITEMS.register("klein_star_drei",
            () -> new KleinStar(new Item.Properties(), KleinStarTier.DREI));

    public static final DeferredItem<KleinStar> KLEIN_STAR_VIER = ITEMS.register("klein_star_vier",
            () -> new KleinStar(new Item.Properties(), KleinStarTier.VIER));

    public static final DeferredItem<KleinStar> KLEIN_STAR_SPHERE = ITEMS.register("klein_star_sphere",
            () -> new KleinStar(new Item.Properties(), KleinStarTier.SPHERE));

    public static final DeferredItem<KleinStar> KLEIN_STAR_OMEGA = ITEMS.register("klein_star_omega",
            () -> new KleinStar(new Item.Properties(), KleinStarTier.OMEGA));

    public static final DeferredItem<Item> DARK_MATTER_SWORD = ITEMS.register("dark_matter_sword",
            () -> new Item(new Item.Properties().sword(MatterMaterials.DARK_MATTER, 8.0F, -2.4F)));

    public static final DeferredItem<Item> DARK_MATTER_PICKAXE = ITEMS.register("dark_matter_pickaxe",
            () -> new Item(new Item.Properties().pickaxe(MatterMaterials.DARK_MATTER, 1.0F, -2.8F)));

    public static final DeferredItem<AxeItem> DARK_MATTER_AXE = ITEMS.register("dark_matter_axe",
            () -> new AxeItem(MatterMaterials.DARK_MATTER, 6.0F, -3.0F, new Item.Properties()));

    public static final DeferredItem<ShovelItem> DARK_MATTER_SHOVEL = ITEMS.register("dark_matter_shovel",
            () -> new ShovelItem(MatterMaterials.DARK_MATTER, 1.5F, -3.0F, new Item.Properties()));

    public static final DeferredItem<HoeItem> DARK_MATTER_HOE = ITEMS.register("dark_matter_hoe",
            () -> new HoeItem(MatterMaterials.DARK_MATTER, 1.0F, -3.0F, new Item.Properties()));

    public static final DeferredItem<ShearsItem> DARK_MATTER_SHEARS = ITEMS.register("dark_matter_shears",
            () -> new ShearsItem(new Item.Properties().durability(6_000)
                    .component(DataComponents.TOOL, ShearsItem.createToolProperties())));

    public static final DeferredItem<HammerItem> DARK_MATTER_HAMMER = ITEMS.register("dark_matter_hammer",
            () -> new HammerItem(new Item.Properties().tool(MatterMaterials.DARK_MATTER, BlockTags.MINEABLE_WITH_PICKAXE, 5.0F, -3.0F, 0.0F)));

    public static final DeferredItem<Item> DARK_MATTER_HELMET = ITEMS.register("dark_matter_helmet",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.DARK_MATTER_ARMOR, ArmorType.HELMET)));

    public static final DeferredItem<Item> DARK_MATTER_CHESTPLATE = ITEMS.register("dark_matter_chestplate",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.DARK_MATTER_ARMOR, ArmorType.CHESTPLATE)));

    public static final DeferredItem<Item> DARK_MATTER_LEGGINGS = ITEMS.register("dark_matter_leggings",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.DARK_MATTER_ARMOR, ArmorType.LEGGINGS)));

    public static final DeferredItem<Item> DARK_MATTER_BOOTS = ITEMS.register("dark_matter_boots",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.DARK_MATTER_ARMOR, ArmorType.BOOTS)));

    public static final DeferredItem<Item> RED_MATTER_SWORD = ITEMS.register("red_matter_sword",
            () -> new Item(new Item.Properties().sword(MatterMaterials.RED_MATTER, 12.0F, -2.4F)));

    public static final DeferredItem<Item> RED_MATTER_PICKAXE = ITEMS.register("red_matter_pickaxe",
            () -> new Item(new Item.Properties().pickaxe(MatterMaterials.RED_MATTER, 1.0F, -2.8F)));

    public static final DeferredItem<AxeItem> RED_MATTER_AXE = ITEMS.register("red_matter_axe",
            () -> new AxeItem(MatterMaterials.RED_MATTER, 9.0F, -3.0F, new Item.Properties()));

    public static final DeferredItem<ShovelItem> RED_MATTER_SHOVEL = ITEMS.register("red_matter_shovel",
            () -> new ShovelItem(MatterMaterials.RED_MATTER, 1.5F, -3.0F, new Item.Properties()));

    public static final DeferredItem<HoeItem> RED_MATTER_HOE = ITEMS.register("red_matter_hoe",
            () -> new HoeItem(MatterMaterials.RED_MATTER, 1.0F, -3.0F, new Item.Properties()));

    public static final DeferredItem<ShearsItem> RED_MATTER_SHEARS = ITEMS.register("red_matter_shears",
            () -> new ShearsItem(new Item.Properties().durability(12_000)
                    .component(DataComponents.TOOL, ShearsItem.createToolProperties())));

    public static final DeferredItem<HammerItem> RED_MATTER_HAMMER = ITEMS.register("red_matter_hammer",
            () -> new HammerItem(new Item.Properties().tool(MatterMaterials.RED_MATTER, BlockTags.MINEABLE_WITH_PICKAXE, 8.0F, -3.0F, 0.0F)));

    public static final DeferredItem<Item> RED_MATTER_KATAR = ITEMS.register("red_matter_katar",
            () -> new Item(new Item.Properties().sword(MatterMaterials.RED_MATTER, 14.0F, -2.0F)));

    public static final DeferredItem<Item> RED_MATTER_MORNING_STAR = ITEMS.register("red_matter_morning_star",
            () -> new Item(new Item.Properties().sword(MatterMaterials.RED_MATTER, 18.0F, -2.4F)));

    public static final DeferredItem<Item> RED_MATTER_HELMET = ITEMS.register("red_matter_helmet",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.RED_MATTER_ARMOR, ArmorType.HELMET)));

    public static final DeferredItem<Item> RED_MATTER_CHESTPLATE = ITEMS.register("red_matter_chestplate",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.RED_MATTER_ARMOR, ArmorType.CHESTPLATE)));

    public static final DeferredItem<Item> RED_MATTER_LEGGINGS = ITEMS.register("red_matter_leggings",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.RED_MATTER_ARMOR, ArmorType.LEGGINGS)));

    public static final DeferredItem<Item> RED_MATTER_BOOTS = ITEMS.register("red_matter_boots",
            () -> new Item(new Item.Properties().humanoidArmor(MatterMaterials.RED_MATTER_ARMOR, ArmorType.BOOTS)));

    private EquivalentLegacyItems() {}
}
