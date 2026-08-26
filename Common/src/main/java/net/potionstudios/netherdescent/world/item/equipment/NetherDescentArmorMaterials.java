package net.potionstudios.netherdescent.world.item.equipment;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentItemTags;

import java.util.EnumMap;

public interface NetherDescentArmorMaterials {
    ArmorMaterial PENDORITE = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), enumMap -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 6);
        enumMap.put(ArmorType.HELMET, 2);
        enumMap.put(ArmorType.BODY, 12);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 1.5F, 0.0F, NetherDescentItemTags.REPAIRS_PENDORITE_ARMOR, NetherDescentEquipmentAssets.PENDORITE);

    static void armorMaterials() {
        NetherDescent.LOGGER.info("Registering Nether Descent Armor Materials");
    }
}
