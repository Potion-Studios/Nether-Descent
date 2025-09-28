package net.potionstudios.netherdescent.world.level.levelgen.feature.placed;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.*;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.NetherDescentConfiguredFeatures;

import java.util.List;

public class NetherDescentPlacedFeatures {

    public static final ResourceKey<PlacedFeature> EMBUR_SPROUT = PlacedFeaturesUtil.createPlacedFeature("embur_sprout", NetherDescentConfiguredFeatures.EMBUR_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_LILY = PlacedFeaturesUtil.createPlacedFeature("embur_lily", NetherDescentConfiguredFeatures.EMBUR_LILY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_BOG_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("embur_bog_vegetation", NetherDescentConfiguredFeatures.EMBUR_BOG_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_CAVE_MOSS = PlacedFeaturesUtil.createPlacedFeature("embur_cave_moss", NetherDescentConfiguredFeatures.EMBUR_CAVE_MOSS, () -> List.of(CountPlacement.of(UniformInt.of(200, 256)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> SYTHIAN_SPROUT = PlacedFeaturesUtil.createPlacedFeature("sythian_sprout", NetherDescentConfiguredFeatures.SYTHIAN_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> TALL_CRIMSON_ROOTS = PlacedFeaturesUtil.createPlacedFeature("tall_crimson_roots", NetherDescentConfiguredFeatures.TALL_CRIMSON_ROOTS, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> CRIMSON_BERRY_BUSH = PlacedFeaturesUtil.createPlacedFeature("crimson_berry_bush", NetherDescentConfiguredFeatures.CRIMSON_BERRY_BUSH, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    public static final ResourceKey<PlacedFeature> CRIMSON_GARDEN_VEGETATION = PlacedFeaturesUtil.createPlacedFeature("crimson_garden_vegetation", NetherDescentConfiguredFeatures.CRIMSON_GARDEN_VEGETATION, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));


    public static void placedFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Placed Features");
        NetherDescentTreePlacedFeatures.treePlacedFeatures();
    }
}
