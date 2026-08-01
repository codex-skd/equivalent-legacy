package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.gui.ChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.state.BlockState;

public class AlchemicalChestBlockEntity extends BaseMachineBlockEntity {
    public static final int SLOT_COUNT = 13;

    public AlchemicalChestBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST.get(), pos, state, SLOT_COUNT);
    }

    @Override
    protected MenuProvider createMenuProvider() {
        return new SimpleMenuProvider((id, inv, p) -> new ChestMenu(id, inv, worldPosition), getDisplayName());
    }

    @Override
    protected Component getDisplayName() {
        return Component.translatable("container.equivalent_legacy.alchemical_chest");
    }
}
