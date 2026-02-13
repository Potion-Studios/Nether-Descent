package net.potionstudios.netherdescent.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.CrimsonBerryBushBlock;
import net.potionstudios.netherdescent.world.level.levelgen.feature.NetherDescentFeature;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CarpetPatchFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;
import net.potionstudios.netherdescent.data.worldgen.placement.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.FloatingBlockFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.HangingPlantFeatureConfiguration;

import java.util.List;
import java.util.function.Supplier;

public class NetherDescentFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_SPROUT = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_sprout", NetherDescentBlocks.EMBUR_SPROUTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_LILY = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_lily", NetherDescentBlocks.EMBUR_LILY, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_roots", NetherDescentBlocks.EMBUR_ROOTS.block(), 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_EMBUR_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("tall_embur_roots", NetherDescentBlocks.TALL_EMBUR_ROOTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_wart", NetherDescentBlocks.EMBUR.growerItem().block(), 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_BOG_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("embur_bog_vegetation", NetherDescentFeature.NETHER_FOREST_VEGETATION, () -> new NetherForestVegetationConfig(
            new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(NetherDescentBlocks.EMBUR_SPROUTS.get().defaultBlockState(), 2)
                            .add(NetherDescentBlocks.EMBUR_ROOTS.getBlockState(), 2)
                            .add(NetherDescentBlocks.TALL_EMBUR_ROOTS.get().defaultBlockState())
                            .add(NetherDescentBlocks.EMBUR.growerItem().getBlockState())
            ), 8, 6)
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_BOG_VEGETATION_BONEMEAL = ConfiguredFeaturesUtil.createConfiguredFeature("embur_bog_vegetation_bonemeal", NetherDescentFeature.NETHER_FOREST_VEGETATION, () -> new NetherForestVegetationConfig(
            new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(NetherDescentBlocks.EMBUR_SPROUTS.get().defaultBlockState(), 2)
                            .add(NetherDescentBlocks.EMBUR_ROOTS.getBlockState(), 2)
                            .add(NetherDescentBlocks.TALL_EMBUR_ROOTS.get().defaultBlockState())
                            .add(NetherDescentBlocks.EMBUR.growerItem().getBlockState())
            ), 3, 1)
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_CAVE_MOSS = ConfiguredFeaturesUtil.createConfiguredFeature("embur_cave_moss", Feature.MULTIFACE_GROWTH, (configuredFeatureBootstrapContext) -> new MultifaceGrowthConfiguration(NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), 16, true, true, true, 1F, HolderSet.direct(Block::builtInRegistryHolder, NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.EMBUR_NYLIUM.get(), NetherDescentBlocks.EMBUR.logstem())));

	public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_EMBUR_MOSS = ConfiguredFeaturesUtil.createConfiguredFeature(
			"hanging_embur_moss",
			Feature.RANDOM_PATCH,
			() -> new RandomPatchConfiguration(
					96, 10, 10,
					PlacementUtils.filtered(
							NetherDescentFeature.CEILING_HANGING_VINES.get(),
							new CeilingHangingVinesFeatureConfiguration(
									NetherDescentBlocks.BLUE_NETHERRACK.get(),
									NetherDescentBlocks.EMBUR_MOSS_BLOCK.get().defaultBlockState(),
									NetherDescentBlocks.EMBUR_HANGING_MOSS.get().defaultBlockState(),
									UniformInt.of(0, 20)
							),
							ConfiguredFeaturesUtil.simplePatchPredicate(List.of())
					)
			)
	);

    //public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_MOSS_PATCH_BONEMEAL = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock();
	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_MOSS_CARPET_PATCH = ConfiguredFeaturesUtil.createConfiguredFeature("embur_moss_carpet_patch", NetherDescentFeature.BLOCK_CARPET_PATCH, () -> new CarpetPatchFeatureConfiguration(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()));

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_TORRIDS_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_torrids_vegetation", Feature.NETHER_FOREST_VEGETATION,  (configuredFeatureBootstrapContext) -> new NetherForestVegetationConfig(
			new WeightedStateProvider(
					SimpleWeightedRandomList.<BlockState>builder()
							.add(NetherDescentBlocks.SYTHIAN_ROOTS.getBlockState(), 2)
							.add(NetherDescentBlocks.SYTHIAN_SHOOT.get().defaultBlockState())
							.add(NetherDescentBlocks.SYTHIAN_SPROUTS.getBlockState(), 2)
                            .add(NetherDescentBlocks.SYTHIAN.growerItem().getBlockState())
			), 8, 6));

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_TORRIDS_VEGETATION_BONEMEAL = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_torrids_vegetation_bonemeal", Feature.NETHER_FOREST_VEGETATION,  (configuredFeatureBootstrapContext) -> new NetherForestVegetationConfig(
			new WeightedStateProvider(
					SimpleWeightedRandomList.<BlockState>builder()
							.add(NetherDescentBlocks.SYTHIAN_ROOTS.getBlockState(), 2)
							.add(NetherDescentBlocks.SYTHIAN_SHOOT.get().defaultBlockState())
							.add(NetherDescentBlocks.SYTHIAN_SPROUTS.getBlockState(), 2)
			), 3, 1));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_CRIMSON_ROOTS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("tall_crimson_roots", NetherDescentBlocks.TALL_CRIMSON_ROOTS, 15);

	//public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_CRIMSON_FUNGI = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("tall_crimson_fungi", NetherDescentBlocks.TALL_CRIMSON_FUNGI, 15);

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CRIMSON_BERRY = createPatchConfiguredFeatureState("crimson_berry", () -> NetherDescentBlocks.CRIMSON_BERRY_BUSH.get().defaultBlockState().setValue(CrimsonBerryBushBlock.AGE, 3), 32);

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_GARDEN_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_garden_vegetation", NetherDescentFeature.NETHER_FOREST_VEGETATION, () -> new NetherForestVegetationConfig(
                new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(Blocks.CRIMSON_ROOTS.defaultBlockState(), 5)
                                .add(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get().defaultBlockState(), 3)
                                .add(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get().defaultBlockState(), 3)
                                .add(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get().defaultBlockState())
                                .add(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get().defaultBlockState().setValue(CrimsonBerryBushBlock.AGE, 1))
                                .add(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get().defaultBlockState().setValue(CrimsonBerryBushBlock.AGE, 2))
                                .add(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get().defaultBlockState().setValue(CrimsonBerryBushBlock.AGE, 3))
                ), 8, 6));

	public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_GRASS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("wailing_grass", NetherDescentBlocks.WAILING_GRASS, 15);
	public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_FUNGUS = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("wailing_fungus", NetherDescentBlocks.WAILING.growerItem().block(), 15);

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_GARTH_VEGETATION = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_garth_vegetation", Feature.RANDOM_SELECTOR,  (configuredFeatureBootstrapContext) -> {
			    HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_FUNGUS)), 0.35F)),
						PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_GRASS)));
				}
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TANGLE_ROOTS = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tangle_roots", NetherDescentFeature.HANGING_PLANT, () -> new HangingPlantFeatureConfiguration(Blocks.BLACKSTONE, NetherDescentBlocks.ARISIAN_TANGLE_ROOTS.get(), NetherDescentBlocks.ARISIAN_TANGLE_ROOTS_PLANT.get()));


	public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_SYTHIAN_ROOTS = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_sythian_roots", NetherDescentFeature.HANGING_PLANT, () -> new HangingPlantFeatureConfiguration(Blocks.NETHERRACK, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS.get(), NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get()));
    //public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HANGING_SYTHIAN_ROOTS = ConfiguredFeaturesUtil.createConfiguredFeature("patch_hanging_sythian_roots", Feature.RANDOM_PATCH, () -> FeatureUtils.simplePatchConfiguration(NetherDescentFeature.HANGING_PLANT.get(), new HangingPlantFeatureConfiguration(Blocks.NETHERRACK, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS.get(), NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get())));

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_STALK = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_stalk", NetherDescentFeature.SYTHIAN_STALK, NoneFeatureConfiguration::new);
	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_STALK_DOWNWARD = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_stalk_downward", NetherDescentFeature.SYTHIAN_STALK_DOWNWARD, NoneFeatureConfiguration::new);

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_BULB_BLOSSOM = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_bulb_blossom", NetherDescentFeature.FLOATING_BLOCK_FEATURE, () -> new FloatingBlockFeatureConfiguration(BlockStateProvider.simple(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get()), UniformInt.of(1, 6)));

	private static ResourceKey<ConfiguredFeature<?, ?>> createPatchConfiguredFeatureState(String id, Supplier<? extends BlockState> state, int tries) {
		return ConfiguredFeaturesUtil.createConfiguredFeature(id, Feature.RANDOM_PATCH, () -> FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state.get())), List.of(), tries));
	}

    public static void features() {
        NetherDescent.LOGGER.info("Registering Nether Descent Features");
        NetherDescentTreeFeatures.treeFeatures();
        NetherDescentOreFeatures.oreFeatures();
    }
}
