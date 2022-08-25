package com.champib.sao.item;
import com.champib.sao.ModCreativeTab;
import com.champib.sao.SwordArtOnlineMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SwordArtOnlineMod.MOD_ID);

    // TODO public static final RegistryObject<Item> AMETHYST = ITEMS.register(
    // TODO         "amethyst",
    // TODO         () -> new Item(new Item.Properties().tab(ModCreativeTab.TAB_SAO))
    // TODO );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
