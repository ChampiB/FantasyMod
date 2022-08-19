package com.champib.fantasy.commands;

import com.champib.fantasy.FantasyMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class ReturnHomeCommand {
    public ReturnHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").then(Commands.literal("return").executes((command) -> {
            return returnHome(command.getSource());
        })));
    }

    private int returnHome(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        boolean hasHomepos = player.getPersistentData().getIntArray(FantasyMod.MOD_ID + "homepos").length != 0;

        if(hasHomepos) {
            int[] playerPos = player.getPersistentData().getIntArray(FantasyMod.MOD_ID + "homepos");
            player.setPos(playerPos[0], playerPos[1], playerPos[2]);

            // TODO? source.sendFeedback(new StringTextComponent("Player returned Home!"), true);
            return 1;
        } else {
            // TODO? source.sendFeedback(new StringTextComponent("No Home Position has been set!"), true);
            return -1;
        }
    }
}