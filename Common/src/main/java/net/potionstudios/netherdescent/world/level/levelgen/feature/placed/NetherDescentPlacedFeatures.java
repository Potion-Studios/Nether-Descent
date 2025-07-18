package net.potionstudios.netherdescent.world.level.levelgen.feature.placed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.NetherDescentConfiguredFeatures;

import java.util.List;

public class NetherDescentPlacedFeatures {

    public static final ResourceKey<PlacedFeature> EMBUR_SPROUT = PlacedFeaturesUtil.createPlacedFeature("embur_sprout", NetherDescentConfiguredFeatures.EMBUR_SPROUT, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));
    public static final ResourceKey<PlacedFeature> EMBUR_LILY = PlacedFeaturesUtil.createPlacedFeature("embur_lily", NetherDescentConfiguredFeatures.EMBUR_LILY, () -> List.of(CountOnEveryLayerPlacement.of(4), BiomeFilter.biome()));

    public static void placedFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Placed Features");
    }
}
