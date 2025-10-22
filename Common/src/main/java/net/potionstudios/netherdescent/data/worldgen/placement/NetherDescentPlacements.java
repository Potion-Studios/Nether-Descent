package net.potionstudios.netherdescent.data.worldgen.placement;

import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentFeatures;

import java.util.List;

public class NetherDescentPlacements {

    public static final ResourceKey<PlacedFeature> EMBUR_SPROUT = PlacedFeaturesUtil.createPlacedFeature("embur_sprout", NetherDescentFeatures.EMBUR_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_LILY = PlacedFeaturesUtil.createPlacedFeature("embur_lily", NetherDescentFeatures.EMBUR_LILY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_BOG_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("embur_bog_vegetation", NetherDescentFeatures.EMBUR_BOG_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_CAVE_MOSS = PlacedFeaturesUtil.createPlacedFeature("embur_cave_moss", NetherDescentFeatures.EMBUR_CAVE_MOSS, () -> List.of(CountPlacement.of(256), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> HANGING_EMBUR_MOSS = PlacedFeaturesUtil.createPlacedFeature("hanging_embur_moss", NetherDescentFeatures.HANGING_EMBUR_MOSS, () -> List.of(CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> EMBUR_MOSS_CARPET_PATCH = PlacedFeaturesUtil.createPlacedFeature("embur_moss_carpet_patch", NetherDescentFeatures.EMBUR_MOSS_CARPET_PATCH, () -> List.of(RarityFilter.onAverageOnceEvery(3), CountOnEveryLayerPlacement.of(2), InSquarePlacement.spread(), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(NetherDescentBlocks.EMBUR_NYLIUM.get()), 16), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> SYTHIAN_SPROUT = PlacedFeaturesUtil.createPlacedFeature("sythian_sprout", NetherDescentFeatures.SYTHIAN_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> SYTHIAN_TORRIDS_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("sythian_torrids_vegetation", NetherDescentFeatures.SYTHIAN_TORRIDS_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> TALL_CRIMSON_ROOTS = PlacedFeaturesUtil.createPlacedFeature("tall_crimson_roots", NetherDescentFeatures.TALL_CRIMSON_ROOTS, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> PATCH_CRIMSON_BERRY = PlacedFeaturesUtil.createPlacedFeature("crimson_berry", NetherDescentFeatures.PATCH_CRIMSON_BERRY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> CRIMSON_GARDEN_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("crimson_garden_vegetation", NetherDescentFeatures.CRIMSON_GARDEN_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

	public static final ResourceKey<PlacedFeature> HANGING_SYTHIAN_ROOTS = PlacedFeaturesUtil.createPlacedFeature("hanging_sythian_roots", NetherDescentFeatures.HANGING_SYTHIAN_ROOTS, () -> List.of(CountPlacement.of(256), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));

    public static void placements() {
        NetherDescent.LOGGER.info("Registering Nether Descent Placements");
        NetherDescentTreePlacements.treePlacements();
        NetherDescentOrePlacements.orePlacements();
    }
}
