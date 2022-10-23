package com.example.examplemod.datagen;

import com.example.examplemod.Signals;
import com.example.examplemod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SignalsItemModels extends ItemModelProvider {
    public SignalsItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Signals.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.SIGNAL_TERMINAL_ITEM.getId().getPath(), modLoc("block/signal_terminal"));
    }
}
