package com.wingmann.signals.datagen;

import com.wingmann.signals.Signals;
import com.wingmann.signals.setup.Registration;
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
        withExistingParent(Registration.SATELLITE_DISH_FRAME.getId().getPath(), modLoc("block/satellite_dish_frame"));
        withExistingParent(Registration.SATELLITE_DISH_SUPPORT.getId().getPath(), modLoc("block/satellite_dish_support"));
        withExistingParent(Registration.SATELLITE_ANTENNA.getId().getPath(), modLoc("block/satellite_antenna"));
        withExistingParent(Registration.EXO_MINING_STATION.getId().getPath(), modLoc("block/exo_mining_station"));
        // Add a single texture with tape item
        singleTexture(Registration.TAPE_ITEM.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/tape"));
    }
}
