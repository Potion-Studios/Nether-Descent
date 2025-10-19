package net.potionstudios.netherdescent.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
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
import net.potionstudios.netherdescent.world.level.levelgen.feature.NetherDescentFeature;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CarpetPatchFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;
import net.potionstudios.netherdescent.data.worldgen.placement.PlacedFeaturesUtil;

import java.util.List;
import java.util.function.Supplier;

public class NetherDescentFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_SPROUT = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_sprout", NetherDescentBlocks.EMBUR_SPROUTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_LILY = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_lily", NetherDescentBlocks.EMBUR_LILY, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_roots", NetherDescentBlocks.EMBUR_ROOTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_EMBUR_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("tall_embur_roots", NetherDescentBlocks.TALL_EMBUR_ROOTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_wart", NetherDescentBlocks.EMBUR.growerItem().block(), 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_BOG_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("embur_bog_vegetation", Feature.RANDOM_SELECTOR, (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(TALL_EMBUR_ROOTS)), 0.35F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_ROOTS)), 0.35F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART)));
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_CAVE_MOSS = ConfiguredFeaturesUtil.createConfiguredFeature("embur_cave_moss", Feature.MULTIFACE_GROWTH, (configuredFeatureBootstrapContext) -> new MultifaceGrowthConfiguration(NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), 16, true, true, true, 1F, HolderSet.direct(Block::builtInRegistryHolder, NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.EMBUR_NYLIUM.get(), NetherDescentBlocks.EMBUR.logstem())));

	public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_EMBUR_MOSS = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_vines_feature", NetherDescentFeature.CEILING_HANGING_VINES, () -> new CeilingHangingVinesFeatureConfiguration(NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get().defaultBlockState(), NetherDescentBlocks.EMBUR_HANGING_MOSS.get().defaultBlockState(), 0.75F, 10, 90));

    //public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_MOSS_PATCH_BONEMEAL = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock();
	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_MOSS_CARPET_PATCH = ConfiguredFeaturesUtil.createConfiguredFeature("embur_moss_carpet_patch", NetherDescentFeature.BLOCK_CARPET_PATCH, () -> new CarpetPatchFeatureConfiguration(NetherDescentBlocks.EMBUR_MOSS_BLOCK.get(), NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), BiasedToBottomInt.of(6, 12), 0.85F));

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_SPROUT = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("sythian_sprout", NetherDescentBlocks.SYTHIAN_SPROUTS, 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("sythian_roots", NetherDescentBlocks.SYTHIAN_ROOTS, 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_TORRIDS_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_torrids_vegetation", Feature.RANDOM_SELECTOR, (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_SPROUT)), 0.5F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_ROOTS)));
            }
    );

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

    public static void features() {
        NetherDescent.LOGGER.info("Registering Nether Descent Features");
        NetherDescentTreeFeatures.treeFeatures();
        NetherDescentOreFeatures.oreFeatures();
    }
}
