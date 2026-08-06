package com.skd.equivalentlegacy.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import com.skd.equivalentlegacy.world_transmutation.TransmutationConfig;
import com.skd.equivalentlegacy.world_transmutation.WorldTransmutationManager;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public final class ModCommands {
    private ModCommands() {}

    @SubscribeEvent
    static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher(), event.getBuildContext());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(
            Commands.literal(EquivalentLegacy.MODID)
                .then(Commands.literal("emc")
                    .executes(ctx -> showEmc(ctx.getSource()))
                    .then(Commands.literal("give")
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.argument("amount", LongArgumentType.longArg(0L))
                                .executes(ctx -> giveEmc(
                                        ctx.getSource(),
                                        EntityArgument.getPlayer(ctx, "player"),
                                        LongArgumentType.getLong(ctx, "amount")))
                            )
                        )
                    )
                    .then(Commands.argument("item", ItemArgument.item(context))
                        .executes(ctx -> viewItemEmc(ctx.getSource(), ItemArgument.getItem(ctx, "item")))
                    )
                )
                .then(Commands.literal("transmute")
                    .requires(Commands.hasPermission(Commands.LEVEL_MODERATORS))
                    .then(Commands.argument("item", ItemArgument.item(context))
                        .then(Commands.argument("amount", com.mojang.brigadier.arguments.IntegerArgumentType.integer(1))
                            .executes(ctx -> transmuteCommand(
                                    ctx.getSource(),
                                    ItemArgument.getItem(ctx, "item"),
                                    com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(ctx, "amount")))
                        )
                    )
                )
                .then(Commands.literal("reload")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .executes(ctx -> reloadCommand(ctx.getSource()))
                )
                .then(Commands.literal("knowledge")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .executes(ctx -> showKnowledge(ctx.getSource(), null))
                    .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> showKnowledge(ctx.getSource(), EntityArgument.getPlayer(ctx, "player")))
                    )
                )
        );
    }

    private static int showEmc(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        long emc = PlayerKnowledge.of(player).getEmc();
        source.sendSuccess(() -> Component.translatable("command.equivalent_legacy.emc", emc), false);
        return 1;
    }

    private static int giveEmc(CommandSourceStack source, ServerPlayer target, long amount) {
        PlayerKnowledge.of(target).addEmc(amount);
        PlayerKnowledge.of(target).syncEmc(target);
        source.sendSuccess(() -> Component.translatable("command.equivalent_legacy.emc.give", amount, target.getName()), true);
        target.sendSystemMessage(Component.translatable("command.equivalent_legacy.emc.give.target", amount));
        return 1;
    }

    private static int viewItemEmc(CommandSourceStack source, ItemInput itemInput) throws CommandSyntaxException {
        ItemStack stack = itemInput.createItemStack(1);
        long emc = EMCHelper.getEMC(NSSItem.createItem(stack));
        source.sendSuccess(() -> Component.translatable(
                "command.equivalent_legacy.emc.view",
                stack.getHoverName().getString(),
                emc), false);
        return 1;
    }

    private static int transmuteCommand(CommandSourceStack source, ItemInput itemInput, int count) throws CommandSyntaxException {
        ItemStack stack = itemInput.createItemStack(1);
        ServerPlayer player = source.getPlayerOrException();
        Block sourceBlock = Block.byItem(stack.getItem());
        if (sourceBlock == Blocks.AIR) {
            source.sendFailure(Component.literal("Item is not a block"));
            return 0;
        }
        Block target = WorldTransmutationManager.getTransmutationTarget(sourceBlock);
        if (target == null) {
            source.sendFailure(Component.literal("No transmutation target registered for this block"));
            return 0;
        }
        int succeeded = 0;
        BlockPos origin = BlockPos.containing(player.getX(), player.getY(), player.getZ());
        for (int dx = -TransmutationConfig.getRange(); dx <= TransmutationConfig.getRange() && succeeded < count; dx++) {
            for (int dy = -TransmutationConfig.getRange(); dy <= TransmutationConfig.getRange() && succeeded < count; dy++) {
                for (int dz = -TransmutationConfig.getRange(); dz <= TransmutationConfig.getRange() && succeeded < count; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (player.level().getBlockState(pos).getBlock() == sourceBlock) {
                        if (WorldTransmutationManager.transmute(player, pos, target)) {
                            succeeded++;
                        }
                    }
                }
            }
        }
        final int done = succeeded;
        source.sendSuccess(() -> Component.translatable("command.equivalent_legacy.transmute", done), true);
        return done;
    }

    private static int reloadCommand(CommandSourceStack source) {
        EquivalentLegacyConfig.reload();
        source.sendSuccess(() -> Component.translatable("command.equivalent_legacy.reload"), true);
        return 1;
    }

    private static int showKnowledge(CommandSourceStack source, ServerPlayer target) throws CommandSyntaxException {
        ServerPlayer player = target != null ? target : source.getPlayerOrException();
        long emc = PlayerKnowledge.of(player).getEmc();
        source.sendSuccess(() -> Component.translatable(
                "command.equivalent_legacy.knowledge",
                player.getName(),
                emc), false);
        return 1;
    }
}
