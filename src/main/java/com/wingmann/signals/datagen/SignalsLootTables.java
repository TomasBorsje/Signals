package com.wingmann.signals.datagen;

import com.wingmann.signals.setup.Registration;
import net.minecraft.data.DataGenerator;

public class SignalsLootTables extends BaseLootTableProvider {
    public SignalsLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(Registration.SATELLITE_DISH_FRAME.get(), createSimpleTable("satellite_dish_frame", Registration.SATELLITE_DISH_FRAME.get()));
        lootTables.put(Registration.SATELLITE_DISH_SUPPORT.get(), createSimpleTable("satellite_dish_support", Registration.SATELLITE_DISH_SUPPORT.get()));
        lootTables.put(Registration.SATELLITE_ANTENNA.get(), createSimpleTable("satellite_dish_antenna", Registration.SATELLITE_ANTENNA.get()));
        lootTables.put(Registration.SIGNAL_TERMINAL.get(), createStandardTable("signal_terminal", Registration.SIGNAL_TERMINAL.get(), Registration.SIGNAL_TERMINAL_BLOCK_ENTITY.get()));
        lootTables.put(Registration.EXO_MINING_STATION.get(), createStandardTable("exo_mining_station", Registration.EXO_MINING_STATION.get(), Registration.EXO_MINING_STATION_BLOCK_ENTITY.get()));
        lootTables.put(Registration.SIGNAL_LOCATOR.get(), createStandardTable("signal_locator", Registration.SIGNAL_LOCATOR.get(), Registration.SIGNAL_LOCATOR_BLOCK_ENTITY.get()));
    }
}
