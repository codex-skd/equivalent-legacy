package com.skd.equivalentlegacy.gameObjs.blocks;

import com.skd.equivalentlegacy.gameObjs.IMatterType;
import net.minecraft.world.level.block.Block;

public class MatterBlock extends Block implements IMatterBlock {

	public final IMatterType matterType;

	public MatterBlock(Properties props, IMatterType type) {
		super(props);
		this.matterType = type;
	}

	@Override
	public IMatterType getMatterType() {
		return matterType;
	}
}