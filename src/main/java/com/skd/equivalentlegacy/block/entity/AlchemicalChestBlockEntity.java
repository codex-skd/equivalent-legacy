package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.gui.ChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlchemicalChestBlockEntity extends BaseMachineBlockEntity implements LidBlockEntity {
    public static final int SLOT_COUNT = 13;
    private int openNess = 0;

    public AlchemicalChestBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST.get(), pos, state, SLOT_COUNT);
    }

    @Override
    public float getOpenNess(float partialTick) {
        return Math.min(1.0F, (openNess + partialTick) / 5.0F);
    }

    public void setOpenNess(int openNess) {
        this.openNess = Math.max(0, Math.min(5, openNess));
    }

    public int getOpenNess() {
        return openNess;
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
