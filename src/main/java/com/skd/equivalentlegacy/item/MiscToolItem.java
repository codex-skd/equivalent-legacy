package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MiscToolItem extends Item {
    public enum Type {
        DIVINING_ROD_1(1),
        DIVINING_ROD_2(2),
        DIVINING_ROD_3(3),
        DESTRUCTION_CATALYST,
        HYPERKINETIC_LENS,
        CATALYTIC_LENS,
        MERCURIAL_EYE;

        public final int radius;

        Type() {
            this.radius = 0;
        }

        Type(int radius) {
            this.radius = radius;
        }
    }

    private final Type type;

    public MiscToolItem(Properties properties, Type type) {
        super(properties);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        if (player == null || level.isClientSide()) return InteractionResult.PASS;

        switch (type) {
            case DIVINING_ROD_1, DIVINING_ROD_2, DIVINING_ROD_3 -> {
                long total = 0;
                int r = type.radius;
                for (int x = -r; x <= r; x++) {
                    for (int y = -r; y <= r; y++) {
                        for (int z = -r; z <= r; z++) {
                            ItemStack stack = new ItemStack(level.getBlockState(pos.offset(x, y, z)).getBlock());
                            if (!stack.isEmpty()) {
                                total += EMCHelper.getEMC(NSSItem.createItem(stack));
                            }
                        }
                    }
                }
                player.sendSystemMessage(Component.literal("Total EMC: " + total));
            }
            case DESTRUCTION_CATALYST -> {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos target = pos.offset(x, y, z);
                            BlockState state = level.getBlockState(target);
                            if (state.isAir() || state.getBlock() == Blocks.BEDROCK) continue;
                            if (state.getDestroySpeed(level, target) < 0.0F) continue;
                            BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(target) : null;
                            Block.dropResources(state, level, target, blockEntity, player, player.getMainHandItem());
                            level.setBlock(target, state.getFluidState().createLegacyBlock(), 3);
                        }
                    }
                }
            }
            case HYPERKINETIC_LENS ->
                    level.explode(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3.0F, Level.ExplosionInteraction.BLOCK);
            case CATALYTIC_LENS ->
                    level.explode(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4.0F, true, Level.ExplosionInteraction.BLOCK);
            case MERCURIAL_EYE ->
                    player.sendSystemMessage(Component.literal("Your EMC: " + PlayerKnowledge.of(player).getEmc()));
        }
        return InteractionResult.CONSUME;
    }
}
