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
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.EmburGelVinesDecorator;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.WeepingVinesDecorator;

import java.util.Set;

public class NetherDescentTreeConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_tree1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_canopy1"))
                    .height(BiasedToBottomInt.of(8, 12))
                    .logProvider(BlockStateProvider.simple(Blocks.CRIMSON_STEM))
                    .leavesProvider(BlockStateProvider.simple(Blocks.NETHER_WART_BLOCK))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new WeepingVinesDecorator()))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_tree2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_canopy2"))
                    .height(BiasedToBottomInt.of(7, 12))
                    .logProvider(BlockStateProvider.simple(Blocks.CRIMSON_STEM))
                    .leavesProvider(BlockStateProvider.simple(Blocks.NETHER_WART_BLOCK))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new WeepingVinesDecorator()))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_TREES_HANGING = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_trees_hanging",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CRIMSON_FUNGI_TREE1)), 0.5F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CRIMSON_FUNGI_TREE2)));
            }
    );

    //SYTHIAN TREES ----------------------------------------------------------------------------------------------------------------------------------------
    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy1"))
                    .height(BiasedToBottomInt.of(9, 12))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(5)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy2"))
                    .height(BiasedToBottomInt.of(10, 14))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree3",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk3"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy3"))
                    .height(BiasedToBottomInt.of(15, 20))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE4 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree4",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk4"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy4"))
                    .height(BiasedToBottomInt.of(18, 25))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE5 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree5",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk5"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy5"))
                    .height(BiasedToBottomInt.of(20, 40))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE6 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree6",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk6"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy6"))
                    .height(BiasedToBottomInt.of(21, 35))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_trees",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE1)), 0.16F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE2)), 0.16F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE3)), 0.16F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE4)), 0.16F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE5)), 0.16F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE6)));
            }
    );

    //EMBUR WART TREES ------------------------------------------------------------------------------------------------------------------------
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART1 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart1_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart1_canopy"))
                    .height(BiasedToBottomInt.of(2, 4))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART2 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart2_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart2_canopy"))
                    .height(BiasedToBottomInt.of(4, 8))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART3 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart3",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart3_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart3_canopy"))
                    .height(BiasedToBottomInt.of(6, 10))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART4 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart4",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart4_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart4_canopy"))
                    .height(BiasedToBottomInt.of(10, 14))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART5 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart5",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart5_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart5_canopy"))
                    .height(BiasedToBottomInt.of(14, 18))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART6 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart6",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart6_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart6_canopy"))
                    .height(BiasedToBottomInt.of(11, 15))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_WART7 = ConfiguredFeaturesUtil.createConfiguredFeature("embur_wart7",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/warts/embur/wart7_stem"))
                    .canopyLocation(NetherDescent.id("features/warts/embur/wart7_canopy"))
                    .height(BiasedToBottomInt.of(11, 15))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.EMBUR_GEL_BLOCK.get().defaultBlockState()))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(new EmburGelVinesDecorator()))
                    .build()
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