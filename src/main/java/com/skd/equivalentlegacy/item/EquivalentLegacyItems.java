package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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

    private EquivalentLegacyItems() {}
}
