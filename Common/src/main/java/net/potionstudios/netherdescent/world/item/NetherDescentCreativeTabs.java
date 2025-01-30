package net.potionstudios.netherdescent.world.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.ArrayList;
import java.util.function.Supplier;

public class NetherDescentCreativeTabs {

    @SafeVarargs
    private static ResourceKey<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        PlatformHandler.PLATFORM_HANDLER.createCreativeTab(name, icon, items);
        return NetherDescent.key(Registries.CREATIVE_MODE_TAB, name);
    }

    public static void tabs() {
        NetherDescent.LOGGER.info("Registering Nether Descent Creative Tabs");
    }
}
