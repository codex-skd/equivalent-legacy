package com.skd.equivalentlegacy.emc.mappers.recipe.special;

import com.skd.equivalentlegacy.api.mapper.collector.IMappingCollector;
import com.skd.equivalentlegacy.api.mapper.recipe.INSSFakeGroupManager;
import com.skd.equivalentlegacy.api.mapper.recipe.IRecipeTypeMapper;
import com.skd.equivalentlegacy.api.mapper.recipe.RecipeTypeMapper;
import com.skd.equivalentlegacy.api.nss.NormalizedSimpleStack;
import com.skd.equivalentlegacy.config.MappingConfig;
import com.skd.equivalentlegacy.config.PEConfigTranslations;
import com.skd.equivalentlegacy.emc.components.processor.DamageProcessor;
import com.skd.equivalentlegacy.emc.components.processor.DecoratedPotProcessor;
import com.skd.equivalentlegacy.emc.components.processor.DecoratedShieldProcessor;
import com.skd.equivalentlegacy.emc.components.processor.FireworkProcessor;
import com.skd.equivalentlegacy.emc.components.processor.FireworkStarProcessor;
import com.skd.equivalentlegacy.emc.components.processor.MapScaleProcessor;
import com.skd.equivalentlegacy.gameObjs.customRecipes.PhiloStoneSmeltingRecipe;
import com.skd.equivalentlegacy.gameObjs.customRecipes.RecipesCovalenceRepair;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.BannerDuplicateRecipe;
import net.minecraft.world.item.crafting.BookCloningRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.DecoratedPotRecipe;
import net.minecraft.world.item.crafting.FireworkRocketRecipe;
import net.minecraft.world.item.crafting.FireworkStarFadeRecipe;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.MapExtendingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;

@RecipeTypeMapper
public class SpecialRecipeMarkHandledMapper implements IRecipeTypeMapper {

	@Override
	public final boolean handleRecipe(IMappingCollector<NormalizedSimpleStack, Long> mapper, RecipeHolder<?> recipeHolder, RegistryAccess registryAccess,
			INSSFakeGroupManager fakeGroupManager) {
		Recipe<?> recipe = recipeHolder.value();
		if (recipe instanceof CustomRecipe) {
			if (recipe instanceof ShieldDecorationRecipe) {
				return MappingConfig.isEnabled(DecoratedShieldProcessor.INSTANCE);
			} else if (recipe instanceof DecoratedPotRecipe) {
				return MappingConfig.isEnabled(DecoratedPotProcessor.INSTANCE);
			} else if (recipe instanceof RepairItemRecipe || recipe instanceof RecipesCovalenceRepair) {
				return MappingConfig.isEnabled(DamageProcessor.INSTANCE);
			} else if (recipe instanceof FireworkStarRecipe || recipe instanceof FireworkStarFadeRecipe) {
				return MappingConfig.isEnabled(FireworkStarProcessor.INSTANCE);
			} else if (recipe instanceof FireworkRocketRecipe) {
				return MappingConfig.isEnabled(FireworkProcessor.INSTANCE);
			}
			//TODO: Do we eventually want to try and figure out how to handle the armor dye recipe?
			//Not needed, it just recreates the smelting recipes
			return recipe instanceof PhiloStoneSmeltingRecipe
				   //Cloning recipes, creates something from itself, doesn't change overall emc values as amounts all balance out
					|| recipe instanceof BookCloningRecipe || recipe instanceof BannerDuplicateRecipe;
		} else if (recipe instanceof MapExtendingRecipe) {
			return MappingConfig.isEnabled(MapScaleProcessor.INSTANCE);
		}
		return false;
	}

	@Override
	public final boolean canHandle(RecipeType<?> recipeType) {
		return recipeType == RecipeType.CRAFTING;
	}

	@Override
	public String getName() {
		return PEConfigTranslations.MAPPING_CRAFTING_MAPPER_MARK_HANDLED.title();
	}

	@Override
	public String getTranslationKey() {
		return PEConfigTranslations.MAPPING_CRAFTING_MAPPER_MARK_HANDLED.getTranslationKey();
	}

	@Override
	public String getDescription() {
		return PEConfigTranslations.MAPPING_CRAFTING_MAPPER_MARK_HANDLED.tooltip();
	}
}