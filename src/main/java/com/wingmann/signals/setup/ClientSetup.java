package com.wingmann.signals.setup;

import com.wingmann.signals.Signals;
import com.wingmann.signals.screens.ExoMiningStationScreen;
import com.wingmann.signals.screens.SignalTerminalScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Signals.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.SIGNAL_TERMINAL_CONTAINER.get(), SignalTerminalScreen::new);
            MenuScreens.register(Registration.EXO_MINING_STATION_CONTAINER.get(), ExoMiningStationScreen::new);
        });
    }
}