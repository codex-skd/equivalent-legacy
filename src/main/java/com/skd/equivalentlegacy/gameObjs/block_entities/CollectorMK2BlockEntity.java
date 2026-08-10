package com.skd.equivalentlegacy.gameObjs.block_entities;

import com.skd.equivalentlegacy.gameObjs.EnumCollectorTier;
import com.skd.equivalentlegacy.gameObjs.container.CollectorMK2Container;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlocks;
import com.skd.equivalentlegacy.utils.text.TextComponentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CollectorMK2BlockEntity extends CollectorMK1BlockEntity {

	public CollectorMK2BlockEntity(BlockPos pos, BlockState state) {
		super(PEBlockEntityTypes.COLLECTOR_MK2, pos, state, EnumCollectorTier.MK2);
	}

	@Override
	protected int getInvSize() {
		return 12;
	}

	@NotNull
	@Override
	public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player playerIn) {
		return new CollectorMK2Container(windowId, playerInventory, this);
	}

	@NotNull
	@Override
	public Component getDisplayName() {
		return TextComponentUtil.build(PEBlocks.COLLECTOR_MK2);
	}
}