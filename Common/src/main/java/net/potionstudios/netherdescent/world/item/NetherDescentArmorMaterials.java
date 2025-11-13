package net.potionstudios.netherdescent.world.item;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class NetherDescentArmorMaterials {

    public static final Supplier<Holder.Reference<ArmorMaterial>> PENDORITE = register("pendorite", Util.make(new EnumMap<>(ArmorItem.Type.class), (enumMap) -> {
        enumMap.put(ArmorItem.Type.BOOTS, 2);
        enumMap.put(ArmorItem.Type.LEGGINGS, 5);
        enumMap.put(ArmorItem.Type.CHESTPLATE, 6);
        enumMap.put(ArmorItem.Type.HELMET, 2);
        enumMap.put(ArmorItem.Type.BODY, 5);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(NetherDescentItems.PENDORITE_INGOT.get()));

    private static Supplier<Holder.Reference<ArmorMaterial>> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngridient) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(NetherDescent.id(name)));

        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);

        for(ArmorItem.Type type : ArmorItem.Type.values())
            enumMap.put(type, defense.get(type));

        return PlatformHandler.PLATFORM_HANDLER.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, name, () -> new ArmorMaterial(enumMap, enchantmentValue, equipSound, repairIngridient, layers, toughness, knockbackResistance));
    }

    public static void armorMaterials() {
        NetherDescent.LOGGER.info("Registering Nether Descent Armor Materials");
    }
}
