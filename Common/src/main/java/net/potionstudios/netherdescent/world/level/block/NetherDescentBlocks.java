package net.potionstudios.netherdescent.world.level.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentBlocks {


    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void blocks() {
        NetherDescent.LOGGER.info("Registering Nether Descent Blocks");
    }
}
