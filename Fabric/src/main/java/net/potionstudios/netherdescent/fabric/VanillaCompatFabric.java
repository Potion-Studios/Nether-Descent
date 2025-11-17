package net.potionstudios.netherdescent.fabric;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;

public class VanillaCompatFabric {

    public static void init() {
        BlockItemFeatures.registerCompostables(CompostingChanceRegistry.INSTANCE::add);
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> NetherDescentBrewingRecipes.buildBrewingRecipes(builder::addMix));
        ToolInteractions.registerTillables((block, pair) -> TillableBlockRegistry.register(block, pair.getFirst(), pair.getSecond()));
        BlockItemFeatures.registerFurnaceFuels(FuelRegistry.INSTANCE::add);
    }
}
