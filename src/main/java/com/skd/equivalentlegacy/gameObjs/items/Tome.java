package com.skd.equivalentlegacy.gameObjs.items;
import java.util.List;

import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

public class Tome extends ItemPE {

	public Tome(Properties props) {
		super(props);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		tooltip.add(PELang.TOOLTIP_TOME.translate());
	}
}