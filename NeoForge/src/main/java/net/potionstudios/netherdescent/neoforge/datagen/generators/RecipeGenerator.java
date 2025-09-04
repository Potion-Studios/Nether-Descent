package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        NetherDescentBlockSet.getBlockSets().forEach(blockSet -> {
            generateRecipes(recipeOutput, blockSet.getBlockFamily(), FeatureFlags.VANILLA_SET);
        });
        NetherDescentWoodSet.woodsets().forEach(set -> {
            planksFromLog(recipeOutput, set.planks(), set.logItemTag(), 4);
            woodFromLogs(recipeOutput, set.wood(), set.logstem());
            woodFromLogs(recipeOutput, set.strippedWood(), set.strippedLogStem());
            set.makeFamily();

            generateRecipes(recipeOutput, set.family(), FeatureFlags.VANILLA_SET);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.bookshelf())
                    .define('#', set.planks())
                    .define('X', Items.BOOK)
                    .pattern("###")
                    .pattern("XXX")
                    .pattern("###")
                    .group("planks")
                    .unlockedBy(getHasName(set.planks()), has(set.planks()))
                    .save(recipeOutput);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, set.craftingTable())
                    .define('#', set.planks())
                    .pattern("##")
                    .pattern("##")
                    .group("planks")
                    .unlockedBy(getHasName(set.planks()), has(set.planks()))
                    .save(recipeOutput);
            hangingSign(recipeOutput, set.hangingSignItem(), set.strippedLogStem());
        });

        oneToOneConversionRecipe(recipeOutput, Items.ORANGE_DYE, NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), "orange_dye");

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), 6)
                .define('W', NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase())
                .define('#', NetherDescentItems.BLUE_NETHER_BRICK.get())
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()))
                .save(recipeOutput);

        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, NetherDescentItems.EMBUR_GEL_BALL.get(), RecipeCategory.MISC, NetherDescentBlocks.EMBUR_GEL_BLOCK.get().asItem());
    }
}
