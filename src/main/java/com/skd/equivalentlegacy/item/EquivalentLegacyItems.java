package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquivalentLegacyItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EquivalentLegacy.MODID);

    public static final DeferredItem<PhilosophersStone> PHILOSOPHERS_STONE = ITEMS.register("philosophers_stone",
            () -> new PhilosophersStone(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<DarkMatter> DARK_MATTER = ITEMS.register("dark_matter",
            () -> new DarkMatter(new Item.Properties()));

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
