package com.champib.fantasy.events;

import com.champib.fantasy.FantasyMod;
import com.champib.fantasy.commands.ReturnHomeCommand;
import com.champib.fantasy.commands.SetHomeCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = FantasyMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public  static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if (!event.getOriginal().getCommandSenderWorld().isClientSide) {
            event.getEntity().getPersistentData().putIntArray(
                    FantasyMod.MOD_ID + "homepos",
                    event.getOriginal().getPersistentData().getIntArray(FantasyMod.MOD_ID + "homepos")
            );
        }
    }
}
