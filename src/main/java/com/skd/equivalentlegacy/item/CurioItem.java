package com.skd.equivalentlegacy.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class CurioItem extends Item {
    public enum Type {
        BLACK_HOLE_BAND,
        BODY_STONE,
        EVERTIDE_AMULET,
        GEM_OF_ETERNAL_DENSITY,
        HARVEST_GODDESS_BAND,
        IGNITION_RING,
        LIFE_STONE,
        MIND_STONE,
        REPAIR_TALISMAN,
        SOUL_STONE,
        SWIFTWOLF_RENDING_GALE,
        VOID_RING,
        VOLCANITE_AMULET,
        WATCH_OF_FLOWING_TIME,
        ZERO_RING
    }

    private final Type type;

    public CurioItem(Properties properties, Type type) {
        super(properties);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var target = context.getClickedPos().relative(context.getClickedFace());
        switch (type) {
            case IGNITION_RING -> {
                if (level.getBlockState(target).isAir() && !level.isClientSide()) {
                    level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
                }
                return InteractionResult.CONSUME;
            }
            case ZERO_RING -> {
                if (!level.isClientSide() && level.getBlockState(target).getBlock() == Blocks.FIRE) {
                    level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
                }
                return InteractionResult.CONSUME;
            }
            case EVERTIDE_AMULET -> {
                if (level.getBlockState(target).isAir() && !level.isClientSide()) {
                    level.setBlock(target, Blocks.WATER.defaultBlockState(), 3);
                }
                return InteractionResult.CONSUME;
            }
            case VOLCANITE_AMULET -> {
                if (level.getBlockState(target).isAir() && !level.isClientSide()) {
                    level.setBlock(target, Blocks.LAVA.defaultBlockState(), 3);
                }
                return InteractionResult.CONSUME;
            }
            default -> {
                return InteractionResult.PASS;
            }
        }
    }
}
