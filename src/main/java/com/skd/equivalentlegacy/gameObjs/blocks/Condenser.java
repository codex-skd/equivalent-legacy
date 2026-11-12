package com.skd.equivalentlegacy.gameObjs.blocks;

import com.skd.equivalentlegacy.gameObjs.block_entities.CondenserBlockEntity;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

public class Condenser extends AlchemicalChest {

	public Condenser(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends CondenserBlockEntity> getType() {
		return PEBlockEntityTypes.CONDENSER;
	}
}