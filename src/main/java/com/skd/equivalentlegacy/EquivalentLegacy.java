package com.skd.equivalentlegacy;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(EquivalentLegacy.MODID)
public class EquivalentLegacy {
    public static final String MODID = "equivalent_legacy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EquivalentLegacy(IEventBus modEventBus, ModContainer modContainer) {
        com.skd.equivalentlegacy.config.EquivalentLegacyConfig.register(modContainer);

        com.skd.equivalentlegacy.player.EquivalentLegacyAttachments.ATTACHMENT_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyItems.ITEMS.register(modEventBus);
        com.skd.equivalentlegacy.block.EquivalentLegacyBlocks.BLOCKS.register(modEventBus);
        com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.EquivalentLegacyEntities.ENTITIES.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        com.skd.equivalentlegacy.item.EquivalentLegacyDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.item.crafting.ModIngredientTypes.INGREDIENT_TYPES.register(modEventBus);
        com.skd.equivalentlegacy.gui.ModMenuTypes.MENU_TYPES.register(modEventBus);

        com.skd.equivalentlegacy.network.PacketHandler.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Equivalent Legacy loading...");
        initEmcValues();
    }

    private static void initEmcValues() {
        var fixedValues = new com.skd.equivalentlegacy.emc.FixedValues();

        addBaseValues(fixedValues);

        addEmcPhase12(fixedValues);

        var nssItem = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.DARK_MATTER.get());
        fixedValues.addSetValueBefore(nssItem, 139_264L);

        var nssRed = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.RED_MATTER.get());
        fixedValues.addSetValueBefore(nssRed, 417_792L);

        var nssAlchemical = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.ALCHEMICAL_COAL.get());
        fixedValues.addSetValueBefore(nssAlchemical, 1_024L);

        var nssMobius = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.MOBIUS_FUEL.get());
        fixedValues.addSetValueBefore(nssMobius, 3_072L);

        var nssAeternalis = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.AETERNALIS_FUEL.get());
        fixedValues.addSetValueBefore(nssAeternalis, 9_216L);

        var nssAlchemicalBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.ALCHEMICAL_COAL_BLOCK.get());
        fixedValues.addSetValueBefore(nssAlchemicalBlock, 9_216L);

        var nssMobiusBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.MOBIUS_FUEL_BLOCK.get());
        fixedValues.addSetValueBefore(nssMobiusBlock, 27_648L);

        var nssAeternalisBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.AETERNALIS_FUEL_BLOCK.get());
        fixedValues.addSetValueBefore(nssAeternalisBlock, 82_944L);

        var nssDarkBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.DARK_MATTER_BLOCK.get());
        fixedValues.addSetValueBefore(nssDarkBlock, 1_253_376L);

        var nssRedBlock = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.RED_MATTER_BLOCK.get());
        fixedValues.addSetValueBefore(nssRedBlock, 3_760_128L);

        var nssLowDust = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.LOW_COVALENCE_DUST.get());
        fixedValues.addSetValueBefore(nssLowDust, 512L);

        var nssMediumDust = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.MEDIUM_COVALENCE_DUST.get());
        fixedValues.addSetValueBefore(nssMediumDust, 1_024L);

        var nssHighDust = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.HIGH_COVALENCE_DUST.get());
        fixedValues.addSetValueBefore(nssHighDust, 2_048L);

        var nssTome = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.TOME.get());
        fixedValues.addSetValueBefore(nssTome, 25_172_992L);

        var nssKlein = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_EIN.get());
        fixedValues.addSetValueBefore(nssKlein, 24_576L);

        var nssKleinZwei = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_ZWEI.get());
        fixedValues.addSetValueBefore(nssKleinZwei, 98_304L);

        var nssKleinDrei = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_DREI.get());
        fixedValues.addSetValueBefore(nssKleinDrei, 393_216L);

        var nssKleinVier = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_VIER.get());
        fixedValues.addSetValueBefore(nssKleinVier, 1_572_864L);

        var nssKleinSphere = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_SPHERE.get());
        fixedValues.addSetValueBefore(nssKleinSphere, 6_291_456L);

        var nssKleinOmega = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(com.skd.equivalentlegacy.item.EquivalentLegacyItems.KLEIN_STAR_OMEGA.get());
        fixedValues.addSetValueBefore(nssKleinOmega, 25_165_824L);

        com.skd.equivalentlegacy.emc.EMCHelper.registerFixedValues(fixedValues);
        LOGGER.info("Registered phase 5 EMC values (Dark Matter, Klein Stars)");
    }

    private static void addEmcPhase12(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.PHILOSOPHERS_STONE.get(), 24_576L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.TRANSMUTATION_STONE.get(), 32_768L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.PE_AXE.get(), 9_216L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.PE_PICKAXE.get(), 16_384L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.PE_SAW.get(), 16_384L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.PEDESTAL.get(), 512L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.DM_PEDESTAL.get(), 1_253_376L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.RM_PEDESTAL.get(), 3_760_128L);
        add(fv, com.skd.equivalentlegacy.item.EquivalentLegacyItems.DESTRUCTION_CATALYST_BLOCK.get(), 4_096L);
    }

    private static void addBaseValues(com.skd.equivalentlegacy.emc.FixedValues fixedValues) {
        int count = 0;

        count += addRawMaterials(fixedValues);
        count += addOres(fixedValues);
        count += addGems(fixedValues);
        count += addBlocks(fixedValues);
        count += addFood(fixedValues);
        count += addDyes(fixedValues);
        count += addTools(fixedValues);
        count += addArmor(fixedValues);
        count += addMobDrops(fixedValues);
        count += addMisc(fixedValues);

        LOGGER.info("Added {} vanilla EMC values", count);
    }

    private static int addRawMaterials(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.COAL, 4);
        add(fv, net.minecraft.world.item.Items.CHARCOAL, 4);
        add(fv, net.minecraft.world.item.Items.COBBLESTONE, 1);
        add(fv, net.minecraft.world.item.Items.STONE, 1);
        add(fv, net.minecraft.world.item.Items.SAND, 1);
        add(fv, net.minecraft.world.item.Items.GRAVEL, 1);
        add(fv, net.minecraft.world.item.Items.DIRT, 1);
        add(fv, net.minecraft.world.item.Items.GRASS_BLOCK, 2);
        add(fv, net.minecraft.world.item.Items.CLAY_BALL, 1);
        add(fv, net.minecraft.world.item.Items.CLAY, 4);
        add(fv, net.minecraft.world.item.Items.FLINT, 1);
        return 11;
    }

    private static int addOres(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.IRON_ORE, 8);
        add(fv, net.minecraft.world.item.Items.IRON_INGOT, 8);
        add(fv, net.minecraft.world.item.Items.GOLD_ORE, 32);
        add(fv, net.minecraft.world.item.Items.GOLD_INGOT, 32);
        add(fv, net.minecraft.world.item.Items.COPPER_ORE, 8);
        add(fv, net.minecraft.world.item.Items.COPPER_INGOT, 8);
        add(fv, net.minecraft.world.item.Items.LAPIS_ORE, 20);
        add(fv, net.minecraft.world.item.Items.LAPIS_LAZULI, 5);
        add(fv, net.minecraft.world.item.Items.DIAMOND_ORE, 2048);
        add(fv, net.minecraft.world.item.Items.DIAMOND, 2048);
        add(fv, net.minecraft.world.item.Items.DEEPSLATE_DIAMOND_ORE, 2048);
        add(fv, net.minecraft.world.item.Items.EMERALD_ORE, 16384);
        add(fv, net.minecraft.world.item.Items.EMERALD, 16384);
        add(fv, net.minecraft.world.item.Items.REDSTONE_ORE, 32);
        add(fv, net.minecraft.world.item.Items.REDSTONE, 8);
        add(fv, net.minecraft.world.item.Items.DEEPSLATE_REDSTONE_ORE, 32);
        return 16;
    }

    private static int addGems(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.DIAMOND, 2048);
        add(fv, net.minecraft.world.item.Items.EMERALD, 16384);
        add(fv, net.minecraft.world.item.Items.NETHER_STAR, 139264);
        add(fv, net.minecraft.world.item.Items.AMETHYST_SHARD, 64);
        add(fv, net.minecraft.world.item.Items.QUARTZ, 16);
        add(fv, net.minecraft.world.item.Items.NETHER_QUARTZ_ORE, 16);
        return 6;
    }

    private static int addBlocks(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.OAK_LOG, 1);
        add(fv, net.minecraft.world.item.Items.OAK_PLANKS, 1);
        add(fv, net.minecraft.world.item.Items.OAK_LEAVES, 1);
        add(fv, net.minecraft.world.item.Items.SPRUCE_LOG, 1);
        add(fv, net.minecraft.world.item.Items.BIRCH_LOG, 1);
        add(fv, net.minecraft.world.item.Items.JUNGLE_LOG, 1);
        add(fv, net.minecraft.world.item.Items.ACACIA_LOG, 1);
        add(fv, net.minecraft.world.item.Items.DARK_OAK_LOG, 1);
        add(fv, net.minecraft.world.item.Items.MANGROVE_LOG, 1);
        add(fv, net.minecraft.world.item.Items.CHERRY_LOG, 1);
        add(fv, net.minecraft.world.item.Items.GLASS, 1);
        add(fv, net.minecraft.world.item.Items.GLOWSTONE_DUST, 4);
        add(fv, net.minecraft.world.item.Items.GLOWSTONE, 16);
        add(fv, net.minecraft.world.item.Items.OBSIDIAN, 64);
        add(fv, net.minecraft.world.item.Items.NETHERRACK, 1);
        add(fv, net.minecraft.world.item.Items.END_STONE, 1);
        add(fv, net.minecraft.world.item.Items.PURPUR_BLOCK, 4);
        add(fv, net.minecraft.world.item.Items.SOUL_SAND, 2);
        add(fv, net.minecraft.world.item.Items.SOUL_SOIL, 2);
        add(fv, net.minecraft.world.item.Items.BLACKSTONE, 1);
        return 20;
    }

    private static int addFood(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.WHEAT, 1);
        add(fv, net.minecraft.world.item.Items.APPLE, 8);
        add(fv, net.minecraft.world.item.Items.GOLDEN_APPLE, 8192);
        add(fv, net.minecraft.world.item.Items.ENCHANTED_GOLDEN_APPLE, 131072);
        add(fv, net.minecraft.world.item.Items.CARROT, 2);
        add(fv, net.minecraft.world.item.Items.GOLDEN_CARROT, 256);
        add(fv, net.minecraft.world.item.Items.POTATO, 1);
        add(fv, net.minecraft.world.item.Items.BAKED_POTATO, 1);
        add(fv, net.minecraft.world.item.Items.POISONOUS_POTATO, 1);
        add(fv, net.minecraft.world.item.Items.PUMPKIN, 4);
        add(fv, net.minecraft.world.item.Items.PUMPKIN_SEEDS, 1);
        add(fv, net.minecraft.world.item.Items.MELON, 2);
        add(fv, net.minecraft.world.item.Items.MELON_SEEDS, 1);
        add(fv, net.minecraft.world.item.Items.SUGAR_CANE, 1);
        add(fv, net.minecraft.world.item.Items.SUGAR, 1);
        add(fv, net.minecraft.world.item.Items.HONEY_BLOCK, 16);
        add(fv, net.minecraft.world.item.Items.COCOA_BEANS, 1);
        add(fv, net.minecraft.world.item.Items.KELP, 1);
        add(fv, net.minecraft.world.item.Items.DRIED_KELP, 1);
        add(fv, net.minecraft.world.item.Items.SEA_PICKLE, 2);
        return 20;
    }

    private static int addDyes(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.BONE_MEAL, 1);
        add(fv, net.minecraft.world.item.Items.INK_SAC, 1);
        add(fv, net.minecraft.world.item.Items.COCOA_BEANS, 1);
        add(fv, net.minecraft.world.item.Items.LAPIS_LAZULI, 5);
        add(fv, net.minecraft.world.item.Items.CACTUS, 1);
        add(fv, net.minecraft.world.item.Items.PUMPKIN, 4);
        add(fv, net.minecraft.world.item.Items.DANDELION, 1);
        add(fv, net.minecraft.world.item.Items.POPPY, 1);
        add(fv, net.minecraft.world.item.Items.BLUE_ORCHID, 1);
        add(fv, net.minecraft.world.item.Items.ALLIUM, 1);
        add(fv, net.minecraft.world.item.Items.AZURE_BLUET, 1);
        add(fv, net.minecraft.world.item.Items.RED_TULIP, 1);
        add(fv, net.minecraft.world.item.Items.ORANGE_TULIP, 1);
        add(fv, net.minecraft.world.item.Items.WHITE_TULIP, 1);
        add(fv, net.minecraft.world.item.Items.PINK_TULIP, 1);
        add(fv, net.minecraft.world.item.Items.OXEYE_DAISY, 1);
        add(fv, net.minecraft.world.item.Items.CORNFLOWER, 1);
        add(fv, net.minecraft.world.item.Items.LILY_OF_THE_VALLEY, 1);
        return 18;
    }

    private static int addTools(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.WOODEN_PICKAXE, 3);
        add(fv, net.minecraft.world.item.Items.WOODEN_AXE, 3);
        add(fv, net.minecraft.world.item.Items.WOODEN_SHOVEL, 2);
        add(fv, net.minecraft.world.item.Items.WOODEN_HOE, 2);
        add(fv, net.minecraft.world.item.Items.STONE_PICKAXE, 4);
        add(fv, net.minecraft.world.item.Items.STONE_AXE, 4);
        add(fv, net.minecraft.world.item.Items.STONE_SHOVEL, 3);
        add(fv, net.minecraft.world.item.Items.STONE_HOE, 3);
        add(fv, net.minecraft.world.item.Items.IRON_PICKAXE, 24);
        add(fv, net.minecraft.world.item.Items.IRON_AXE, 24);
        add(fv, net.minecraft.world.item.Items.IRON_SHOVEL, 16);
        add(fv, net.minecraft.world.item.Items.IRON_HOE, 16);
        add(fv, net.minecraft.world.item.Items.DIAMOND_PICKAXE, 2048);
        add(fv, net.minecraft.world.item.Items.DIAMOND_AXE, 2048);
        add(fv, net.minecraft.world.item.Items.DIAMOND_SHOVEL, 1368);
        add(fv, net.minecraft.world.item.Items.DIAMOND_HOE, 1368);
        add(fv, net.minecraft.world.item.Items.NETHERITE_PICKAXE, 8192);
        add(fv, net.minecraft.world.item.Items.NETHERITE_AXE, 8192);
        add(fv, net.minecraft.world.item.Items.NETHERITE_SHOVEL, 5456);
        add(fv, net.minecraft.world.item.Items.NETHERITE_HOE, 5456);
        return 20;
    }

    private static int addArmor(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.LEATHER_HELMET, 5);
        add(fv, net.minecraft.world.item.Items.LEATHER_CHESTPLATE, 8);
        add(fv, net.minecraft.world.item.Items.LEATHER_LEGGINGS, 7);
        add(fv, net.minecraft.world.item.Items.LEATHER_BOOTS, 4);
        add(fv, net.minecraft.world.item.Items.IRON_HELMET, 24);
        add(fv, net.minecraft.world.item.Items.IRON_CHESTPLATE, 40);
        add(fv, net.minecraft.world.item.Items.IRON_LEGGINGS, 35);
        add(fv, net.minecraft.world.item.Items.IRON_BOOTS, 16);
        add(fv, net.minecraft.world.item.Items.DIAMOND_HELMET, 2048);
        add(fv, net.minecraft.world.item.Items.DIAMOND_CHESTPLATE, 3456);
        add(fv, net.minecraft.world.item.Items.DIAMOND_LEGGINGS, 3024);
        add(fv, net.minecraft.world.item.Items.DIAMOND_BOOTS, 1368);
        add(fv, net.minecraft.world.item.Items.NETHERITE_HELMET, 8192);
        add(fv, net.minecraft.world.item.Items.NETHERITE_CHESTPLATE, 13824);
        add(fv, net.minecraft.world.item.Items.NETHERITE_LEGGINGS, 12096);
        add(fv, net.minecraft.world.item.Items.NETHERITE_BOOTS, 5456);
        return 16;
    }

    private static int addMobDrops(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.ROTTEN_FLESH, 1);
        add(fv, net.minecraft.world.item.Items.BONE, 2);
        add(fv, net.minecraft.world.item.Items.GUNPOWDER, 8);
        add(fv, net.minecraft.world.item.Items.STRING, 1);
        add(fv, net.minecraft.world.item.Items.SPIDER_EYE, 8);
        add(fv, net.minecraft.world.item.Items.FERMENTED_SPIDER_EYE, 10);
        add(fv, net.minecraft.world.item.Items.SLIME_BALL, 2);
        add(fv, net.minecraft.world.item.Items.ENDER_PEARL, 16);
        add(fv, net.minecraft.world.item.Items.ENDER_EYE, 24);
        add(fv, net.minecraft.world.item.Items.BLAZE_ROD, 16);
        add(fv, net.minecraft.world.item.Items.BLAZE_POWDER, 4);
        add(fv, net.minecraft.world.item.Items.MAGMA_CREAM, 10);
        add(fv, net.minecraft.world.item.Items.GHAST_TEAR, 32);
        add(fv, net.minecraft.world.item.Items.SHULKER_SHELL, 128);
        add(fv, net.minecraft.world.item.Items.PHANTOM_MEMBRANE, 16);
        add(fv, net.minecraft.world.item.Items.DRAGON_BREATH, 512);
        return 16;
    }

    private static int addMisc(com.skd.equivalentlegacy.emc.FixedValues fv) {
        add(fv, net.minecraft.world.item.Items.PAPER, 1);
        add(fv, net.minecraft.world.item.Items.BOOK, 4);
        add(fv, net.minecraft.world.item.Items.ENCHANTED_BOOK, 256);
        add(fv, net.minecraft.world.item.Items.STICK, 1);
        add(fv, net.minecraft.world.item.Items.LEATHER, 4);
        add(fv, net.minecraft.world.item.Items.SADDLE, 32);
        add(fv, net.minecraft.world.item.Items.NAME_TAG, 32);
        add(fv, net.minecraft.world.item.Items.ENDER_CHEST, 512);
        add(fv, net.minecraft.world.item.Items.ANVIL, 456);
        add(fv, net.minecraft.world.item.Items.BUCKET, 24);
        add(fv, net.minecraft.world.item.Items.WATER_BUCKET, 24);
        add(fv, net.minecraft.world.item.Items.LAVA_BUCKET, 48);
        add(fv, net.minecraft.world.item.Items.MILK_BUCKET, 24);
        add(fv, net.minecraft.world.item.Items.POWDER_SNOW_BUCKET, 24);
        add(fv, net.minecraft.world.item.Items.PISTON, 32);
        add(fv, net.minecraft.world.item.Items.STICKY_PISTON, 40);
        add(fv, net.minecraft.world.item.Items.REDSTONE_LAMP, 32);
        add(fv, net.minecraft.world.item.Items.REDSTONE_TORCH, 8);
        add(fv, net.minecraft.world.item.Items.DETECTOR_RAIL, 24);
        add(fv, net.minecraft.world.item.Items.POWERED_RAIL, 32);
        return 20;
    }

    private static void add(com.skd.equivalentlegacy.emc.FixedValues fv, net.minecraft.world.item.Item item, long emc) {
        var nss = com.skd.equivalentlegacy.emc.nss.NSSItem.createItem(item);
        fv.addSetValueBefore(nss, emc);
    }

    public static Identifier rl(String path) {
        return Identifier.parse(MODID + ":" + path);
    }
}
