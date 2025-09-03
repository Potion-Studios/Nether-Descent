package net.potionstudios.netherdescent.world.level.levelgen.feature.placed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.NetherDescentTreeConfiguredFeatures;

import java.util.List;

public class NetherDescentTreePlacedFeatures {

	public static final ResourceKey<PlacedFeature> EMBUR_WARTS = PlacedFeaturesUtil.createPlacedFeature("embur_warts", NetherDescentTreeConfiguredFeatures.EMBUR_WARTS, () -> List.of(CountOnEveryLayerPlacement.of(3), BiomeFilter.biome()));

	public static void treePlacedFeatures() {
		NetherDescentTreeConfiguredFeatures.treeConfiguredFeatures();
	}

}
