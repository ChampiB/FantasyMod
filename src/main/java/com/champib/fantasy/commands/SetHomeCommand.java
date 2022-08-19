package com.champib.fantasy.commands;

import com.champib.fantasy.FantasyMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {

    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").then(Commands.literal("set").executes((command) -> {
            return setHome(command.getSource());
        })));
    }

    private int setHome(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayer();
        BlockPos playerPos = player.getOnPos();
        String pos = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";

        player.getPersistentData().putIntArray(
                FantasyMod.MOD_ID + "homepos",
                new int[]{ playerPos.getX(), playerPos.getY(), playerPos.getZ() }
        );

        // TODO? source.sendSuccess(new TextComponentTagVisitor("Set Home at " + pos), true);
        return 1;
    }
}