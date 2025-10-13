package net.potionstudios.netherdescent.world.level.levelgen.feature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CarpetPatchFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;

import java.util.function.Supplier;

public class NetherDescentFeatures {

	public static final Supplier<Feature<CeilingHangingVinesFeatureConfiguration>> CEILING_HANGING_VINES = create("ceiling_hanging_vines", () -> new CeilingHangingVinesFeature(CeilingHangingVinesFeatureConfiguration.CODEC));
	public static final Supplier<Feature<CarpetPatchFeatureConfiguration>> BLOCK_CARPET_PATCH = create("block_carpet_patch", () -> new CarpetPatchFeature(CarpetPatchFeatureConfiguration.CODEC));

	private static <C extends FeatureConfiguration, F extends Feature<C>> Supplier<F> create(String id, Supplier<F> supplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.FEATURE, id, supplier);
	}

	public static void features() {
		NetherDescent.LOGGER.info("Loading Nether Descent Custom Features");
	}
}
