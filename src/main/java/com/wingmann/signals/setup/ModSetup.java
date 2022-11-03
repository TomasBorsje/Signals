package com.wingmann.signals.setup;

import com.wingmann.signals.network.SignalsPacketHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
    public static String TAB_NAME = "signals";
    static CreativeModeTab ITEM_TAB = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return Registration.SIGNAL_TERMINAL_ITEM.get().getDefaultInstance();
        }
    };
    public static void init(final FMLCommonSetupEvent event) {
        SignalsPacketHandler.register();
        // TODO: Load in signal registry data
        // Devise way to load in signals from json files
    }
}
