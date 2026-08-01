package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public final class MatterMaterials {
    public static final ToolMaterial DARK_MATTER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            6_000,
            8.0F,
            3.0F,
            10,
            EquivalentLegacyTags.DARK_MATTER
    );

    public static final ToolMaterial RED_MATTER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            12_000,
            12.0F,
            6.0F,
            15,
            EquivalentLegacyTags.RED_MATTER
    );

    public static final ResourceKey<net.minecraft.world.item.equipment.EquipmentAsset> DARK_MATTER_ARMOR_ASSET =
            ResourceKey.create(EquipmentAssets.ROOT_ID, EquivalentLegacy.rl("dark_matter"));

    public static final ResourceKey<net.minecraft.world.item.equipment.EquipmentAsset> RED_MATTER_ARMOR_ASSET =
            ResourceKey.create(EquipmentAssets.ROOT_ID, EquivalentLegacy.rl("red_matter"));

    public static final ArmorMaterial DARK_MATTER_ARMOR = new ArmorMaterial(
            6_000,
            Map.of(ArmorType.HELMET, 4, ArmorType.CHESTPLATE, 8, ArmorType.LEGGINGS, 6, ArmorType.BOOTS, 4),
            10,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            2.0F,
            0.0F,
            EquivalentLegacyTags.DARK_MATTER,
            DARK_MATTER_ARMOR_ASSET
    );

    public static final ArmorMaterial RED_MATTER_ARMOR = new ArmorMaterial(
            12_000,
            Map.of(ArmorType.HELMET, 5, ArmorType.CHESTPLATE, 10, ArmorType.LEGGINGS, 7, ArmorType.BOOTS, 5),
            15,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            4.0F,
            0.1F,
            EquivalentLegacyTags.RED_MATTER,
            RED_MATTER_ARMOR_ASSET
    );

    private MatterMaterials() {}
}
