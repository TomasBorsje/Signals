package com.example.examplemod.datagen;

import com.example.examplemod.Signals;
import com.example.examplemod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SignalsBlockTags extends BlockTagsProvider {
    public SignalsBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Signals.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.SIGNAL_TERMINAL.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Registration.SIGNAL_TERMINAL.get());
    }

    @Override
    public String getName() {
        return "Signals Tags";
    }
}
