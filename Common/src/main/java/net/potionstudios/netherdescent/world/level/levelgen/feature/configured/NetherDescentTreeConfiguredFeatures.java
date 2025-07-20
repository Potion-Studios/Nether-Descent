package net.potionstudios.netherdescent.world.level.levelgen.feature.configured;

import com.google.common.collect.ImmutableList;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.TYGFeatures;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.TreeFromStructureNBTConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.PlacedFeaturesUtil;

public class NetherDescentTreeConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART1 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart2",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart2_stem"),
					NetherDescent.id("features/warts/embur/wart2_canopy"),
					BiasedToBottomInt.of(5, 15),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					new WeightedStateProvider(SimpleWeightedRandomList.single(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState())),
					Blocks.CRIMSON_STEM,
					Blocks.NETHER_WART_BLOCK,
					BlockTags.NYLIUM, 4
			)
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART2 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart1",
			TYGFeatures.TREE_FROM_NBT_V1,
			() -> new TreeFromStructureNBTConfig(
					NetherDescent.id("features/warts/embur/wart1_stem"),
					NetherDescent.id("features/warts/embur/wart1_canopy"),
					BiasedToBottomInt.of(5, 15),
					BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()),
					new WeightedStateProvider(SimpleWeightedRandomList.single(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState())),
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
						new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART1)), 0.5F)),
						PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(EMBUR_WART2)));
			}
	);

	public static void treeConfiguredFeatures() {
		NetherDescent.LOGGER.info("NetherDescent Tree Features");
	}
}
