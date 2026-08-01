package com.skd.equivalentlegacy.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.skd.equivalentlegacy.EquivalentLegacy;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = EquivalentLegacy.MODID)
public final class ModCommands {
    private ModCommands() {}

    @SubscribeEvent
    static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
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
}
