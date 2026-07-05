package net.potionstudios.netherdescent.compat.lithostitched;

import com.mojang.serialization.MapCodec;
import dev.worldgen.lithostitched.api.predicate.LoadPredicate;
import dev.worldgen.lithostitched.api.registry.LithostitchedBuiltInRegistries;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class LoadPredicateType {
	public static final Supplier<MapCodec<ConfigLoadPredicate>> CONFIG_LOAD_PREDICATE = register("config", () -> ConfigLoadPredicate.CODEC);

	private static <L extends LoadPredicate> Supplier<MapCodec<L>> register(String id, Supplier<MapCodec<L>> codec) {
		return PlatformHandler.PLATFORM_HANDLER.register(LithostitchedBuiltInRegistries.LOAD_PREDICATE_TYPE, id, codec);
	}

	public static void loadPredicateType() {
		NetherDescent.LOGGER.info("Registering LoadPredicateType for Lithostitched compatibility.");
	}
}
