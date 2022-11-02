package com.wingmann.signals.datagen;

import com.wingmann.signals.setup.Registration;
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
        ShapedRecipeBuilder.shaped(Registration.CIRCUIT_BOARD.get())
                .pattern("g g")
                .pattern("cgc")
                .pattern("iri")
                .define('g', Items.GOLD_NUGGET)
                .define('c', Tags.Items.INGOTS_COPPER)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .group("signals")
                .unlockedBy("gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
                .save(consumer);
        ShapedRecipeBuilder.shaped(Registration.SATELLITE_DISH_FRAME.get(), 4)
                .pattern("IiI")
                .pattern("iCi")
                .pattern("IiI")
                .define('C', Registration.CIRCUIT_BOARD.get())
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .group("signals")
                .unlockedBy("circuit_board", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.CIRCUIT_BOARD.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(Registration.SATELLITE_DISH_SUPPORT.get(), 4)
                .pattern("IbI")
                .pattern("b b")
                .pattern("IbI")
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('b', Items.IRON_BARS)
                .group("signals")
                .unlockedBy("iron_bars", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BARS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(Registration.SATELLITE_ANTENNA.get(), 2)
                .pattern("bIb")
                .pattern("ICI")
                .pattern("bIb")
                .define('C', Registration.CIRCUIT_BOARD.get())
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('b', Tags.Items.DYES_BLACK)
                .group("signals")
                .unlockedBy("circuit_board", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.CIRCUIT_BOARD.get()))
                .save(consumer);
    }
}
