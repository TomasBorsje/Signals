package com.wingmann.signals.datagen;

import com.wingmann.signals.Signals;
import com.wingmann.signals.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SignalsBlockTags extends BlockTagsProvider {
    public SignalsBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Signals.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.SIGNAL_TERMINAL.get())
                .add(Registration.SATELLITE_DISH_FRAME.get())
                .add(Registration.SATELLITE_DISH_SUPPORT.get())
                .add(Registration.SATELLITE_ANTENNA.get())
                .add(Registration.EXO_MINING_STATION.get())
                .add(Registration.SIGNAL_LOCATOR.get())
                .add(Registration.ALUMINIUM_ORE.get())
                .add(Registration.DEEPSLATE_ALUMINIUM_ORE.get())
                .add(Registration.ALUMINIUM_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Registration.SIGNAL_TERMINAL.get())
                .add(Registration.SATELLITE_DISH_FRAME.get())
                .add(Registration.SATELLITE_DISH_SUPPORT.get())
                .add(Registration.SATELLITE_ANTENNA.get())
                .add(Registration.EXO_MINING_STATION.get())
                .add(Registration.SIGNAL_LOCATOR.get())
                .add(Registration.ALUMINIUM_ORE.get())
                .add(Registration.DEEPSLATE_ALUMINIUM_ORE.get())
                .add(Registration.ALUMINIUM_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .add(Registration.ALUMINIUM_ORE.get())
                .add(Registration.DEEPSLATE_ALUMINIUM_ORE.get());

        tag(Registration.ALUMINIUM_ORE_TAG)
                .add(Registration.ALUMINIUM_ORE.get())
                .add(Registration.DEEPSLATE_ALUMINIUM_ORE.get());
    }

    @Override
    public String getName() {
        return "Signals Tags";
    }
}
