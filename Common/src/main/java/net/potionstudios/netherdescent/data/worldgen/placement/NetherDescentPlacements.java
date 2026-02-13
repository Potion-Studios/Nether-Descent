package net.potionstudios.netherdescent.data.worldgen.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentBlockTags;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentFeatures;
import net.potionstudios.netherdescent.world.level.block.plants.SythianStalkBlock;

import java.util.List;

public class NetherDescentPlacements {

    public static final ResourceKey<PlacedFeature> EMBUR_SPROUT = PlacedFeaturesUtil.createPlacedFeature("embur_sprout", NetherDescentFeatures.EMBUR_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_LILY = PlacedFeaturesUtil.createPlacedFeature("embur_lily", NetherDescentFeatures.EMBUR_LILY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_BOG_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("embur_bog_vegetation", NetherDescentFeatures.EMBUR_BOG_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_CAVE_MOSS = PlacedFeaturesUtil.createPlacedFeature("embur_cave_moss", NetherDescentFeatures.EMBUR_CAVE_MOSS, () -> List.of(CountPlacement.of(256), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> HANGING_EMBUR_MOSS = PlacedFeaturesUtil.createPlacedFeature("hanging_embur_moss", NetherDescentFeatures.HANGING_EMBUR_MOSS, () -> List.of(CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> EMBUR_MOSS_CARPET_PATCH = PlacedFeaturesUtil.createPlacedFeature("embur_moss_carpet_patch", NetherDescentFeatures.EMBUR_MOSS_CARPET_PATCH, () -> List.of(
            CountPlacement.of(256),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_10_10,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(
                BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get()),
                BlockPredicate.not(BlockPredicate.solid())
            ), 12),
            BiomeFilter.biome()));

	public static final ResourceKey<PlacedFeature> SYTHIAN_TORRIDS_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("sythian_torrids_vegetation", NetherDescentFeatures.SYTHIAN_TORRIDS_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(6), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> TALL_CRIMSON_ROOTS = PlacedFeaturesUtil.createPlacedFeature("tall_crimson_roots", NetherDescentFeatures.TALL_CRIMSON_ROOTS, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> PATCH_CRIMSON_BERRY = PlacedFeaturesUtil.createPlacedFeature("crimson_berry", NetherDescentFeatures.PATCH_CRIMSON_BERRY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> CRIMSON_GARDEN_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("crimson_garden_vegetation", NetherDescentFeatures.CRIMSON_GARDEN_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(6), BiomeFilter.biome()));

	public static final ResourceKey<PlacedFeature> HANGING_SYTHIAN_ROOTS = PlacedFeaturesUtil.createPlacedFeature("hanging_sythian_roots", NetherDescentFeatures.HANGING_SYTHIAN_ROOTS, () -> List.of(CountPlacement.of(250), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> HANGING_ARISIAN_TANGLE_ROOTS = PlacedFeaturesUtil.createPlacedFeature("hanging_arisian_tangle_roots", NetherDescentFeatures.HANGING_ARISIAN_TANGLE_ROOTS, () -> List.of(CountPlacement.of(250), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));


    public static final ResourceKey<PlacedFeature> SYTHIAN_STALKS = PlacedFeaturesUtil.createPlacedFeature("sythian_stalks", NetherDescentFeatures.SYTHIAN_STALK, () -> List.of(NoiseBasedCountPlacement.of(160, 80.0, 0.3), InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(
            BlockPredicate.matchesTag(Direction.DOWN.getNormal(), NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON),
            BlockPredicate.not(BlockPredicate.solid())
    ), 12), PlacementUtils.filteredByBlockSurvival(NetherDescentBlocks.SYTHIAN_STALK.getBlock()), BiomeFilter.biome()));

	public static final ResourceKey<PlacedFeature> SYTHIAN_STALKS_DOWNWARD = PlacedFeaturesUtil.createPlacedFeature("sythian_stalks_downward", NetherDescentFeatures.SYTHIAN_STALK_DOWNWARD, () -> List.of(NoiseBasedCountPlacement.of(160, 80.0, 0.3), InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(
			BlockPredicate.matchesTag(Direction.UP.getNormal(), NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON),
			BlockPredicate.not(BlockPredicate.solid())
	), 12), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.HANGING, true), BlockPos.ZERO)), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> WAILING_GARTH_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("wailing_garth_vegetation", NetherDescentFeatures.WAILING_GARTH_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(6), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> WAILING_BULB_BLOSSOM = PlacedFeaturesUtil.createPlacedFeature("wailing_bulb_blossom", NetherDescentFeatures.WAILING_BULB_BLOSSOM, () -> List.of(CountPlacement.of(10), InSquarePlacement.spread(), PlacementUtils.RANGE_8_8,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.allOf(
                    BlockPredicate.matchesBlocks(Direction.UP.getNormal(), Blocks.SOUL_SOIL),
                    BlockPredicate.ONLY_IN_AIR_PREDICATE
            ), 12), BiomeFilter.biome()));

    public static void placements() {
        NetherDescent.LOGGER.info("Registering Nether Descent Placements");
        NetherDescentTreePlacements.treePlacements();
        NetherDescentOrePlacements.orePlacements();
    }
}
