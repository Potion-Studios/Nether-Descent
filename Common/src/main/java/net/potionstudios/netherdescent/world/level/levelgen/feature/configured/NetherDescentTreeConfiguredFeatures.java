package net.potionstudios.netherdescent.world.level.levelgen.feature.configured;

import com.google.common.collect.ImmutableList;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.TYGFeatures;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.TreeFromStructureNBTConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.PlacedFeaturesUtil;

public class NetherDescentTreeConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree1",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/trees/sythian/sythian_fungi_trunk1"),
					NetherDescent.id("features/trees/sythian/sythian_fungi_canopy1"),
					BiasedToBottomInt.of(9, 12),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()),
					Blocks.OAK_LOG,
					Blocks.OAK_LEAVES,
					BlockTags.NYLIUM, 5
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree2",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/trees/sythian/sythian_fungi_trunk2"),
					NetherDescent.id("features/trees/sythian/sythian_fungi_canopy2"),
					BiasedToBottomInt.of(10, 14),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()),
					Blocks.OAK_LOG,
					Blocks.OAK_LEAVES,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree3",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/trees/sythian/sythian_fungi_trunk3"),
					NetherDescent.id("features/trees/sythian/sythian_fungi_canopy3"),
					BiasedToBottomInt.of(15, 20),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()),
					Blocks.OAK_LOG,
					Blocks.OAK_LEAVES,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE4 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree4",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/trees/sythian/sythian_fungi_trunk4"),
					NetherDescent.id("features/trees/sythian/sythian_fungi_canopy4"),
					BiasedToBottomInt.of(18, 25),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()),
					Blocks.OAK_LOG,
					Blocks.OAK_LEAVES,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE5 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree5",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/trees/sythian/sythian_fungi_trunk5"),
					NetherDescent.id("features/trees/sythian/sythian_fungi_canopy5"),
					BiasedToBottomInt.of(20, 40),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()),
					Blocks.OAK_LOG,
					Blocks.OAK_LEAVES,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_trees",
			Feature.RANDOM_SELECTOR,
			(configuredFeatureBootstrapContext) -> {
				HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
				return new RandomFeatureConfiguration(ImmutableList.of(
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE1)), 0.2F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE2)), 0.2F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE3)), 0.2F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE4)), 0.2F)),
						PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE5)));
			}
	);

	//EMBUR WART TREES ------------------------------------------------------------------------------------------------------------------------
	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART1 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart1",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart1_stem"),
					NetherDescent.id("features/warts/embur/wart1_canopy"),
					BiasedToBottomInt.of(2, 4),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART2 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart2",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart2_stem"),
					NetherDescent.id("features/warts/embur/wart2_canopy"),
					BiasedToBottomInt.of(4, 8),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART3 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart3",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart3_stem"),
					NetherDescent.id("features/warts/embur/wart3_canopy"),
					BiasedToBottomInt.of(6, 10),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART4 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart4",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/embur_trunk4"),
					NetherDescent.id("features/warts/embur/embur_canopy4"),
					BiasedToBottomInt.of(10, 14),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART5 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart5",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart5_stem"),
					NetherDescent.id("features/warts/embur/wart5_canopy"),
					BiasedToBottomInt.of(14, 18),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART6 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart6",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart6_stem"),
					NetherDescent.id("features/warts/embur/wart6_canopy"),
					BiasedToBottomInt.of(11, 15),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART7 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart7",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart7_stem"),
					NetherDescent.id("features/warts/embur/wart7_canopy"),
					BiasedToBottomInt.of(11, 15),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WARTS = ConfiguredFeaturesUtil.createConfiguredFeature("embur_warts",
			Feature.RANDOM_SELECTOR,
			(configuredFeatureBootstrapContext) -> {
				HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
				return new RandomFeatureConfiguration(ImmutableList.of(
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART1)), 0.1F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART2)), 0.1F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART3)), 0.18F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART4)), 0.1F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART5)), 0.1F),
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART6)), 0.1F)),
						PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART7)));
			}
	);

	public static void treeConfiguredFeatures() {
		NetherDescent.LOGGER.info("NetherDescent Tree Features");
	}
}
