package com.example.examplemod.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
    public static String TAB_NAME = "signals";
    static CreativeModeTab ITEM_TAB = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return Items.AMETHYST_BLOCK.getDefaultInstance();
        }
    };
    public static void init(final FMLCommonSetupEvent event) {

    }
}
