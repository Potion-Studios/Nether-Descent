package net.potionstudios.netherdescent.data.worldgen.placement;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.*;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentOreFeatures;

import java.util.List;

public class NetherDescentOrePlacements {

    //public static final ResourceKey<PlacedFeature> ORE_BLUE_GOLD_DELTAS = PlacedFeaturesUtil.createPlacedFeature("ore_blue_gold_deltas", NetherDescentOreFeatures.ORE_BLUE_QUARTZ, () -> commonOrePlacement(20, PlacementUtils.RANGE_10_10));
    //public static final ResourceKey<PlacedFeature> ORE_BLUE_QUARTZ_DELTAS = PlacedFeaturesUtil.createPlacedFeature("ore_blue_quartz_deltas", NetherDescentOreFeatures.ORE_BLUE_QUARTZ, () -> commonOrePlacement(32, PlacementUtils.RANGE_10_10));
    public static final ResourceKey<PlacedFeature> ORE_BLUE_GOLD_NETHER = PlacedFeaturesUtil.createPlacedFeature("ore_blue_gold_nether", NetherDescentOreFeatures.ORE_BLUE_NETHER_GOLD, () -> commonOrePlacement(10, PlacementUtils.RANGE_10_10));
    public static final ResourceKey<PlacedFeature> ORE_BLUE_QUARTZ_NETHER = PlacedFeaturesUtil.createPlacedFeature("ore_blue_quartz_nether", NetherDescentOreFeatures.ORE_BLUE_QUARTZ, () -> commonOrePlacement(16, PlacementUtils.RANGE_10_10));
    public static final ResourceKey<PlacedFeature> ORE_PENDORITE = PlacedFeaturesUtil.createPlacedFeature("ore_pendorite", NetherDescentOreFeatures.ORE_PENDORITE, () -> commonOrePlacement(16, PlacementUtils.RANGE_10_10));

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    protected static void orePlacements() {
        NetherDescent.LOGGER.info("Registering Nether Descent Ore Placements");
    }
}
