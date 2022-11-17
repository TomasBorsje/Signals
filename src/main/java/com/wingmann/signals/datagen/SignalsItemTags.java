package com.wingmann.signals.datagen;

import com.wingmann.signals.Signals;
import com.wingmann.signals.setup.Registration;
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
                .add(Registration.ALUMINIUM_ORE_ITEM.get())
                .add(Registration.DEEPSLATE_ALUMINIUM_ORE_ITEM.get());

        tag(Tags.Items.NUGGETS)
                .add(Registration.ALUMINIUM_NUGGET.get());

        tag(Tags.Items.STORAGE_BLOCKS)
                .add(Registration.ALUMINIUM_BLOCK_ITEM.get());

        tag(Registration.ALUMINIUM_ORE_ITEM_TAG)
                .add(Registration.ALUMINIUM_ORE_ITEM.get())
                .add(Registration.DEEPSLATE_ALUMINIUM_ORE_ITEM.get());
    }

    @Override
    public String getName() {
        return "Signals Tags";
    }
}
