package net.potionstudios.netherdescent.world.level.levelgen.feature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CarpetPatchFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.FloatingBlockFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.HangingPlantFeatureConfiguration;

import java.util.function.Supplier;

public class NetherDescentFeature {

	public static final Supplier<Feature<CeilingHangingVinesFeatureConfiguration>> CEILING_HANGING_VINES = create("ceiling_hanging_vines", () -> new CeilingHangingVinesFeature(CeilingHangingVinesFeatureConfiguration.CODEC));
	public static final Supplier<Feature<CarpetPatchFeatureConfiguration>> BLOCK_CARPET_PATCH = create("block_carpet_patch", () -> new CarpetPatchFeature(CarpetPatchFeatureConfiguration.CODEC));
	public static final Supplier<Feature<HangingPlantFeatureConfiguration>> HANGING_PLANT = create("hanging_plant", () -> new HangingPlantFeature(HangingPlantFeatureConfiguration.CODEC));
	public static final Supplier<Feature<NoneFeatureConfiguration>> SYTHIAN_STALK = create("sythian_stalk", () -> new SythianStalkFeature(NoneFeatureConfiguration.CODEC));
	public static final Supplier<Feature<NoneFeatureConfiguration>> SYTHIAN_STALK_DOWNWARD = create("sythian_stalk_downward", () -> new SythianStalkDownwardFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<NetherForestVegetationConfig>> NETHER_FOREST_VEGETATION = create("nether_forest_vegetation", () -> new NetherForestVegetationFeature(NetherForestVegetationConfig.CODEC));
    public static final Supplier<Feature<FloatingBlockFeatureConfiguration>> FLOATING_BLOCK_FEATURE = create("floating_block_feature", () -> new FloatingBlockFeature(FloatingBlockFeatureConfiguration.CODEC));
	public static final Supplier<Feature<NoneFeatureConfiguration>> BASALT_LINE = create("basalt_line", () -> new BasaltLineFeature(NoneFeatureConfiguration.CODEC));

	private static <C extends FeatureConfiguration, F extends Feature<C>> Supplier<F> create(String id, Supplier<F> supplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.FEATURE, id, supplier);
	}

	public static void features() {
		NetherDescent.LOGGER.info("Loading Nether Descent Custom Features");
	}
}
