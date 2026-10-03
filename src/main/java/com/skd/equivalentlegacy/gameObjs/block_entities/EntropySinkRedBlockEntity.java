package com.skd.equivalentlegacy.gameObjs.block_entities;

import com.skd.equivalentlegacy.gameObjs.EnumEntropySinkTier;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EntropySinkRedBlockEntity extends EntropySinkBlockEntity {

	public EntropySinkRedBlockEntity(BlockPos pos, BlockState state) {
		super(PEBlockEntityTypes.ENTROPY_SINK_RED, pos, state, EnumEntropySinkTier.RED);
	}
}
