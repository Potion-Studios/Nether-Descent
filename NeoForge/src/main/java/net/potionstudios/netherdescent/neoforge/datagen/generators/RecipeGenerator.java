package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
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
    }
}
