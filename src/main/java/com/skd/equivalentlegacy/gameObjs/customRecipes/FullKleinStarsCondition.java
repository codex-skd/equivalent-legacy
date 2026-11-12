package com.skd.equivalentlegacy.gameObjs.customRecipes;

import com.mojang.serialization.MapCodec;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.registries.PERecipeConditions;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class FullKleinStarsCondition implements ICondition {

	public static final FullKleinStarsCondition INSTANCE = new FullKleinStarsCondition();

	private FullKleinStarsCondition() {
	}

	@Override
	public boolean test(@NotNull IContext context) {
		return EquivalentLegacyConfig.common.fullKleinStars.get();
	}

	@NotNull
	@Override
	public MapCodec<? extends ICondition> codec() {
		return PERecipeConditions.FULL_KLEIN_STARS.value();
	}
}