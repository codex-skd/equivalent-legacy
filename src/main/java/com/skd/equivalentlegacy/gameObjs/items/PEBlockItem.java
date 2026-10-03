package com.skd.equivalentlegacy.gameObjs.items;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PEBlockItem extends BlockItem {

	private final TooltipAppender tooltipAppender;

	public PEBlockItem(Block block, Properties props, TooltipAppender tooltipAppender) {
		super(block, props);
		this.tooltipAppender = tooltipAppender;
	}

	public PEBlockItem(Block block, Properties props) {
		this(block, props, null);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip,
			@NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		if (tooltipAppender != null) {
			tooltipAppender.append(stack, tooltip, flags);
		}
	}

	@FunctionalInterface
	public interface TooltipAppender {
		void append(ItemStack stack, List<Component> tooltip, TooltipFlag flags);
	}
}
