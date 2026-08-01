package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.EquivalentLegacyBlocks;
import com.skd.equivalentlegacy.gui.MatterFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class MatterFurnaceBlockEntity extends BaseContainerBlockEntity {
    private static final int SLOT_INPUT = 0;
    private static final int SLOT_FUEL = 1;
    private static final int SLOT_RESULT = 2;
    private static final int COOK_TIME = 200;

    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int litTimeRemaining;
    private int litTotalTime;
    private int cookingTimer;
    private int cookingTotalTime = COOK_TIME;

    private final RecipeManager.CachedCheck<SingleRecipeInput, SmeltingRecipe> quickCheck =
            RecipeManager.createCheck(RecipeType.SMELTING);

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> litTimeRemaining;
                case 1 -> litTotalTime;
                case 2 -> cookingTimer;
                default -> cookingTotalTime;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTimeRemaining = value;
                case 1 -> litTotalTime = value;
                case 2 -> cookingTimer = value;
                default -> cookingTotalTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public MatterFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.MATTER_FURNACE.get(), pos, state);
    }

    private int getSpeed() {
        Block block = getBlockState().getBlock();
        return block == EquivalentLegacyBlocks.RM_FURNACE.get() ? 8 : 4;
    }

    public ContainerData getContainerData() {
        return data;
    }

    public static BlockEntityTicker<MatterFurnaceBlockEntity> ticker() {
        return (level, pos, state, be) -> {
            if (level instanceof ServerLevel serverLevel) {
                be.serverTick(serverLevel);
            }
        };
    }

    private void serverTick(ServerLevel level) {
        int speed = getSpeed();
        boolean changed = false;
        boolean wasLit = litTimeRemaining > 0;
        if (litTimeRemaining > 0) {
            litTimeRemaining = Math.max(0, litTimeRemaining - speed);
        }

        ItemStack fuel = items.get(SLOT_FUEL);
        ItemStack ingredient = items.get(SLOT_INPUT);
        boolean hasIngredient = !ingredient.isEmpty();
        boolean hasFuel = !fuel.isEmpty();
        boolean isLit = litTimeRemaining > 0;

        if (isLit || (hasFuel && hasIngredient)) {
            if (hasIngredient) {
                SingleRecipeInput input = new SingleRecipeInput(ingredient);
                RecipeHolder<? extends SmeltingRecipe> recipe = quickCheck.getRecipeFor(input, level).orElse(null);
                if (recipe != null) {
                    RegistryAccess registryAccess = level.registryAccess();
                    ItemStack burnResult = recipe.value().assemble(input);
                    if (!burnResult.isEmpty() && canBurn(burnResult)) {
                        if (!isLit) {
                            int newLit = level.fuelValues().burnDuration(fuel);
                            litTimeRemaining = newLit;
                            litTotalTime = newLit;
                            if (newLit > 0) {
                                fuel.shrink(1);
                                isLit = true;
                                changed = true;
                            }
                        }
                        if (isLit) {
                            cookingTimer += speed;
                            if (cookingTimer >= cookingTotalTime) {
                                cookingTimer = 0;
                                burn(burnResult);
                                changed = true;
                            }
                        } else {
                            cookingTimer = 0;
                        }
                    } else {
                        cookingTimer = 0;
                    }
                }
            } else {
                cookingTimer = 0;
            }
        } else if (cookingTimer > 0) {
            cookingTimer = 0;
        }

        if (wasLit != isLit) {
            changed = true;
        }
        if (changed) {
            setChanged();
        }
    }

    private boolean canBurn(ItemStack result) {
        ItemStack output = items.get(SLOT_RESULT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void burn(ItemStack result) {
        ItemStack output = items.get(SLOT_RESULT);
        if (output.isEmpty()) {
            items.set(SLOT_RESULT, result.copy());
        } else {
            output.grow(result.getCount());
        }
        items.get(SLOT_INPUT).shrink(1);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
        cookingTimer = input.getIntOr("cooking_time_spent", 0);
        cookingTotalTime = input.getIntOr("cooking_total_time", COOK_TIME);
        litTimeRemaining = input.getIntOr("lit_time_remaining", 0);
        litTotalTime = input.getIntOr("lit_total_time", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putInt("cooking_time_spent", cookingTimer);
        output.putInt("cooking_total_time", cookingTotalTime);
        output.putInt("lit_time_remaining", litTimeRemaining);
        output.putInt("lit_total_time", litTotalTime);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemStacks) {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, itemStacks.get(i));
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack old = items.get(slot);
        boolean same = !stack.isEmpty() && ItemStack.isSameItemSameComponents(old, stack);
        super.setItem(slot, stack);
        if (slot == SLOT_INPUT && !same) {
            cookingTimer = 0;
            setChanged();
        }
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.equivalent_legacy.matter_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new MatterFurnaceMenu(containerId, inventory, this, data);
    }
}
