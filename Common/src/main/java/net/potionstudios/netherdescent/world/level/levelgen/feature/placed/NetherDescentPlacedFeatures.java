package net.potionstudios.netherdescent.world.level.levelgen.feature.placed;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.placement.*;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.NetherDescentConfiguredFeatures;

import java.util.List;

public class NetherDescentPlacedFeatures {

    public static final ResourceKey<PlacedFeature> EMBUR_SPROUT = PlacedFeaturesUtil.createPlacedFeature("embur_sprout", NetherDescentConfiguredFeatures.EMBUR_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_LILY = PlacedFeaturesUtil.createPlacedFeature("embur_lily", NetherDescentConfiguredFeatures.EMBUR_LILY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_BOG_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("embur_bog_vegetation", NetherDescentConfiguredFeatures.EMBUR_BOG_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_CAVE_MOSS = PlacedFeaturesUtil.createPlacedFeature("embur_cave_moss", NetherDescentConfiguredFeatures.EMBUR_CAVE_MOSS, () -> List.of(CountPlacement.of(ConstantInt.of(256)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), BiomeFilter.biome()));
	public static final ResourceKey<PlacedFeature> HANGING_EMBUR_MOSS = PlacedFeaturesUtil.createPlacedFeature("hanging_embur_moss", NetherDescentConfiguredFeatures.HANGING_EMBUR_MOSS, () -> List.of(CountPlacement.of(25), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> SYTHIAN_SPROUT = PlacedFeaturesUtil.createPlacedFeature("sythian_sprout", NetherDescentConfiguredFeatures.SYTHIAN_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> SYTHIAN_TORRIDS_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("sythian_torrids_vegetation", NetherDescentConfiguredFeatures.SYTHIAN_TORRIDS_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(8), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> TALL_CRIMSON_ROOTS = PlacedFeaturesUtil.createPlacedFeature("tall_crimson_roots", NetherDescentConfiguredFeatures.TALL_CRIMSON_ROOTS, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> PATCH_CRIMSON_BERRY = PlacedFeaturesUtil.createPlacedFeature("crimson_berry", NetherDescentConfiguredFeatures.PATCH_CRIMSON_BERRY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> CRIMSON_GARDEN_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("crimson_garden_vegetation", NetherDescentConfiguredFeatures.CRIMSON_GARDEN_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    //public static final ResourceKey<PlacedFeature> ORE_BLUE_GOLD_DELTAS = PlacedFeaturesUtil.createPlacedFeature("ore_blue_gold_deltas", NetherDescentConfiguredFeatures.ORE_BLUE_QUARTZ, () -> commonOrePlacement(20, PlacementUtils.RANGE_10_10));
    //public static final ResourceKey<PlacedFeature> ORE_BLUE_QUARTZ_DELTAS = PlacedFeaturesUtil.createPlacedFeature("ore_blue_quartz_deltas", NetherDescentConfiguredFeatures.ORE_BLUE_QUARTZ, () -> commonOrePlacement(32, PlacementUtils.RANGE_10_10));
    public static final ResourceKey<PlacedFeature> ORE_BLUE_GOLD_NETHER = PlacedFeaturesUtil.createPlacedFeature("ore_blue_gold_nether", NetherDescentConfiguredFeatures.ORE_BLUE_NETHER_GOLD, () -> commonOrePlacement(10, PlacementUtils.RANGE_10_10));
    public static final ResourceKey<PlacedFeature> ORE_BLUE_QUARTZ_NETHER = PlacedFeaturesUtil.createPlacedFeature("ore_blue_quartz_nether", NetherDescentConfiguredFeatures.ORE_BLUE_QUARTZ, () -> commonOrePlacement(16, PlacementUtils.RANGE_10_10));

    public static void placedFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Placed Features");
        NetherDescentTreePlacedFeatures.treePlacedFeatures();
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }
}
