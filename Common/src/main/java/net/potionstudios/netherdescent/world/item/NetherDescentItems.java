package net.potionstudios.netherdescent.world.item;

import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityTypes;
import net.potionstudios.netherdescent.world.item.custom.SoulFireChargeItem;
import net.potionstudios.netherdescent.world.item.custom.SythianScaffoldingBlockItem;
import net.potionstudios.netherdescent.world.item.equipment.NetherDescentArmorMaterials;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetherDescentItems {

    public static final ArrayList<Supplier<? extends Item>> ITEMS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> NO_LANG_ITEMS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> SIMPLE_ITEMS = new ArrayList<>();

    public static final Supplier<Item> BLUE_NETHER_BRICK = registerSimpleItem(NetherDescentItemIds.BLUE_NETHER_BRICK, Item::new, new Item.Properties());
    public static final Supplier<Item> EMBUR_GEL_BALL = registerSimpleItem(NetherDescentItemIds.EMBUR_GEL_BALL, Item::new, new Item.Properties());

    public static final Supplier<PlaceOnWaterBlockItem> EMBUR_LILY = registerItemNoLang(NetherDescentBlockItemIds.EMBUR_LILY, (properties) -> new PlaceOnWaterBlockItem(NetherDescentBlocks.EMBUR_LILY.get(), properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Supplier<SythianScaffoldingBlockItem> SYTHIAN_SCAFFOLDING = registerItemNoLang(NetherDescentBlockItemIds.SYTHIAN_SCAFFOLDING, (properties) -> new SythianScaffoldingBlockItem(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Supplier<Item> CRIMSON_BERRIES = registerSimpleItem(NetherDescentBlockItemIds.CRIMSON_BERRIES, (properties) -> new BlockItem(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get(), properties), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build()).component(DataComponents.CONSUMABLE, Consumable.builder().build()));
    public static final Supplier<Item> CRIMSON_BERRY_PIE = registerSimpleItem(NetherDescentItemIds.CRIMSON_BERRY_PIE, Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3F).build()).component(DataComponents.CONSUMABLE, Consumable.builder().build()));

    public static final Supplier<Item> RAW_PENDORITE = registerSimpleItem(NetherDescentItemIds.RAW_PENDORITE, Item::new, new Item.Properties());
    public static final Supplier<Item> PENDORITE_INGOT = registerSimpleItem(NetherDescentItemIds.PENDORITE_INGOT, Item::new, new Item.Properties());
    public static final Supplier<Item> PENDORITE_NUGGET = registerSimpleItem(NetherDescentItemIds.PENDORITE_NUGGET, Item::new, new Item.Properties());
    public static final Supplier<StandingAndWallBlockItem> PENDORITE_TORCH = registerItemNoLang(NetherDescentBlockItemIds.PENDORITE_TORCH, (properties) -> new StandingAndWallBlockItem(NetherDescentBlocks.PENDORITE_TORCH.get(), NetherDescentBlocks.PENDORITE_WALL_TORCH.get(), Direction.DOWN, properties), new Item.Properties().useBlockDescriptionPrefix());
    public static final Supplier<Item> PENDORITE_HORSE_ARMOR = registerSimpleItem(NetherDescentItemIds.PENDORITE_HORSE_ARMOR, (properties) -> new Item(properties.horseArmor(NetherDescentArmorMaterials.PENDORITE)), new Item.Properties());
    public static final Supplier<Item> PENDORITE_WOLF_ARMOR = registerItem(NetherDescentItemIds.PENDORITE_WOLF_ARMOR, (properties) -> new Item(properties.wolfArmor(NetherDescentArmorMaterials.PENDORITE)), new Item.Properties());

    public static final Supplier<Item> SOUL_BLAZE_ROD = registerSimpleItem(NetherDescentItemIds.SOUL_BLAZE_ROD, Item::new, new Item.Properties());
    public static final Supplier<Item> SOUL_BLAZE_POWDER = registerSimpleItem(NetherDescentItemIds.SOUL_BLAZE_POWDER, Item::new, new Item.Properties());
    public static final Supplier<SoulFireChargeItem> SOUL_FIRE_CHARGE = registerSimpleItem(NetherDescentItemIds.SOUL_FIRE_CHARGE, SoulFireChargeItem::new, new Item.Properties());
    public static final Supplier<FireChargeItem> PENDORITE_FIRE_CHARGE = registerSimpleItem(NetherDescentItemIds.PENDORITE_FIRE_CHARGE, FireChargeItem::new, new Item.Properties());

    public static final Supplier<Item> HORNET_NEST = registerItemNoLang(NetherDescentBlockItemIds.HORNET_NEST, (properties) -> new BlockItem(NetherDescentBlocks.HORNET_NEST.get(), properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Supplier<SpawnEggItem> SOUL_BLAZE_SPAWN_EGG = registerSpawnEgg(NetherDescentItemIds.SOUL_BLAZE_SPAWN_EGG, NetherDescentEntityTypes.SOUL_BLAZE);
    public static final Supplier<SpawnEggItem> PENDORITE_BLAZE_SPAWN_EGG = registerSpawnEgg(NetherDescentItemIds.PENDORITE_BLAZE_SPAWN_EGG, NetherDescentEntityTypes.PENDORITE_BLAZE);
    public static final Supplier<SpawnEggItem> HORNET_SPAWN_EGG = registerSpawnEgg(NetherDescentItemIds.HORNET_SPAWN_EGG, NetherDescentEntityTypes.HORNET);
    public static final Supplier<SpawnEggItem> SOUL_GHAST_SPAWN_EGG = registerSpawnEgg(NetherDescentItemIds.SOUL_GHAST_SPAWN_EGG, NetherDescentEntityTypes.SOUL_GHAST);

    public static <I extends Item> Supplier<I> registerSimpleItem(ResourceKey<Item> key, Function<Item.Properties, I> item, Item.Properties properties) {
        Supplier<I> supplier = registerItem(key, item, properties);
        if (PlatformHandler.PLATFORM_HANDLER.isDatagen()) SIMPLE_ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerSimpleItem(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        return registerSimpleItem(key(id), item, properties);
    }

    public static <I extends Item> Supplier<I> registerItem(ResourceKey<Item> key, Function<Item.Properties, I> item, Item.Properties properties) {
        Supplier<I> supplier = register(key, item, properties);
        ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerItem(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        return registerItem(key(id), item, properties);
    }

    public static <I extends Item> Supplier<I> registerItemNoLang(ResourceKey<Item> key, Function<Item.Properties, I> item, Item.Properties properties) {
        Supplier<I> supplier = register(key, item, properties);
        NO_LANG_ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerItemNoLang(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        return registerItemNoLang(key(id), item, properties);
    }

    public static <I extends Item> Supplier<I> register(ResourceKey<Item> key, Function<Item.Properties, I> item, Item.Properties properties) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, key.identifier().getPath(), () -> item.apply(properties.setId(key)));
    }

    public static <I extends Item> Supplier<I> register(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        return register(key(id), item, properties);
    }

    private static <E extends Entity> Supplier<SpawnEggItem> registerSpawnEgg(ResourceKey<Item> key, Supplier<EntityType<E>> entity) {
        Supplier<SpawnEggItem> egg = PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, key.identifier().getPath(), () -> new SpawnEggItem(new Item.Properties().setId(key).spawnEgg(entity.get())));
        SIMPLE_ITEMS.add(egg);
        ITEMS.add(egg);
        return egg;
    }

    private static <E extends Entity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<E>> entity) {
        return registerSpawnEgg(key(id), entity);
    }

    public static ResourceKey<Item> key(String id) {
        return NetherDescent.key(Registries.ITEM, id);
    }

    public static <I extends Item> Supplier<I> register(ResourceKey<Item> key, Supplier<I> item) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, key.identifier().getPath(), item);
    }

    public static <I extends Item> Supplier<I> register(String id, Supplier<I> item) {
        return register(key(id), item);
    }

    public static void items() {
        NetherDescent.LOGGER.info("Registering Nether Descent Items");
    }
}
