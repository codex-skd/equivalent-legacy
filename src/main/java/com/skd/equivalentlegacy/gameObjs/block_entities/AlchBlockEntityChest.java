package com.skd.equivalentlegacy.gameObjs.block_entities;

import com.skd.equivalentlegacy.api.capabilities.PECapabilities;
import com.skd.equivalentlegacy.api.capabilities.item.IAlchChestItem;
import com.skd.equivalentlegacy.gameObjs.container.AlchChestContainer;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlocks;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.TextComponentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlchBlockEntityChest extends EmcChestBlockEntity {

	public static final ICapabilityProvider<AlchBlockEntityChest, @Nullable Direction, IItemHandler> INVENTORY_PROVIDER = (chest, side) -> chest.inventory;

	private final StackHandler inventory = new StackHandler(104) {
		@Override
		public void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			if (level != null && !level.isClientSide) {
				inventoryChanged = true;
			}
		}
	};
	private boolean inventoryChanged;

	public AlchBlockEntityChest(BlockPos pos, BlockState state) {
		super(PEBlockEntityTypes.ALCHEMICAL_CHEST, pos, state, 1_000);
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		inventory.deserializeNBT(registries, tag.getCompound("inventory"));
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.put("inventory", inventory.serializeNBT(registries));
	}

	public static void tickClient(Level level, BlockPos pos, BlockState state, AlchBlockEntityChest alchChest) {
		for (int i = 0, slots = alchChest.inventory.getSlots(); i < slots; i++) {
			ItemStack stack = alchChest.inventory.getStackInSlot(i);
			IAlchChestItem alchChestItem = stack.getCapability(PECapabilities.ALCH_CHEST_ITEM_CAPABILITY);
			if (alchChestItem != null) {
				alchChestItem.updateInAlchChest(level, pos, stack);
			}
		}
		EmcChestBlockEntity.lidAnimateTick(level, pos, state, alchChest);
	}

	public static void tickServer(Level level, BlockPos pos, BlockState state, AlchBlockEntityChest alchChest) {
		StackHandler inventory = alchChest.inventory;
		for (int i = 0, slots = inventory.getSlots(); i < slots; i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			IAlchChestItem alchChestItem = stack.getCapability(PECapabilities.ALCH_CHEST_ITEM_CAPABILITY);
			if (alchChestItem != null && alchChestItem.updateInAlchChest(level, pos, stack)) {
				inventory.onContentsChanged(i);
			}
		}
		if (alchChest.inventoryChanged) {
			//If the inventory changed, resync so that the client can tick things properly
			alchChest.inventoryChanged = false;
			level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
		}
		alchChest.updateComparators(level, pos);
	}

	public IItemHandler getInventory() {
		return inventory;
	}

	/**
	 * Drops this chest's contents. Called from {@link com.skd.equivalentlegacy.gameObjs.blocks.AlchemicalChest#onRemove}
	 * while the block entity is still valid (26.2 used the {@code BlockEntity#preRemoveSideEffects} hook here; 1.21.1
	 * has no such hook, so the block's {@code onRemove} drives it instead, before the block entity is torn down).
	 */
	public void dropContentsOnRemoval(@NotNull Level level, @NotNull BlockPos pos) {
		WorldHelper.dropInventory(inventory, level, pos);
	}

	@NotNull
	@Override
	public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player playerIn) {
		return new AlchChestContainer(windowId, playerInventory, this);
	}

	@NotNull
	@Override
	public Component getDisplayName() {
		return TextComponentUtil.build(PEBlocks.ALCHEMICAL_CHEST);
	}
}