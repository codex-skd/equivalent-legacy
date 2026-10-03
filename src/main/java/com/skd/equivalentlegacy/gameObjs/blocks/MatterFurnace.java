package com.skd.equivalentlegacy.gameObjs.blocks;

import com.mojang.serialization.MapCodec;
import com.skd.equivalentlegacy.gameObjs.EnumMatterType;
import com.skd.equivalentlegacy.gameObjs.block_entities.DMFurnaceBlockEntity;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockTypes;
import com.skd.equivalentlegacy.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MatterFurnace extends AbstractFurnaceBlock implements IMatterBlock, PEEntityBlock<DMFurnaceBlockEntity> {

	private final EnumMatterType matterType;

	public MatterFurnace(Properties props, EnumMatterType type) {
		super(props);
		this.matterType = type;
	}

	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends DMFurnaceBlockEntity> getType() {
		return matterType == EnumMatterType.RED_MATTER ? PEBlockEntityTypes.RED_MATTER_FURNACE : PEBlockEntityTypes.DARK_MATTER_FURNACE;
	}

	@NotNull
	@Override
	protected MapCodec<MatterFurnace> codec() {
		return PEBlockTypes.MATTER_FURNACE.value();
	}

	@Override
	protected void openContainer(Level level, @NotNull BlockPos pos, @NotNull Player player) {
		if (!level.isClientSide) {
			DMFurnaceBlockEntity furnace = WorldHelper.getBlockEntity(DMFurnaceBlockEntity.class, level, pos, true);
			if (furnace != null) {
				player.openMenu(furnace, pos);
			}
		}
	}

	//onBlockStateChange fires only after LevelChunk#setBlockState has already removed this block's block entity,
	// so a drop from there always finds it gone. Block#onRemove runs while the block entity is still present,
	// which is where 26.2 relied on DMFurnaceBlockEntity#preRemoveSideEffects (a hook 1.21.1 does not have).
	@Override
	@Deprecated
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof DMFurnaceBlockEntity furnace) {
			furnace.dropContentsOnRemoval(level, pos);
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	@Deprecated
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		return ItemHandlerHelper.calcRedstoneFromInventory(WorldHelper.getItemHandler(level, pos, state, null, null));
	}

	@Override
	public EnumMatterType getMatterType() {
		return matterType;
	}
}