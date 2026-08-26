package net.potionstudios.netherdescent.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.TYGFeatures;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.TreeFromStructureNBTConfig;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.TreeFromStructureNBTConfigV2;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.TreeLogFilterBehavior;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.treedecorators.AttachedToLogsDecorator;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.data.worldgen.placement.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.FungalBulbsBlock;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.GrowingPlantVinesDecorator;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.HornetNestDecorator;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class NetherDescentTreeFeatures {
    public static final Supplier<AttachedToLogsDecorator> SYTHIAN_WALL_ROOTS = () -> new AttachedToLogsDecorator(0.22F, 0, 1, SimpleStateProvider.simple(NetherDescentBlocks.SYTHIAN_ROOTS.get()), 2, List.of(Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.EAST));
    public static final Supplier<AttachedToLogsDecorator> ARISIAN_BRANCH_DECORATOR = () -> new AttachedToLogsDecorator(0.2f, 0, 1, SimpleStateProvider.simple(NetherDescentBlocks.ARISIAN_BRANCH.get()), 2, List.of(Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.EAST));
    public static final Supplier<AttachedToLogsDecorator> FUNGAL_BULBS_WALL_UP_DOWN = () -> new AttachedToLogsDecorator(0.2F, 0, 1, SimpleStateProvider.simple(NetherDescentBlocks.FUNGAL_BULBS.get().defaultBlockState().setValue(FungalBulbsBlock.FACE, AttachFace.WALL)), 2, List.of(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH));

    public static final GrowingPlantVinesDecorator WEEPING_VINES_DECORATOR = new GrowingPlantVinesDecorator(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT);
    public static final Supplier<GrowingPlantVinesDecorator> ARISIAN_TANGLE_ROOTS_DECORATOR = () -> new GrowingPlantVinesDecorator(NetherDescentBlocks.ARISIAN_TANGLE_ROOTS.get(), NetherDescentBlocks.ARISIAN_TANGLE_ROOTS_PLANT.get(), NetherDescentBlocks.ARISIAN.logstem());
    public static final Supplier<AttachedToLogsDecorator> THORN_SPROUT_DECORATOR = () -> new AttachedToLogsDecorator(0.1F, 0, 1, SimpleStateProvider.simple(NetherDescentBlocks.THORN_SPROUT.get()), 2, List.of(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH));

    //ARISIAN TREES ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_ROOT_1 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_root_1",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_root_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_root_canopy1"))
                    .height(BiasedToBottomInt.of(2, 45))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_ROOT_2 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_root_2",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_root_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_root_canopy2"))
                    .height(BiasedToBottomInt.of(2, 45))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_ROOT_3 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_root_3",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_root_trunk3"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_root_canopy3"))
                    .height(BiasedToBottomInt.of(2, 45))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tree1",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy1"))
                    .height(BiasedToBottomInt.of(2, 4))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tree2",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy2"))
                    .height(BiasedToBottomInt.of(4, 6))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tree3",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk3"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy3"))
                    .height(BiasedToBottomInt.of(11, 26))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREE4 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tree4",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk4"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy4"))
                    .height(BiasedToBottomInt.of(11, 26))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREE5 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tree5",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk5"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy5"))
                    .height(BiasedToBottomInt.of(11, 26))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREE6 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_tree6",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk6"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy6"))
                    .height(BiasedToBottomInt.of(16, 30))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_HYPHAE))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_LARGE_BUSH = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_large_bush",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_large_bush_trunk"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_large_bush_canopy"))
                    .height(BiasedToBottomInt.of(2, 3))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get())))
                    .logTarget(Set.of(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesTarget(List.of(NetherDescentBlocks.ARISIAN_LEAVES.get()))
                    .maxLogDepth(4)
                    .growableOn(BlockPredicate.anyOf(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER), BlockPredicate.matchesBlocks(Collections.singletonList(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_ROOTS = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_roots",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_ROOT_1)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_ROOT_2)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_ROOT_3)), 0.25F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_ROOT_3)));
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ARISIAN_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_arisian_trees",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_TREE1)), 0.1F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_TREE2)), 0.1F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_TREE3)), 0.2F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_TREE4)), 0.2F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_TREE5)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_LARGE_BUSH)), 0.3F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_ARISIAN_TREE6)));
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_tree1",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy1"))
                    .height(BiasedToBottomInt.of(2, 4))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_tree2",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy2"))
                    .height(BiasedToBottomInt.of(4, 6))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_tree3",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk3"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy3"))
                    .height(BiasedToBottomInt.of(11, 26))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREE4 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_tree4",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk4"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy4"))
                    .height(BiasedToBottomInt.of(11, 26))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREE5 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_tree5",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk5"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy5"))
                    .height(BiasedToBottomInt.of(11, 26))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_STEM))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREE6 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_tree6",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_trunk6"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_canopy6"))
                    .height(BiasedToBottomInt.of(16, 30))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.WARPED_HYPHAE))
                    .leavesTarget(List.of(Blocks.CHERRY_LEAVES, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(ARISIAN_TANGLE_ROOTS_DECORATOR.get(), ARISIAN_BRANCH_DECORATOR.get(), THORN_SPROUT_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_BUSH_1 = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_bush1",
            Feature.TREE,
            () -> new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()),
                    new StraightTrunkPlacer(1, 0, 0),
                    BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get()),
                    new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                    new TwoLayersFeatureSize(0, 0, 0)
            ).dirt(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get())).build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_LARGE_BUSH = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_large_bush",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/arisian/arisian_large_bush_trunk"))
                    .canopyLocation(NetherDescent.id("features/trees/arisian/arisian_large_bush_canopy"))
                    .height(BiasedToBottomInt.of(2, 3))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.ARISIAN_LEAVES.get())))
                    .logTarget(Set.of(NetherDescentBlocks.ARISIAN.logstem()))
                    .leavesTarget(List.of(NetherDescentBlocks.ARISIAN_LEAVES.get()))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.ARISIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARISIAN_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("arisian_trees",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_TREE1)), 0.1F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_TREE2)), 0.1F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_TREE3)), 0.2F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_TREE4)), 0.2F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_TREE5)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_BUSH_1)), 0.3F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_LARGE_BUSH)), 0.35F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(ARISIAN_TREE6)));
            }
    );


    //BONE TREES -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("bone_tree1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk1"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy1"))
                    .height(BiasedToBottomInt.of(5, 20))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.CRIMSON_CARPET.get()))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("bone_tree2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk2"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy2"))
                    .height(BiasedToBottomInt.of(7, 22))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.CRIMSON_CARPET.get()))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("bone_tree3",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk3"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy3"))
                    .height(BiasedToBottomInt.of(4, 20))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.CRIMSON_CARPET.get()))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_TREE4 = ConfiguredFeaturesUtil.createConfiguredFeature("bone_tree4",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk4"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy4"))
                    .height(BiasedToBottomInt.of(7, 25))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.CRIMSON_CARPET.get()))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_BONE_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_bone_tree1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk1"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy1"))
                    .height(BiasedToBottomInt.of(5, 20))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR, FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_BONE_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_bone_tree2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk2"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy2"))
                    .height(BiasedToBottomInt.of(7, 25))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR, FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_BONE_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_bone_tree3",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk3"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy3"))
                    .height(BiasedToBottomInt.of(4, 20))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR, FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_BONE_TREE4 = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_bone_tree4",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/bones/bone_trunk4"))
                    .canopyLocation(NetherDescent.id("features/bones/bone_canopy4"))
                    .height(BiasedToBottomInt.of(7, 25))
                    .logProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .leavesProvider(BlockStateProvider.simple(Blocks.BONE_BLOCK))
                    .logTarget(Set.of(Blocks.BONE_BLOCK))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR, FUNGAL_BULBS_WALL_UP_DOWN.get()))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("bone_trees",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(BONE_TREE1)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(BONE_TREE2)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(BONE_TREE3)), 0.25F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(BONE_TREE4)));
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_BONE_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("hanging_bone_trees",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_BONE_TREE1)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_BONE_TREE2)), 0.25F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_BONE_TREE3)), 0.25F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(HANGING_BONE_TREE4)));
            }
    );

    //CRIMSON TREES ----------------------------------------------------------------------------------------------------------------------------------------
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_HANGING_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_hanging_tree1",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_canopy1"))
                    .height(BiasedToBottomInt.of(8, 10))
                    .logProvider(BlockStateProvider.simple(Blocks.CRIMSON_STEM))
                    .leavesProvider(List.of(BlockStateProvider.simple(Blocks.NETHER_WART_BLOCK), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(List.of(Blocks.NETHER_WART_BLOCK, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_HANGING_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_hanging_tree2",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_canopy2"))
                    .height(BiasedToBottomInt.of(7, 10))
                    .logProvider(BlockStateProvider.simple(Blocks.CRIMSON_STEM))
                    .leavesProvider(List.of(BlockStateProvider.simple(Blocks.NETHER_WART_BLOCK), BlockStateProvider.simple(Blocks.SHROOMLIGHT)))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(List.of(Blocks.NETHER_WART_BLOCK, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfigV2.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .logsPlacementFilter(BlockPredicate.not(BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.BEDROCK), BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))))
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_TREES_HANGING = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_trees_hanging",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CRIMSON_FUNGI_HANGING_TREE1)), 0.5F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CRIMSON_FUNGI_HANGING_TREE2)));
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_trees",
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
                    .treeDecorators(ImmutableList.of(SYTHIAN_WALL_ROOTS.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.SYTHIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(SYTHIAN_WALL_ROOTS.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.SYTHIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(SYTHIAN_WALL_ROOTS.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.SYTHIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(SYTHIAN_WALL_ROOTS.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.SYTHIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(SYTHIAN_WALL_ROOTS.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.SYTHIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> SYTHIAN_FUNGI_TREE6 = ConfiguredFeaturesUtil.createConfiguredFeature("sythian_fungi_tree6",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_trunk6"))
                    .canopyLocation(NetherDescent.id("features/trees/sythian/sythian_fungi_canopy6"))
                    .height(BiasedToBottomInt.of(21, 22))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN.logstem()))
                    .leavesProvider(BlockStateProvider.simple(NetherDescentBlocks.SYTHIAN_WART_BLOCK.get()))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(Set.of(Blocks.OAK_LEAVES))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.NYLIUM))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(SYTHIAN_WALL_ROOTS.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.SYTHIAN.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE5)), 0.2F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(SYTHIAN_FUNGI_TREE6)));
            }
    );

    //EMBUR WART TREES ------------------------------------------------------------------------------------------------------------------------
    public static final Supplier<GrowingPlantVinesDecorator> EMBUR_GEL_VINES_DECORATOR = () -> new GrowingPlantVinesDecorator(NetherDescentBlocks.EMBUR_GEL_VINES.get(), NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get());
    public static final Supplier<HornetNestDecorator> HORNET_NEST_DECORATOR = () -> new HornetNestDecorator(0.15F);

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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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
                    .treeDecorators(ImmutableList.of(HORNET_NEST_DECORATOR.get(), EMBUR_GEL_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.EMBUR.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
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

    //WAILING TREES ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static final Supplier<GrowingPlantVinesDecorator> WAILING_VINES_DECORATOR = () -> new GrowingPlantVinesDecorator(NetherDescentBlocks.WAILING_VINES.get(), NetherDescentBlocks.WAILING_VINES_PLANT.get(), NetherDescentBlocks.WAILING_WART_BLOCK.get());

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_FUNGI_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_fungi_tree1",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/wailing/wailing_fungi_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/wailing/wailing_fungi_canopy1"))
                    .height(BiasedToBottomInt.of(4, 10))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.WAILING.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.WAILING_WART_BLOCK.get()), BlockStateProvider.simple(NetherDescentBlocks.WAILING_GILLS.get())))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(List.of(Blocks.WARPED_WART_BLOCK, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesBlocks(NetherDescentBlocks.WAILING_NYLIUM.get(), Blocks.SOUL_SOIL))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WAILING_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.WAILING.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_FUNGI_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_fungi_tree2",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/wailing/wailing_fungi_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/wailing/wailing_fungi_canopy2"))
                    .height(BiasedToBottomInt.of(4, 10))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.WAILING.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.WAILING_WART_BLOCK.get()), BlockStateProvider.simple(NetherDescentBlocks.WAILING_GILLS.get())))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(List.of(Blocks.WARPED_WART_BLOCK, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesBlocks(NetherDescentBlocks.WAILING_NYLIUM.get(), Blocks.SOUL_SOIL))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WAILING_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.WAILING.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_FUNGI_TREE3 = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_fungi_tree3",
            TYGFeatures.TREE_FROM_NBT_V2,
            () -> new TreeFromStructureNBTConfigV2.Builder()
                    .baseLocation(NetherDescent.id("features/trees/wailing/wailing_fungi_trunk3"))
                    .canopyLocation(NetherDescent.id("features/trees/wailing/wailing_fungi_canopy3"))
                    .height(BiasedToBottomInt.of(9, 19))
                    .logProvider(BlockStateProvider.simple(NetherDescentBlocks.WAILING.logstem()))
                    .leavesProvider(List.of(BlockStateProvider.simple(NetherDescentBlocks.WAILING_WART_BLOCK.get()), BlockStateProvider.simple(NetherDescentBlocks.WAILING_GILLS.get())))
                    .logTarget(Set.of(Blocks.OAK_LOG))
                    .leavesTarget(List.of(Blocks.WARPED_WART_BLOCK, Blocks.SHROOMLIGHT))
                    .growableOn(BlockPredicate.matchesBlocks(NetherDescentBlocks.WAILING_NYLIUM.get(), Blocks.SOUL_SOIL))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WAILING_VINES_DECORATOR.get()))
                    .logsPlacementFilter(logPlacementFilter(NetherDescentBlocks.WAILING.growerItem().get()))
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_FUNGI_TREES = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_fungi_trees",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_FUNGI_TREE1)), 0.3F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_FUNGI_TREE2)), 0.33F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_FUNGI_TREE3)));
            }
    );

    //WAILING CAGES --------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_CAGE1 = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_cage1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/cages/wailing_cage_trunk1"))
                    .canopyLocation(NetherDescent.id("features/cages/wailing_cage_canopy1"))
                    .height(BiasedToBottomInt.of(12, 12))
                    .logProvider(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                            .add(Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState())
                            .add(Blocks.POLISHED_BLACKSTONE.defaultBlockState(), 5)
                            .add(Blocks.BLACKSTONE.defaultBlockState(), 5)
                            .add(Blocks.GILDED_BLACKSTONE.defaultBlockState(), 5)))
                    .leavesProvider(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                            .add(Blocks.BONE_BLOCK.defaultBlockState())
                            .add(Blocks.CALCITE.defaultBlockState(), 3)
                            .add(Blocks.BUDDING_AMETHYST.defaultBlockState(), 3)))
                    .logTarget(Set.of(Blocks.POLISHED_BLACKSTONE_BRICKS))
                    .leavesTarget(Set.of(Blocks.BONE_BLOCK))
                    .growableOn(BlockPredicate.matchesBlocks(Blocks.NETHERRACK, Blocks.SOUL_SOIL))
                    .maxLogDepth(4)
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_CAGE2 = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_cage2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/cages/wailing_cage_trunk2"))
                    .canopyLocation(NetherDescent.id("features/cages/wailing_cage_canopy2"))
                    .height(BiasedToBottomInt.of(48, 48))
                    .logProvider(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                            .add(Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState())
                            .add(Blocks.POLISHED_BLACKSTONE.defaultBlockState(), 5)
                            .add(Blocks.BLACKSTONE.defaultBlockState(), 5)
                            .add(Blocks.GILDED_BLACKSTONE.defaultBlockState(), 5)))
                    .leavesProvider(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                            .add(Blocks.BONE_BLOCK.defaultBlockState())
                            .add(Blocks.CALCITE.defaultBlockState(), 3)
                            .add(Blocks.BUDDING_AMETHYST.defaultBlockState(), 3)))
                    .logTarget(Set.of(Blocks.POLISHED_BLACKSTONE_BRICKS))
                    .leavesTarget(Set.of(Blocks.BONE_BLOCK))
                    .growableOn(BlockPredicate.matchesBlocks(Blocks.NETHERRACK, Blocks.SOUL_SOIL))
                    .maxLogDepth(4)
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .treeLogFilterBehavior(TreeLogFilterBehavior.PASSTHROUGH)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> WAILING_CAGES = ConfiguredFeaturesUtil.createConfiguredFeature("wailing_cages",
            Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstrapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_CAGE1)), 0.3F),
                        new WeightedPlacedFeature(PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_CAGE2)), 0.33F)),
                        PlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(WAILING_CAGE2)));
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGUS_PLANTED = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungus_planted",
            Feature.HUGE_FUNGUS,
            () -> new HugeFungusConfiguration(
                    NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get().defaultBlockState(),
                    Blocks.CRIMSON_STEM.defaultBlockState(),
                    Blocks.NETHER_WART_BLOCK.defaultBlockState(),
                    Blocks.SHROOMLIGHT.defaultBlockState(),
                    BlockPredicate.replaceable(),
                    true
            ));

    protected static void treeFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Tree Configured Features");
    }

    public static BlockPredicate logPlacementFilter(Block... blocks) {
      return BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.BEDROCK, Blocks.WATER));
    }
}