package com.champib.sao;

import com.champib.sao.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {

    public static final CreativeModeTab TAB_SAO = new CreativeModeTab("SAO") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.FROZEN_BLUE_ROSE.get());
        }
    };
}
