package com.example.examplemod.datagen;

import com.example.examplemod.Signals;
import com.example.examplemod.blocks.SignalTerminalBlock;
import com.example.examplemod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.example.examplemod.setup.ModSetup.TAB_NAME;

public class SignalsLanguageProvider extends LanguageProvider {
    public SignalsLanguageProvider(DataGenerator gen, String locale) {
        super(gen, Signals.MODID, locale);
    }
    @Override
    protected void addTranslations() {
        add("itemGroup." + TAB_NAME, "Signals");
        add(Registration.SIGNAL_TERMINAL.get(), "Signal Terminal");
        add(Registration.SATELLITE_DISH_FRAME.get(), "Satellite Dish Frame");
        add(Registration.SATELLITE_DISH_SUPPORT.get(), "Satellite Dish Support");
        add(Registration.SATELLITE_ANTENNA.get(), "Satellite Antenna");
        add(SignalTerminalBlock.MESSAGE_SIGNAL_TERMINAL, "Message Signal Terminal");
        add(SignalTerminalBlock.SCREEN_SIGNAL_TERMINAL, "Screen Signal Terminal");
    }
}
