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
        simpleBlock(Registration.SIGNAL_TERMINAL.get());
    }
}
