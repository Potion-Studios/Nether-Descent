package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public interface NetherDescentFeatureTypes {
	Supplier<MapCodec<? extends Feature>> BLOCK_CARPET_PATCH = create("block_carpet_patch", () -> CarpetPatchFeature.CODEC);
	Supplier<MapCodec<? extends Feature>> HANGING_PLANT = create("hanging_plant", () -> HangingPlantFeature.CODEC);
	Supplier<MapCodec<? extends Feature>> SYTHIAN_STALK = create("sythian_stalk", () -> SythianStalkFeature.CODEC);
	Supplier<MapCodec<? extends Feature>> BASALT_LINE = create("basalt_line", () -> BasaltLineFeature.CODEC);

	private static Supplier<MapCodec<? extends Feature>> create(String id, Supplier<MapCodec<? extends Feature>> supplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.FEATURE_TYPE, id, supplier);
	}

	static void features() {
		NetherDescent.LOGGER.info("Registering Nether Descent Feature Types");
	}
}
