package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Tome extends Item {
    public Tome(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            PlayerKnowledge knowledge = PlayerKnowledge.of(player);
            if (!knowledge.hasFullKnowledge()) {
                knowledge.setFullKnowledge(true);
                if (player instanceof ServerPlayer serverPlayer) {
                    knowledge.sync(serverPlayer);
                }
            }
            stack.shrink(1);
        }
        return InteractionResult.CONSUME;
    }
}
