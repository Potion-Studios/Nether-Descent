package net.potionstudios.netherdescent.neoforge.datagen.generators;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        NetherDescentBlockSet.getBlockSets().forEach(blockSet -> generateRecipes(recipeOutput, blockSet.getBlockFamily(), FeatureFlags.VANILLA_SET));
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

		twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase(), NetherDescentItems.BLUE_NETHER_BRICK.get());

	    SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.BLUE_NETHERRACK.get()), RecipeCategory.MISC, NetherDescentItems.BLUE_NETHER_BRICK.get(), 0.1F, 200)
			    .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHERRACK.get()), has(NetherDescentBlocks.BLUE_NETHERRACK.get()))
			    .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), 6)
                .define('W', NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase())
                .define('#', NetherDescentItems.BLUE_NETHER_BRICK.get())
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()))
                .save(recipeOutput);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CHISELED_BLUE_NETHER_BRICKS.get())
						.define('#', NetherDescentBlocks.BLUE_NETHER_BRICKS.getSlab())
						.pattern("#")
						.pattern("#")
						.unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getSlab()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getSlab()))
						.save(recipeOutput);

		stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CHISELED_BLUE_NETHER_BRICKS.get(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase(), 1);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CRACKED_BLUE_NETHER_BRICKS.get().asItem(), 0.1F, 200)
						.unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()))
						.save(recipeOutput);

        NDNineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, NetherDescentItems.EMBUR_GEL_BALL.get(), RecipeCategory.MISC, NetherDescentBlocks.EMBUR_GEL_BLOCK.get(), getSimpleRecipeName(NetherDescentBlocks.EMBUR_GEL_BLOCK.get()), null, getSimpleRecipeName(NetherDescentItems.EMBUR_GEL_BALL.get()), null);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentItems.SYTHIAN_SCAFFOLDING.get(), 6)
                .define('~', Items.STRING)
                .define('I', NetherDescentBlocks.SYTHIAN.planks())
                .pattern("I~I")
                .pattern("I I")
                .pattern("I I")
                .unlockedBy(getHasName(NetherDescentBlocks.SYTHIAN.planks()), has(NetherDescentBlocks.SYTHIAN.planks()))
                .save(recipeOutput);

        twoByTwoPacker(recipeOutput, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.EMBUR_MOSS_BLOCK.get(), NetherDescentBlocks.EMBUR_CAVE_MOSS.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NetherDescentItems.CRIMSON_BERRY_PIE.get())
                .requires(NetherDescentItems.CRIMSON_BERRIES.get())
                .requires(Items.SUGAR)
                .requires(Tags.Items.EGGS)
                .unlockedBy(getHasName(NetherDescentItems.CRIMSON_BERRIES.get()), has(NetherDescentItems.CRIMSON_BERRIES.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NetherDescentBlocks.TALL_CRIMSON_FUNGI.get())
                .requires(Items.CRIMSON_FUNGUS, 2)
                .unlockedBy(getHasName(Items.CRIMSON_FUNGUS), has(Items.CRIMSON_FUNGUS))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CRIMSON_FUNGUS, 2)
                .requires(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get())
                .unlockedBy(getHasName(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get()), has(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get()))
                .save(recipeOutput);

        oreSmelting(recipeOutput, ImmutableList.of(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200, "gold_ingot");
        oreBlasting(recipeOutput, ImmutableList.of(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100, "gold_ingot");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 200)
                .unlockedBy("has_blue_nether_quartz_ore", has(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()))
                .save(recipeOutput);
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 100)
                .unlockedBy("has_blue_nether_quartz_ore", has(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()))
                .save(recipeOutput, getBlastingRecipeName(Items.QUARTZ));
    }

	private static void NDNineBlockStorageRecipes(RecipeOutput recipeOutput, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed, String packedName, @Nullable String packedGroup, String unpackedName, @Nullable String unpackedGroup) {
		ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9)
				.requires(packed)
				.group(unpackedGroup)
				.unlockedBy(getHasName(packed), has(packed))
				.save(recipeOutput, NetherDescent.id(unpackedName));
		ShapedRecipeBuilder.shaped(packedCategory, packed)
				.define('#', unpacked)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.group(packedGroup)
				.unlockedBy(getHasName(unpacked), has(unpacked))
				.save(recipeOutput, NetherDescent.id(packedName));
    }

	protected static void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
				.unlockedBy(getHasName(material), has(material))
				.save(recipeOutput, NetherDescent.id(getConversionRecipeName(result, material) + "_stonecutting"));
	}
}
