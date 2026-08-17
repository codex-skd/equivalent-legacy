package com.skd.equivalentlegacy.integration.recipe_viewer.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.utils.text.ILangEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;

public class PEEmiCategory extends EmiRecipeCategory {

	private final Component name;

	public PEEmiCategory(String path, ItemLike icon, ILangEntry name) {
		super(ELCore.rl(path), EmiStack.of(icon));
		this.name = name.translate();
	}

	@Override
	public Component getName() {
		return name;
	}
}