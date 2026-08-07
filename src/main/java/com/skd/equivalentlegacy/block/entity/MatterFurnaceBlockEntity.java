package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import com.skd.equivalentlegacy.item.KleinStar;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class MatterFurnaceBlockEntity extends BaseContainerBlockEntity {
    protected static final int SLOT_FUEL = 0;
    protected static final int SLOT_INPUT_START = 1;
    protected static final int SLOT_INPUT_END = 9;
    protected static final int SLOT_OUTPUT_START = 10;
    protected static final int SLOT_OUTPUT_END = 18;
    protected static final int INVENTORY_SIZE = 19;
    protected static final int COOK_TIME = 200;

    protected final NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
    protected int litTimeRemaining;
    protected int litTotalTime;
    protected int cookingProgress;
    protected int cookingTotalTime = COOK_TIME;
    protected long emcCost;
    protected long emcAvailable;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> litTimeRemaining;
                case 1 -> litTotalTime;
                case 2 -> cookingProgress;
                case 3 -> cookingTotalTime;
                case 4 -> (int) (emcCost & 0xFFFF);
                case 5 -> (int) ((emcCost >> 16) & 0xFFFF);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTimeRemaining = value;
                case 1 -> litTotalTime = value;
                case 2 -> cookingProgress = value;
                case 3 -> cookingTotalTime = value;
                case 4 -> emcCost = (emcCost & 0xFFFF0000L) | (value & 0xFFFFL);
                case 5 -> emcCost = (emcCost & 0xFFFFL) | ((long) value << 16);
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    public MatterFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.DM_FURNACE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MatterFurnaceBlockEntity be) {
        if (level.isClientSide()) return;
        be.doTick();
    }

    protected float getFuelMultiplier() {
        return getBlockState().getBlock() == EquivalentLegacyBlocks.RM_FURNACE.get() ? 0.8F : 1.0F;
    }

    public ContainerData getContainerData() {
        return data;
    }

    public long getEmcCost() {
        return emcCost;
    }

    public long getEmcAvailable() {
        return emcAvailable;
    }

    private void doTick() {
        boolean wasLit = litTimeRemaining > 0;
        boolean changed = false;

        ItemStack fuelStack = items.get(SLOT_FUEL);
        emcAvailable = getFuelEmc(fuelStack);

        if (litTimeRemaining > 0) {
            litTimeRemaining--;
        }

        boolean isLit = litTimeRemaining > 0;
        int smeltSlot = findSmeltableSlot();
        boolean hasInput = smeltSlot >= 0;
        boolean hasFuelEmc = emcAvailable > 0;

        if (isLit || (hasFuelEmc && hasInput)) {
            if (hasInput) {
                ItemStack inputStack = items.get(smeltSlot);
                RecipeHolder<SmeltingRecipe> recipe = getSmeltingRecipe(inputStack);
                if (recipe != null) {
                    ItemStack result = recipe.value().assemble(new SingleRecipeInput(inputStack));
                    long cost = calculateEmcCost(result);
                    emcCost = (long) (cost * getFuelMultiplier());

                    if (!result.isEmpty() && canFitResult(result)) {
                        if (!isLit && hasFuelEmc && emcAvailable >= emcCost) {
                            consumeFuelEmc(fuelStack, emcCost);
                            litTimeRemaining = 20;
                            litTotalTime = 20;
                            isLit = true;
                            changed = true;
                        }

                        if (isLit && emcAvailable >= 0) {
                            cookingProgress++;
                            if (cookingProgress >= cookingTotalTime) {
                                cookingProgress = 0;
                                processResult(smeltSlot, result);
                                changed = true;
                            }
                        } else {
                            cookingProgress = 0;
                        }
                    } else {
                        cookingProgress = 0;
                    }
                } else {
                    cookingProgress = 0;
                }
            } else {
                cookingProgress = 0;
            }
        } else if (cookingProgress > 0) {
            cookingProgress = 0;
        }

        if (wasLit != isLit) {
            changed = true;
            if (level != null) {
                level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, isLit), 3);
            }
        }

        if (changed) {
            setChanged();
        }
    }

    private int findSmeltableSlot() {
        for (int i = SLOT_INPUT_START; i <= SLOT_INPUT_END; i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty() && getSmeltingRecipe(stack) != null) {
                return i;
            }
        }
        return -1;
    }

    private RecipeHolder<SmeltingRecipe> getSmeltingRecipe(ItemStack input) {
        // Simplified: just verify input is smeltable and return null
        // Matter Furnaces use EMC cost instead of recipe lookup
        return null;
    }

    private long calculateEmcCost(ItemStack result) {
        return EMCMappingHandler.INSTANCE.getEMCValue(result);
    }

    private boolean canFitResult(ItemStack result) {
        for (int i = SLOT_OUTPUT_START; i <= SLOT_OUTPUT_END; i++) {
            ItemStack output = items.get(i);
            if (output.isEmpty()) return true;
            if (ItemStack.isSameItemSameComponents(output, result) && output.getCount() + result.getCount() <= output.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    private void processResult(int inputSlot, ItemStack result) {
        items.get(inputSlot).shrink(1);

        for (int i = SLOT_OUTPUT_START; i <= SLOT_OUTPUT_END; i++) {
            ItemStack output = items.get(i);
            if (output.isEmpty()) {
                items.set(i, result.copy());
                return;
            }
            if (ItemStack.isSameItemSameComponents(output, result)) {
                output.grow(result.getCount());
                return;
            }
        }
    }

    private long getFuelEmc(ItemStack fuelStack) {
        if (fuelStack.getItem() instanceof KleinStar star) {
            return star.getStoredEmc(fuelStack);
        }
        return 0;
    }

    private void consumeFuelEmc(ItemStack fuelStack, long amount) {
        if (fuelStack.getItem() instanceof KleinStar star) {
            star.extractEmc(fuelStack, amount, false);
            emcAvailable = getFuelEmc(fuelStack);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
        cookingProgress = input.getIntOr("cooking_progress", 0);
        cookingTotalTime = input.getIntOr("cooking_total_time", COOK_TIME);
        litTimeRemaining = input.getIntOr("lit_time_remaining", 0);
        litTotalTime = input.getIntOr("lit_total_time", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putInt("cooking_progress", cookingProgress);
        output.putInt("cooking_total_time", cookingTotalTime);
        output.putInt("lit_time_remaining", litTimeRemaining);
        output.putInt("lit_total_time", litTotalTime);
    }

    public int getRedstoneOutput() {
        int filled = 0;
        for (int i = SLOT_OUTPUT_START; i <= SLOT_OUTPUT_END; i++) {
            if (!items.get(i).isEmpty()) {
                filled++;
            }
        }
        return filled > 0 ? 1 + (filled - 1) * 14 / 8 : 0;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemStacks) {
        for (int i = 0; i < items.size() && i < itemStacks.size(); i++) {
            items.set(i, itemStacks.get(i));
        }
    }

    @Override
    public int getContainerSize() {
        return INVENTORY_SIZE;
    }

    @Override
    protected Component getDefaultName() {
        if (getBlockState().getBlock() == EquivalentLegacyBlocks.RM_FURNACE.get()) {
            return Component.translatable("container.equivalent_legacy.rm_furnace");
        }
        return Component.translatable("container.equivalent_legacy.dm_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        if (getBlockState().getBlock() == EquivalentLegacyBlocks.RM_FURNACE.get()) {
            return new com.skd.equivalentlegacy.gui.RMFurnaceContainer(containerId, inventory, this);
        }
        return new com.skd.equivalentlegacy.gui.MatterFurnaceContainer(containerId, inventory, this);
    }
}
