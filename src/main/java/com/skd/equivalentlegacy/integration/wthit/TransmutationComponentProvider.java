package com.skd.equivalentlegacy.integration.wthit;

import com.skd.equivalentlegacy.player.PlayerKnowledge;
import com.skd.equivalentlegacy.world_transmutation.TransmutationResult;
import com.skd.equivalentlegacy.world_transmutation.WorldTransmutationManager;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

/**
 * WTHIT/Jade block component provider: appends the transmutation target and its EMC cost to the tooltip
 * shown when hovering a transmutable block. The cost line is green when affordable, red when the player
 * lacks enough EMC, and grey when the conversion is free.
 */
public class TransmutationComponentProvider implements IBlockComponentProvider {
    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        Block block = accessor.getBlock();
        if (!WorldTransmutationManager.isTransmutable(block)) return;
        TransmutationResult result = WorldTransmutationManager.getTransmutation(block);
        if (result == null || result.resultBlock() == null) return;

        long playerEmc = accessor.getPlayer() != null ? PlayerKnowledge.of(accessor.getPlayer()).getEmc() : 0L;
        ChatFormatting color;
        if (result.emcCost() <= 0L) {
            color = ChatFormatting.GRAY;
        } else {
            color = playerEmc >= result.emcCost() ? ChatFormatting.GREEN : ChatFormatting.RED;
        }

        tooltip.addLine().with(Component.translatable("tooltip.equivalent_legacy.transmutable",
                result.resultBlock().getName().getString()).withStyle(ChatFormatting.GOLD));
        tooltip.addLine().with(Component.translatable("tooltip.equivalent_legacy.transmutation_cost",
                result.emcCost()).withStyle(color));
    }
}
