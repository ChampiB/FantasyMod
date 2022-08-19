package com.champib.fantasy.item;
import com.champib.fantasy.FantasyMod;
import com.champib.fantasy.entity.ModEntityTypes;
import com.champib.fantasy.item.custom.ModSpawnEggItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FantasyMod.MOD_ID);

    public static final RegistryObject<Item> AMETHYST = ITEMS.register(
            "amethyst",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TAB_FANTASY))
    );

    public static final RegistryObject<Item> PIRATE_KING_SPAWN_EGG = ITEMS.register(
            "pirate_king_spawn_egg",
            () -> new ModSpawnEggItem(
                    ModEntityTypes.PIRATE_KING, 0x464F56, 0x1D6336,
                    new Item.Properties().tab(ModCreativeModeTab.TAB_FANTASY)
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
