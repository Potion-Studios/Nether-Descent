package net.potionstudios.netherdescent.world.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentItems {

    public static <I extends Item> Supplier<I> register(String id, Supplier<I> item) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, id, item);
    }

    public static void items() {
        NetherDescent.LOGGER.info("Registering Nether Descent Items");
    }
}
