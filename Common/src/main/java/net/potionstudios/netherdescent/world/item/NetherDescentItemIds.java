package net.potionstudios.netherdescent.world.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.potionstudios.netherdescent.NetherDescent;

public class NetherDescentItemIds {

    public static final ResourceKey<Item> BLUE_NETHER_BRICK = createKey("blue_nether_brick");
    public static final ResourceKey<Item> EMBUR_GEL_BALL = createKey("embur_gel_ball");
    public static final ResourceKey<Item> CRIMSON_BERRY_PIE = createKey("crimson_berry_pie");
    public static final ResourceKey<Item> RAW_PENDORITE = createKey("raw_pendorite");
    public static final ResourceKey<Item> PENDORITE_INGOT = createKey("pendorite_ingot");
    public static final ResourceKey<Item> PENDORITE_NUGGET = createKey("pendorite_nugget");
    public static final ResourceKey<Item> PENDORITE_HORSE_ARMOR = createKey("pendorite_horse_armor");
    public static final ResourceKey<Item> PENDORITE_WOLF_ARMOR = createKey("pendorite_wolf_armor");
    public static final ResourceKey<Item> SOUL_BLAZE_ROD = createKey("soul_blaze_rod");
    public static final ResourceKey<Item> SOUL_BLAZE_POWDER = createKey("soul_blaze_powder");
    public static final ResourceKey<Item> SOUL_FIRE_CHARGE = createKey("soul_fire_charge");
    public static final ResourceKey<Item> PENDORITE_FIRE_CHARGE = createKey("pendorite_fire_charge");
    public static final ResourceKey<Item> SOUL_BLAZE_SPAWN_EGG = createKey("soul_blaze_spawn_egg");
    public static final ResourceKey<Item> PENDORITE_BLAZE_SPAWN_EGG = createKey("pendorite_blaze_spawn_egg");
    public static final ResourceKey<Item> HORNET_SPAWN_EGG = createKey("hornet_spawn_egg");
    public static final ResourceKey<Item> SOUL_GHAST_SPAWN_EGG = createKey("soul_ghast_spawn_egg");

    private static ResourceKey<Item> createKey(String name) {
        return NetherDescent.key(Registries.ITEM, name);
    }
}
