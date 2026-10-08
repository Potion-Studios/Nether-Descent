package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.*;

import java.util.function.Supplier;

public class NetherDescentFeatureTypes {

	public static final Supplier<Feature<CeilingHangingVinesFeatureConfiguration>> CEILING_HANGING_VINES = create("ceiling_hanging_vines", () -> new CeilingHangingVinesFeature(CeilingHangingVinesFeatureConfiguration.CODEC));
	public static final Supplier<Feature<CarpetPatchFeatureConfiguration>> BLOCK_CARPET_PATCH = create("block_carpet_patch", () -> new CarpetPatchFeature(CarpetPatchFeatureConfiguration.CODEC));
	public static final Supplier<Feature<HangingPlantFeatureConfiguration>> HANGING_PLANT = create("hanging_plant", () -> new HangingPlantFeature(HangingPlantFeatureConfiguration.CODEC));
	public static final Supplier<Feature<SythianStalkFeatureConfiguration>> SYTHIAN_STALK = create("sythian_stalk", () -> new SythianStalkFeature(SythianStalkFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NetherForestVegetationConfig>> NETHER_FOREST_VEGETATION = create("nether_forest_vegetation", () -> new NetherForestVegetationFeature(NetherForestVegetationConfig.CODEC));
	public static final Supplier<Feature<NetherForestVegetationConfig>> HANGING_NETHER_FOREST_VEGETATION = create("hanging_nether_forest_vegetation", () -> new HangingNetherForestVegetationFeature(NetherForestVegetationConfig.CODEC));
    public static final Supplier<MapCodec<FloatingBlockFeature>> FLOATING_BLOCK_FEATURE = create("floating_block_feature", () -> FloatingBlockFeature.CODEC);
	public static final Supplier<MapCodec<BasaltLineFeature>> BASALT_LINE = create("basalt_line", () -> BasaltLineFeature.CODEC);

	private static Supplier<MapCodec<? extends Feature>> create(String id, Supplier<MapCodec<? extends Feature>> supplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.FEATURE_TYPE, id, supplier);
	}

	public static void features() {
		NetherDescent.LOGGER.info("Registering Nether Descent Features");
	}
}
