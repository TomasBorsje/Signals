package com.wingmann.signals.datagen;

import com.wingmann.signals.Signals;
import com.wingmann.signals.blocks.ExoMiningStationBlock;
import com.wingmann.signals.blocks.SignalTerminalBlock;
import com.wingmann.signals.setup.Registration;
import com.wingmann.signals.setup.ModSetup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class SignalsLanguageProvider extends LanguageProvider {
    public SignalsLanguageProvider(DataGenerator gen, String locale) {
        super(gen, Signals.MODID, locale);
    }
    @Override
    protected void addTranslations() {
        // TODO: Split this up into multiple methods
        add("itemGroup." + ModSetup.TAB_NAME, "Signals");
        add(Registration.SIGNAL_TERMINAL.get(), "Signal Terminal");
        add(Registration.SATELLITE_DISH_FRAME.get(), "Satellite Dish Frame");
        add(Registration.SATELLITE_DISH_SUPPORT.get(), "Satellite Dish Support");
        add(Registration.SATELLITE_ANTENNA.get(), "Satellite Antenna");
        add(SignalTerminalBlock.DESCRIPTION, "A satellite terminal that allows the user to download extraterrestrial signals and store them onto tapes.");
        add(SignalTerminalBlock.SCREEN_SIGNAL_TERMINAL, "Signal Terminal");

        // Exo-Mining Station
        add(ExoMiningStationBlock.DESCRIPTION, "A station in charge of sending exo-miner drones to harvest valuable resources from outer space.");
        add(ExoMiningStationBlock.SCREEN_EXO_MINING_STATION, "Exo-Mining Station");
        add(Registration.EXO_MINING_STATION.get(), "Exo-Mining Station");

        add(Registration.TAPE_ITEM.get(), "Tape");
        add("signals.empty_tape", "Empty");
        // Signal data tooltip
        add("signals.tooltips.signal_name_title", "Signal Name: ");
        add("signals.tooltips.signal_description_title", "Signal Description:");
        add("signals.tooltips.download_title", "Download Progress:");
        // UI
        add("signals.ui.signal_terminal_title", "Signal Terminal");
        // Unknown Signal
        add("signals.signal.unknown_signal", "Unknown Signal");
        add("signals.signal.unknown_signal_description", "This signal is unknown.");
        // Test Signals
        add("signals.signal.basic1", "Nearby Asteroid");
        add("signals.signal.basic1_description", "A nearby asteroid. Downloads very quickly.");
        add("signals.signal.basic2", "Distant Exoplanet");
        add("signals.signal.basic2_description", "A distant exoplanet. Slow download speed.");

        addPatchouliBook();
    }

    private void addPatchouliBook() {
        add("signals.book.title", "Signals And You");
        add("signals.book.description", "Signals is a space-based resource generation mod focused on detecting signals from outer planets and investigating them for loot.");
    }
}
