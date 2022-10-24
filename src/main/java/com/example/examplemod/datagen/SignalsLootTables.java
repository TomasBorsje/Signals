package com.example.examplemod.datagen;

import com.example.examplemod.setup.Registration;
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
    }
}
