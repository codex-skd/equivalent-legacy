package com.skd.equivalentlegacy.gameObjs.blocks;

import com.skd.equivalentlegacy.gameObjs.block_entities.CondenserMK2BlockEntity;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class CondenserMK2 extends Condenser {

	public CondenserMK2(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<CondenserMK2BlockEntity> getType() {
		return PEBlockEntityTypes.CONDENSER_MK2;
	}
}