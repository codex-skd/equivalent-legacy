package com.skd.equivalentlegacy.integration.jei;

import net.minecraft.world.item.ItemStack;

/**
 * A single transmutation recipe shown in JEI: source block, destination block and the EMC cost
 * required to transmute between them.
 */
public record TransmutationRecipeDisplay(ItemStack inputBlock, ItemStack outputBlock, long emcCost) {}
