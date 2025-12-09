package net.potionstudios.netherdescent.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.TYGFeatures;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.TreeFromStructureNBTConfig;
import dev.corgitaco.ohthetreesyoullgrow.world.level.levelgen.feature.configurations.treedecorators.AttachedToLogsDecorator;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.data.worldgen.placement.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.GrowingPlantVinesDecorator;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.HornetNestDecorator;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class NetherDescentTreeFeatures {
    public static final Supplier<AttachedToLogsDecorator> SYTHIAN_WALL_ROOTS = () -> new AttachedToLogsDecorator(0.22F, 0, 1, SimpleStateProvider.simple(NetherDescentBlocks.SYTHIAN_ROOTS.get()), 2, List.of(Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.EAST));
    public static final Supplier<AttachedToLogsDecorator> FUNGAL_BULBS_WALL_UP_DOWN = () -> new AttachedToLogsDecorator(0.25F, 0, 1, SimpleStateProvider.simple(NetherDescentBlocks.FUNGAL_BULBS.get()), 2, List.of(Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.UP, Direction.DOWN));

    public static final GrowingPlantVinesDecorator WEEPING_VINES_DECORATOR = new GrowingPlantVinesDecorator(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT);
    
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
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
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
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
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_HANGING_TREE1 = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_hanging_tree1",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_trunk1"))
                    .canopyLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_canopy1"))
                    .height(BiasedToBottomInt.of(8, 10))
                    .logProvider(BlockStateProvider.simple(Blocks.CRIMSON_STEM))
                    .leavesProvider(BlockStateProvider.simple(Blocks.NETHER_WART_BLOCK))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGI_HANGING_TREE2 = ConfiguredFeaturesUtil.createConfiguredFeature("crimson_fungi_hanging_tree2",
            TYGFeatures.TREE_FROM_NBT_V1,
            () -> new TreeFromStructureNBTConfig.Builder()
                    .baseLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_trunk2"))
                    .canopyLocation(NetherDescent.id("features/trees/crimson/crimson_fungi_canopy2"))
                    .height(BiasedToBottomInt.of(7, 10))
                    .logProvider(BlockStateProvider.simple(Blocks.CRIMSON_STEM))
                    .leavesProvider(BlockStateProvider.simple(Blocks.NETHER_WART_BLOCK))
                    .logTarget(Set.of(Blocks.CRIMSON_STEM))
                    .leavesTarget(Set.of(Blocks.NETHER_WART_BLOCK))
                    .growableOn(BlockPredicate.matchesTag(BlockTags.BASE_STONE_NETHER))
                    .maxLogDepth(4)
                    .treeDecorators(ImmutableList.of(WEEPING_VINES_DECORATOR))
                    .orientation(TreeFromStructureNBTConfig.Orientation.UPSIDE_DOWN)
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
    public static final Supplier<HornetNestDecorator> HORNET_NEST_DECORATOR = () -> new HornetNestDecorator(0.1F);
    
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

    protected static void treeFeatures() {
        NetherDescent.LOGGER.info("NetherDescent Tree Features");
    }
}