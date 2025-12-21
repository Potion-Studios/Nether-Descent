package net.potionstudios.netherdescent.world.entity.ai.village.poi;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.function.Supplier;

public class NetherDescentPoiTypes {

	public static final ResourceKey<PoiType> HORNET_NEST = register("hornet_nest", NetherDescentBlocks.HORNET_NEST, 0, 1);
	public static final ResourceKey<PoiType> BARTERER = register("barterer", NetherDescentBlocks.BARTERING_TABLE, 1, 1);

	private static ResourceKey<PoiType> register(String id, Supplier<? extends Block> block, int maxTickets, int validRange) {
		PlatformHandler.PLATFORM_HANDLER.registerPOIType(id, block, maxTickets, validRange);
		return NetherDescent.key(Registries.POINT_OF_INTEREST_TYPE, id);
	}

	public static void poiTypes() {
		NetherDescent.LOGGER.info("Registering Nether Descent Poi Types");
	}
}
