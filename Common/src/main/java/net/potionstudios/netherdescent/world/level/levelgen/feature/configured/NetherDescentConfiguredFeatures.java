package net.potionstudios.netherdescent.world.level.levelgen.feature.configured;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.CrimsonBerryBushBlock;
import net.potionstudios.netherdescent.world.level.levelgen.feature.NetherDescentFeatures;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.PlacedFeaturesUtil;

import java.util.List;
import java.util.function.Supplier;

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

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_CAVE_MOSS = ConfiguredFeaturesUtil.createConfiguredFeature("embur_cave_moss", Feature.MULTIFACE_GROWTH, (configuredFeatureBootstrapContext) -> new MultifaceGrowthConfiguration(NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), 16, true, true, true, 1F, HolderSet.direct(Block::builtInRegistryHolder, NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.EMBUR_NYLIUM.get(), NetherDescentBlocks.EMBUR.logstem())));

	public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_EMBUR_MOSS = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_vines_feature", NetherDescentFeatures.CEILING_HANGING_VINES, () -> new CeilingHangingVinesFeatureConfiguration(NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get().defaultBlockState(), NetherDescentBlocks.EMBUR_HANGING_MOSS.get().defaultBlockState(), 0.75F, 25, 100));

    //public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_MOSS_PATCH_BONEMEAL = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock();

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_SPROUT = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("sythian_sprout", NetherDescentBlocks.SYTHIAN_SPROUTS, 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_CRIMSON_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("tall_crimson_roots", NetherDescentBlocks.TALL_CRIMSON_ROOTS, 15);

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CRIMSON_BERRY = createPatchConfiguredFeatureState("crimson_berry", () -> NetherDescentBlocks.CRIMSON_BERRY_BUSH.get().defaultBlockState().setValue(CrimsonBerryBushBlock.AGE, 3), 32);

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_GARDEN_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_garden_vegetation", Feature.RANDOM_SELECTOR, (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(NetherFeatures.PATCH_CRIMSON_ROOTS)), 0.45F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(TALL_CRIMSON_ROOTS)), 0.8F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(PATCH_CRIMSON_BERRY)));
            }
    );

	private static ResourceKey<ConfiguredFeature<?, ?>> createPatchConfiguredFeatureState(String id, Supplier<? extends BlockState> state, int tries) {
		return ConfiguredFeaturesUtil.createConfiguredFeature(id, Feature.RANDOM_PATCH, () -> FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state.get())), List.of(), tries));
	}

    public static void configuredFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Configured Features");
        NetherDescentTreeConfiguredFeatures.treeConfiguredFeatures();
    }
}
