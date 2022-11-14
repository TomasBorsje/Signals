package com.wingmann.signals.datagen;

import com.wingmann.signals.Signals;
import com.wingmann.signals.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SignalsBlockStates extends BlockStateProvider {

    public SignalsBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, Signals.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.SATELLITE_DISH_SUPPORT.get(),
                models().cubeAll(Registration.SATELLITE_DISH_SUPPORT.getId().getPath(), modLoc("block/satellite_dish_support")).renderType("cutout"));

        simpleBlock(Registration.SATELLITE_DISH_FRAME.get());
        simpleBlock(Registration.SATELLITE_ANTENNA.get());
        // Machines
        simpleBlock(Registration.SIGNAL_TERMINAL.get());
        simpleBlock(Registration.EXO_MINING_STATION.get());
        simpleBlock(Registration.SIGNAL_LOCATOR.get());
        simpleBlock(Registration.ALUMINIUM_ORE.get());
    }
}
