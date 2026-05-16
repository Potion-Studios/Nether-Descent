package net.potionstudios.netherdescent.data.worldgen.placement;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentTreeFeatures;

import java.util.List;

public class NetherDescentTreePlacements {
	public static final ResourceKey<PlacedFeature> ARISIAN_TREES = PlacedFeaturesUtil.createPlacedFeature("arisian_trees", NetherDescentTreeFeatures.ARISIAN_TREES, () -> List.of(CountOnEveryLayerPlacement.of(5), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> ARISIAN_TREES_HANGING = PlacedFeaturesUtil.createPlacedFeature("arisian_trees_hanging", NetherDescentTreeFeatures.HANGING_ARISIAN_TREES, () -> List.of(CountOnEveryLayerPlacement.of(15), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> ARISIAN_ROOTS_HANGING = PlacedFeaturesUtil.createPlacedFeature("arisian_roots_hanging", NetherDescentTreeFeatures.HANGING_ARISIAN_ROOTS, () -> List.of(CountOnEveryLayerPlacement.of(15), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> EMBUR_WARTS = PlacedFeaturesUtil.createPlacedFeature("embur_warts", NetherDescentTreeFeatures.EMBUR_WARTS, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> SYTHIAN_FUNGI_TREES = PlacedFeaturesUtil.createPlacedFeature("sythian_fungi_trees", NetherDescentTreeFeatures.SYTHIAN_FUNGI_TREES, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> CRIMSON_FUNGI_TREES_HANGING = PlacedFeaturesUtil.createPlacedFeature("crimson_fungi_trees_hanging", NetherDescentTreeFeatures.CRIMSON_FUNGI_TREES_HANGING, () -> List.of(CountOnEveryLayerPlacement.of(3), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> CRIMSON_FUNGI_TREES = PlacedFeaturesUtil.createPlacedFeature("crimson_fungi_trees", NetherDescentTreeFeatures.CRIMSON_FUNGI_TREES, () -> List.of(CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> BONE_TREES = PlacedFeaturesUtil.createPlacedFeature("bone_trees", NetherDescentTreeFeatures.BONE_TREES, () -> List.of(CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> HANGING_BONE_TREES = PlacedFeaturesUtil.createPlacedFeature("hanging_bone_trees", NetherDescentTreeFeatures.HANGING_BONE_TREES, () -> List.of(CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> WAILING_FUNGI_TREES = PlacedFeaturesUtil.createPlacedFeature("wailing_fungi_trees", NetherDescentTreeFeatures.WAILING_FUNGI_TREES, () -> List.of(CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> WAILING_CAGES = PlacedFeaturesUtil.createPlacedFeature("wailing_cages", NetherDescentTreeFeatures.WAILING_CAGES, () -> List.of(CountOnEveryLayerPlacement.of(2), BiomeFilter.biome()));

	protected static void treePlacements() {
        NetherDescent.LOGGER.info("Registering Nether Descent Tree Placements");
	}
}
