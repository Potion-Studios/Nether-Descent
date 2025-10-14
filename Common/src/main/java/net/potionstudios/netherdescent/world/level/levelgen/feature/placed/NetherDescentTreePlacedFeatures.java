package net.potionstudios.netherdescent.world.level.levelgen.feature.placed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.NetherDescentTreeConfiguredFeatures;

import java.util.List;

public class NetherDescentTreePlacedFeatures {

	public static final ResourceKey<PlacedFeature> EMBUR_WARTS = PlacedFeaturesUtil.createPlacedFeature("embur_warts", NetherDescentTreeConfiguredFeatures.EMBUR_WARTS, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> SYTHIAN_FUNGI_TREES = PlacedFeaturesUtil.createPlacedFeature("sythian_fungi_trees", NetherDescentTreeConfiguredFeatures.SYTHIAN_FUNGI_TREES, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> CRIMSON_FUNGI_TREES_HANGING = PlacedFeaturesUtil.createPlacedFeature("crimson_fungi_trees_hanging", NetherDescentTreeConfiguredFeatures.CRIMSON_FUNGI_TREES_HANGING, () -> List.of(CountOnEveryLayerPlacement.of(3), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> CRIMSON_FUNGI_TREES = PlacedFeaturesUtil.createPlacedFeature("crimson_fungi_trees", NetherDescentTreeConfiguredFeatures.CRIMSON_FUNGI_TREES, () -> List.of(CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));

	public static void treePlacedFeatures() {
		NetherDescentTreeConfiguredFeatures.treeConfiguredFeatures();
	}

}
