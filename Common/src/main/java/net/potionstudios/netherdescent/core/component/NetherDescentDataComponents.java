package net.potionstudios.netherdescent.core.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NetherDescentDataComponents {

	public static final Supplier<DataComponentType<List<HornetNestBlockEntity.Occupant>>> HORNETS = register(
			"hornets",
			builder -> builder.persistent(HornetNestBlockEntity.Occupant.LIST_CODEC)
					.networkSynchronized(HornetNestBlockEntity.Occupant.STREAM_CODEC.apply(ByteBufCodecs.list()))
					.cacheEncoding()
	);

	private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, () -> builder.apply(DataComponentType.builder()).build());
	}

	public static void dataComponents() {
		NetherDescent.LOGGER.info("Registering Nether Descent Data Components");
	}
}
