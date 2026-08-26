package net.potionstudios.netherdescent.world.item;

import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.equipment.ArmorType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
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

    public static final Supplier<Item> BLUE_NETHER_BRICK = registerSimpleItem("blue_nether_brick", Item::new, new Item.Properties());
    public static final Supplier<Item> EMBUR_GEL_BALL = registerSimpleItem("embur_gel_ball", Item::new, new Item.Properties());

    public static final Supplier<PlaceOnWaterBlockItem> EMBUR_LILY = registerItemNoLang("embur_lily", (properties) -> new PlaceOnWaterBlockItem(NetherDescentBlocks.EMBUR_LILY.get(), properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Supplier<SythianScaffoldingBlockItem> SYTHIAN_SCAFFOLDING = registerItemNoLang("sythian_scaffolding", (properties) -> new SythianScaffoldingBlockItem(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Supplier<Item> CRIMSON_BERRIES = registerSimpleItem("crimson_berries", (properties) -> new BlockItem(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get(), properties), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build()).component(DataComponents.CONSUMABLE, Consumable.builder().build()));
    public static final Supplier<Item> CRIMSON_BERRY_PIE = registerSimpleItem("crimson_berry_pie", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.3F).build()).component(DataComponents.CONSUMABLE, Consumable.builder().build()));

    public static final Supplier<Item> RAW_PENDORITE = registerSimpleItem("raw_pendorite", Item::new, new Item.Properties());
    public static final Supplier<Item> PENDORITE_INGOT = registerSimpleItem("pendorite_ingot", Item::new, new Item.Properties());
    public static final Supplier<Item> PENDORITE_NUGGET = registerSimpleItem("pendorite_nugget", Item::new, new Item.Properties());
    public static final Supplier<StandingAndWallBlockItem> PENDORITE_TORCH = registerItemNoLang("pendorite_torch", (properties) -> new StandingAndWallBlockItem(NetherDescentBlocks.PENDORITE_TORCH.get(), NetherDescentBlocks.PENDORITE_WALL_TORCH.get(), Direction.DOWN, properties), new Item.Properties().useBlockDescriptionPrefix());
    public static final Supplier<AnimalArmorItem> PENDORITE_HORSE_ARMOR = registerSimpleItem("pendorite_horse_armor", (properties) -> new AnimalArmorItem(NetherDescentArmorMaterials.PENDORITE, AnimalArmorItem.BodyType.EQUESTRIAN, properties), new Item.Properties().stacksTo(1));
    public static final Supplier<AnimalArmorItem> PENDORITE_WOLF_ARMOR = registerItem("pendorite_wolf_armor", (properties) -> new AnimalArmorItem(NetherDescentArmorMaterials.PENDORITE, AnimalArmorItem.BodyType.CANINE, properties), new Item.Properties().durability(ArmorType.BODY.getDurability(4)));

    public static final Supplier<Item> SOUL_BLAZE_ROD = registerSimpleItem("soul_blaze_rod", Item::new, new Item.Properties());
    public static final Supplier<Item> SOUL_BLAZE_POWDER = registerSimpleItem("soul_blaze_powder", Item::new, new Item.Properties());
    public static final Supplier<SoulFireChargeItem> SOUL_FIRE_CHARGE = registerSimpleItem("soul_fire_charge", SoulFireChargeItem::new, new Item.Properties());
    public static final Supplier<FireChargeItem> PENDORITE_FIRE_CHARGE = registerSimpleItem("pendorite_fire_charge", FireChargeItem::new, new Item.Properties());

    public static final Supplier<Item> HORNET_NEST = registerItemNoLang("hornet_nest", (properties) -> new BlockItem(NetherDescentBlocks.HORNET_NEST.get(), properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Supplier<SpawnEggItem> SOUL_BLAZE_SPAWN_EGG = registerSpawnEgg("soul_blaze_spawn_egg", NetherDescentEntityType.SOUL_BLAZE);
	public static final Supplier<SpawnEggItem> PENDORITE_BLAZE_SPAWN_EGG = registerSpawnEgg("pendorite_blaze_spawn_egg", NetherDescentEntityType.PENDORITE_BLAZE);
    public static final Supplier<SpawnEggItem> HORNET_SPAWN_EGG = registerSpawnEgg("hornet_spawn_egg", NetherDescentEntityType.HORNET);
    public static final Supplier<SpawnEggItem> SOUL_GHAST_SPAWN_EGG = registerSpawnEgg("soul_ghast_spawn_egg", NetherDescentEntityType.SOUL_GHAST);

    public static <I extends Item> Supplier<I> registerSimpleItem(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        Supplier<I> supplier = registerItem(id, item, properties);
        if (PlatformHandler.PLATFORM_HANDLER.isDatagen()) SIMPLE_ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerItem(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        Supplier<I> supplier = register(id, item, properties);
        ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerItemNoLang(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        Supplier<I> supplier = register(id, item, properties);
        NO_LANG_ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> register(String id, Function<Item.Properties, I> item, Item.Properties properties) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, id, () -> item.apply(properties.setId(key(id))));
    }

    private static <E extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<E>> entity) {
        Supplier<SpawnEggItem> egg = PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, id, () -> new SpawnEggItem(entity.get(), new Item.Properties().setId(key(id))));
        ITEMS.add(egg);
        return egg;
    }

    private static ResourceKey<Item> key(String id) {
        return NetherDescent.key(Registries.ITEM, id);
    }

    public static <I extends Item> Supplier<I> register(String id, Supplier<I> item) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, id, item);
    }

    public static void items() {
        NetherDescent.LOGGER.info("Registering Nether Descent Items");
    }
}
