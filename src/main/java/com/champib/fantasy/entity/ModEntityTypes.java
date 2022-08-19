package com.champib.fantasy.entity;

import com.champib.fantasy.FantasyMod;
import com.champib.fantasy.entity.custom.PirateKingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FantasyMod.MOD_ID);

    public static RegistryObject<EntityType<PirateKingEntity>> PIRATE_KING
            = ENTITY_TYPES.register(
                "pirate_king", () -> EntityType.Builder.of(PirateKingEntity::new, MobCategory.MONSTER)
                    .sized(1f, 3f)
                    .build(new ResourceLocation(FantasyMod.MOD_ID, "pirate_king").toString())
            );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
