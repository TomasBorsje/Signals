package com.example.examplemod.datagen;

import com.example.examplemod.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class SignalsRecipes extends RecipeProvider {
    public SignalsRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(Registration.TAPE_ITEM.get())
                .pattern("i i")
                .pattern("xxx")
                .pattern("ppp")
                .define('x', Items.DRIED_KELP)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('p', Items.PAPER)
                .group("signals")
                .unlockedBy("kelp", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DRIED_KELP))
                .save(consumer);
    }
}
