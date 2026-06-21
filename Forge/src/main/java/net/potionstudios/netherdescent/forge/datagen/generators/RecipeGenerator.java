package net.potionstudios.netherdescent.forge.datagen.generators;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput output) {
        super(output);
    }

    private static final ImmutableList<ItemLike> PENDORITE_SMELTABLES = ImmutableList.of(NetherDescentBlocks.PENDORITE_ORE.get(), NetherDescentItems.RAW_PENDORITE.get());

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        NetherDescentBlockSet.getBlockSets().forEach(blockSet -> generateRecipes(writer, blockSet.getBlockFamily()));
        NetherDescentWoodSet.woodsets().forEach(set -> {
            planksFromLog(writer, set.planks(), set.logItemTag(), 4);
            woodFromLogs(writer, set.wood(), set.logstem());
            woodFromLogs(writer, set.strippedWood(), set.strippedLogStem());
            set.makeFamily();

            generateRecipes(writer, set.family());
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.bookshelf())
                    .define('#', set.planks())
                    .define('X', Items.BOOK)
                    .pattern("###")
                    .pattern("XXX")
                    .pattern("###")
                    .group("planks")
                    .unlockedBy(getHasName(set.planks()), has(set.planks()))
                    .save(writer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, set.craftingTable())
                    .define('#', set.planks())
                    .pattern("##")
                    .pattern("##")
                    .group("planks")
                    .unlockedBy(getHasName(set.planks()), has(set.planks()))
                    .save(writer);
            hangingSign(writer, set.hangingSignItem(), set.strippedLogStem());
        });

        oneToOneConversionRecipe(writer, Items.ORANGE_DYE, NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), "orange_dye");

        twoByTwoPacker(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase(), NetherDescentItems.BLUE_NETHER_BRICK.get());

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.BLUE_NETHERRACK.get()), RecipeCategory.MISC, NetherDescentItems.BLUE_NETHER_BRICK.get(), 0.1F, 200)
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHERRACK.get()), has(NetherDescentBlocks.BLUE_NETHERRACK.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), 6)
                .define('W', NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase())
                .define('#', NetherDescentItems.BLUE_NETHER_BRICK.get())
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CHISELED_BLUE_NETHER_BRICKS.get())
                .define('#', NetherDescentBlocks.BLUE_NETHER_BRICKS.getSlab())
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getSlab()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getSlab()))
                .save(writer);

        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CHISELED_BLUE_NETHER_BRICKS.get(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase(), 1);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CRACKED_BLUE_NETHER_BRICKS.get().asItem(), 0.1F, 200)
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()), has(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()))
                .save(writer);

        NDNineBlockStorageRecipes(writer, RecipeCategory.MISC, NetherDescentItems.EMBUR_GEL_BALL.get(), RecipeCategory.MISC, NetherDescentBlocks.EMBUR_GEL_BLOCK.get(), getSimpleRecipeName(NetherDescentBlocks.EMBUR_GEL_BLOCK.get()), null, getSimpleRecipeName(NetherDescentItems.EMBUR_GEL_BALL.get()), null);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentItems.SYTHIAN_SCAFFOLDING.get(), 6)
                .define('~', Items.STRING)
                .define('I', NetherDescentBlocks.SYTHIAN_STALK.getItem())
                .pattern("I~I")
                .pattern("I I")
                .pattern("I I")
                .unlockedBy(getHasName(NetherDescentBlocks.SYTHIAN_STALK.getItem()), has(NetherDescentBlocks.SYTHIAN_STALK.getItem()))
                .save(writer);

        twoByTwoPacker(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.EMBUR_MOSS_BLOCK.get(), NetherDescentBlocks.EMBUR_CAVE_MOSS.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NetherDescentItems.CRIMSON_BERRY_PIE.get())
                .requires(NetherDescentItems.CRIMSON_BERRIES.get())
                .requires(Items.SUGAR)
                .requires(Ingredient.merge(Arrays.asList(
                                Ingredient.of(Items.EGG),
                                Ingredient.of(Tags.Items.EGGS)
                        )))
                .unlockedBy(getHasName(NetherDescentItems.CRIMSON_BERRIES.get()), has(NetherDescentItems.CRIMSON_BERRIES.get()))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NetherDescentBlocks.TALL_CRIMSON_FUNGI.get())
                .requires(Items.CRIMSON_FUNGUS, 2)
                .unlockedBy(getHasName(Items.CRIMSON_FUNGUS), has(Items.CRIMSON_FUNGUS))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CRIMSON_FUNGUS, 2)
                .requires(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get())
                .unlockedBy(getHasName(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get()), has(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get()))
                .save(writer);

        oreSmelting(writer, ImmutableList.of(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200, "gold_ingot");
        oreBlasting(writer, ImmutableList.of(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100, "gold_ingot");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 200)
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()), has(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()))
                .save(writer);
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 100)
                .unlockedBy(getHasName(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()), has(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get()))
                .save(writer, getBlastingRecipeName(Items.QUARTZ));

        nineBlockStorageRecipesWithCustomPacking(writer, RecipeCategory.MISC, NetherDescentItems.PENDORITE_NUGGET.get(), RecipeCategory.MISC, NetherDescentItems.PENDORITE_INGOT.get(), "pendorite_ingot_from_nuggets", "pendorite_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(writer, RecipeCategory.MISC, NetherDescentItems.PENDORITE_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.PENDORITE_BLOCK.get(), "pendorite_ingot_from_pendorite_block", "pendorite_ingot");
        oreSmelting(writer, PENDORITE_SMELTABLES, RecipeCategory.MISC, NetherDescentItems.PENDORITE_INGOT.get(), 0.7F, 200, "pendorite_ingot");
        oreBlasting(writer, PENDORITE_SMELTABLES, RecipeCategory.MISC, NetherDescentItems.PENDORITE_INGOT.get(), 0.7F, 100, "pendorite_ingot");

        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CUT_PENDORITE.get(), NetherDescentBlocks.PENDORITE_BLOCK.get(), 4);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CHISELED_PENDORITE.get(), NetherDescentBlocks.PENDORITE_BLOCK.get(), 4);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CHISELED_PENDORITE.get(), NetherDescentBlocks.CUT_PENDORITE.get(), 1);

        nineBlockStorageRecipes(writer, RecipeCategory.MISC, NetherDescentItems.RAW_PENDORITE.get(), RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.RAW_PENDORITE_BLOCK.get());

        doorBuilder(NetherDescentBlocks.PENDORITE_DOOR.get(), Ingredient.of(NetherDescentItems.PENDORITE_INGOT.get())).unlockedBy(getHasName(NetherDescentItems.PENDORITE_INGOT.get()), has(NetherDescentItems.PENDORITE_INGOT.get())).save(writer);
        trapdoorBuilder(NetherDescentBlocks.PENDORITE_TRAPDOOR.get(), Ingredient.of(NetherDescentItems.PENDORITE_INGOT.get())).unlockedBy(getHasName(NetherDescentItems.PENDORITE_INGOT.get()), has(NetherDescentItems.PENDORITE_INGOT.get())).save(writer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentBlocks.SYTHIAN_STALK.getItem()), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 400)
                .unlockedBy(getHasName(NetherDescentBlocks.SYTHIAN_STALK.getItem()), has(NetherDescentBlocks.SYTHIAN_STALK.getItem()))
                .save(writer, NetherDescent.id("gold_nugget_from_smelting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(NetherDescentBlocks.SYTHIAN_STALK.getItem()), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 200)
                .unlockedBy(getHasName(NetherDescentBlocks.SYTHIAN_STALK.getItem()), has(NetherDescentBlocks.SYTHIAN_STALK.getItem()))
                .save(writer, NetherDescent.id("gold_nugget_from_blasting"));

        stairBuilder(NetherDescentBlocks.CUT_PENDORITE_STAIRS.get(), Ingredient.of(NetherDescentBlocks.CUT_PENDORITE.get()))
                .unlockedBy(getHasName(NetherDescentBlocks.CUT_PENDORITE.get()), has(NetherDescentBlocks.CUT_PENDORITE.get()))
                .save(writer);

        slab(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CUT_PENDORITE_SLAB.get(), NetherDescentBlocks.CUT_PENDORITE.get());

        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CUT_PENDORITE_STAIRS.get(), NetherDescentBlocks.CUT_PENDORITE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CUT_PENDORITE_STAIRS.get(), NetherDescentBlocks.PENDORITE_BLOCK.get(), 4);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CUT_PENDORITE_SLAB.get(), NetherDescentBlocks.CUT_PENDORITE.get(),2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, NetherDescentBlocks.CUT_PENDORITE_SLAB.get(), NetherDescentBlocks.PENDORITE_BLOCK.get(), 8);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.PENDORITE_CAMPFIRE.get())
                .define('L', ItemTags.LOGS)
                .define('S', Items.STICK)
                .define('#', NetherDescentItems.PENDORITE_INGOT.get())
                .pattern(" S ")
                .pattern("S#S")
                .pattern("LLL")
                .unlockedBy(getHasName(NetherDescentItems.PENDORITE_INGOT.get()), has(NetherDescentItems.PENDORITE_INGOT.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.PENDORITE_BARS.get(), 16)
                .define('#', NetherDescentItems.PENDORITE_INGOT.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(NetherDescentItems.PENDORITE_INGOT.get()), has(NetherDescentItems.PENDORITE_INGOT.get()))
                .save(writer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()), RecipeCategory.MISC, NetherDescentItems.PENDORITE_NUGGET.get(), 0.1F, 200).unlockedBy(getHasName(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()), has(NetherDescentItems.PENDORITE_HORSE_ARMOR.get())).save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.PENDORITE_TORCH.get(), 4)
                .define('X', Ingredient.of(Items.COAL, Items.CHARCOAL))
                .define('#', Items.STICK)
                .define('S', NetherDescentItems.PENDORITE_NUGGET.get())
                .pattern("X")
                .pattern("#")
                .pattern("S")
                .unlockedBy(getHasName(NetherDescentItems.PENDORITE_NUGGET.get()), has(NetherDescentItems.PENDORITE_NUGGET.get()))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.PENDORITE_LANTERN.get())
                .define('#', NetherDescentItems.PENDORITE_TORCH.get())
                .define('X', NetherDescentItems.PENDORITE_NUGGET.get())
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .unlockedBy(getHasName(NetherDescentItems.PENDORITE_NUGGET.get()), has(NetherDescentItems.PENDORITE_NUGGET.get()))
                .unlockedBy(getHasName(NetherDescentItems.PENDORITE_INGOT.get()), has(NetherDescentItems.PENDORITE_INGOT.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.SOUL_FIRE_ROD.get(), 4)
                .define('X', NetherDescentItems.SOUL_BLAZE_ROD.get())
                .define('Y', Items.BLACKSTONE)
                .pattern("X")
                .pattern("Y")
                .unlockedBy(getHasName(NetherDescentItems.SOUL_BLAZE_ROD.get()), has(NetherDescentItems.SOUL_BLAZE_ROD.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.BLAZE_FIRE_ROD.get(), 4)
                .define('X', Items.BLAZE_ROD)
                .define('Y', Items.BLACKSTONE)
                .pattern("X")
                .pattern("Y")
                .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NetherDescentBlocks.PENDORITE_FIRE_ROD.get())
                .define('X', NetherDescentItems.PENDORITE_NUGGET.get())
                .define('Y', NetherDescentBlocks.SOUL_FIRE_ROD.get())
                .pattern("X")
                .pattern("Y")
                .unlockedBy(getHasName(NetherDescentBlocks.SOUL_FIRE_ROD.get()), has(NetherDescentBlocks.SOUL_FIRE_ROD.get()))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, NetherDescentItems.SOUL_BLAZE_POWDER.get(), 2)
                .requires(NetherDescentItems.SOUL_BLAZE_ROD.get())
                .unlockedBy(getHasName(NetherDescentItems.SOUL_BLAZE_ROD.get()), has(NetherDescentItems.SOUL_BLAZE_ROD.get()))
                .save(writer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NetherDescentItems.SOUL_FIRE_CHARGE.get(), 3)
                .requires(Items.GUNPOWDER)
                .requires(NetherDescentItems.SOUL_BLAZE_POWDER.get())
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy(getHasName(NetherDescentItems.SOUL_BLAZE_POWDER.get()), has(NetherDescentItems.SOUL_BLAZE_POWDER.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NetherDescentBlocks.HORNET_NEST.get())
                .define('#', ItemTags.LOGS)
                .define('X', Items.WATER_BUCKET)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .save(writer);
    }


    private static void NDNineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> writer, RecipeCategory unpackedCategory, ItemLike unpacked, RecipeCategory packedCategory, ItemLike packed, String packedName, @Nullable String packedGroup, String unpackedName, @Nullable String unpackedGroup) {
        ShapelessRecipeBuilder.shapeless(unpackedCategory, unpacked, 9)
                .requires(packed)
                .group(unpackedGroup)
                .unlockedBy(getHasName(packed), has(packed))
                .save(writer, NetherDescent.id(unpackedName));
        ShapedRecipeBuilder.shaped(packedCategory, packed)
                .define('#', unpacked)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(packedGroup)
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(writer, NetherDescent.id(packedName));
    }

    protected static void stonecutterResultFromBase(@NotNull Consumer<FinishedRecipe> writer, @NotNull RecipeCategory category, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(writer, NetherDescent.id(getConversionRecipeName(result, material) + "_stonecutting"));
    }
}
