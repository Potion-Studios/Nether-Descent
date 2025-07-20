package net.potionstudios.netherdescent.world.level.levelgen.feature.configured;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.PlacedFeaturesUtil;

public class NetherDescentConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_SPROUT = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_sprout", NetherDescentBlocks.EMBUR_SPROUTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_LILY = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_lily", NetherDescentBlocks.EMBUR_LILY, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_roots", NetherDescentBlocks.EMBUR_ROOTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_EMBUR_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("tall_embur_roots", NetherDescentBlocks.TALL_EMBUR_ROOTS, 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_BOG_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("embur_bog_vegetation", Feature.RANDOM_SELECTOR, (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(TALL_EMBUR_ROOTS)), 0.333F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_ROOTS)), 0.333F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(TALL_EMBUR_ROOTS)));  // TODO: Change to Warts
            }
    );

    public static void configuredFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Configured Features");
    }
}
