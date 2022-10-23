package com.example.examplemod.datagen;

import com.example.examplemod.Signals;
import com.example.examplemod.setup.Registration;
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

        simpleBlock(Registration.SIGNAL_TERMINAL.get());
        simpleBlock(Registration.SATELLITE_DISH_FRAME.get());
        simpleBlock(Registration.SATELLITE_ANTENNA.get());
    }
}
