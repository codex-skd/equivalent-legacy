package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.gui.BagMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AlchemicalBag extends Item {
    public static final int SLOT_COUNT = 13;

    public AlchemicalBag(Properties properties) {
        super(properties);
    }

    public static ItemStackHandler createInventory(ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                NonNullList<ItemStack> list = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
                for (int i = 0; i < SLOT_COUNT; i++) {
                    list.set(i, getStackInSlot(i));
                }
                stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(list));
                super.onContentsChanged(slot);
            }
        };
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        for (int i = 0; i < Math.min(SLOT_COUNT, contents.getSlots()); i++) {
            handler.setStackInSlot(i, contents.getStackInSlot(i));
        }
        return handler;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider((id, inv, p) -> new BagMenu(id, inv, hand),
                    Component.translatable("container.equivalent_legacy.alchemical_bag")),
                    buf -> buf.writeEnum(hand));
        }
        return InteractionResult.CONSUME;
    }
}
