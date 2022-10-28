package com.wingmann.signals.datagen;

import com.wingmann.signals.Signals;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Signals.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new SignalsRecipes(generator));
        generator.addProvider(event.includeServer(), new SignalsLootTables(generator));
        SignalsBlockTags blockTags = new SignalsBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new SignalsItemTags(generator, blockTags, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new SignalsBlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new SignalsItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new SignalsLanguageProvider(generator, "en_us"));
    }
}