package com.champib.fantasy.events;

import com.champib.fantasy.FantasyMod;
import com.champib.fantasy.entity.ModEntityTypes;
import com.champib.fantasy.entity.custom.PirateKingEntity;
import com.champib.fantasy.item.custom.ModSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.registries.NewRegistryEvent;


@Mod.EventBusSubscriber(modid = FantasyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.PIRATE_KING.get(), PirateKingEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterEntities(NewRegistryEvent event) {
        ModSpawnEggItem.initSpawnEggs();
    }
}
