package com.example.examplemod.datagen;

import com.example.examplemod.Signals;
import com.example.examplemod.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SignalsItemTags extends ItemTagsProvider {
    public SignalsItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, Signals.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.ORES)
                .add(Registration.SIGNAL_TERMINAL_ITEM.get());
    }

    @Override
    public String getName() {
        return "Signals Tags";
    }
}
