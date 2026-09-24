package com.skd.equivalentlegacy.gameObjs.blocks;

import com.skd.equivalentlegacy.gameObjs.block_entities.CondenserBlockEntity;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
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

	//Overrides AlchemicalChest#onRemove: a Condenser's block entity is a CondenserBlockEntity, not an
	// AlchBlockEntityChest, so drop its input/output inventories here instead (26.2 did this from
	// CondenserBlockEntity#preRemoveSideEffects, a hook 1.21.1 does not have).
	@Override
	@Deprecated
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof CondenserBlockEntity condenser) {
			condenser.dropContentsOnRemoval(level, pos);
		}
		//AlchemicalChest#onRemove only drops when the block entity is an AlchBlockEntityChest, so this is safe
		// (it will not double-drop the CondenserBlockEntity inventories handled above).
		super.onRemove(state, level, pos, newState, isMoving);
	}
}