package net.potionstudios.netherdescent.world.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.ArrayList;
import java.util.function.Supplier;

public class NetherDescentItems {

    public static final ArrayList<Supplier<? extends Item>> ITEMS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> SIMPLE_ITEMS = new ArrayList<>();

    public static final Supplier<Item> BLUE_NETHER_BRICK = registerSimpleItem("blue_nether_brick", () -> new Item(new Item.Properties()));

    //public static final Supplier<Item> CRIMSON_BERRIES = registerSimpleItem()

    public static <I extends Item> Supplier<I> registerSimpleItem(String id, Supplier<I> item) {
        Supplier<I> supplier = registerItem(id, item);
        SIMPLE_ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        Supplier<I> supplier = register(id, item);
        ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> register(String id, Supplier<I> item) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, id, item);
    }

    public static void items() {
        NetherDescent.LOGGER.info("Registering Nether Descent Items");
    }
}
