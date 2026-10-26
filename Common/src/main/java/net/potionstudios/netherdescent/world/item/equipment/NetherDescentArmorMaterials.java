package net.potionstudios.netherdescent.world.item.equipment;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentItemTags;

public interface NetherDescentArmorMaterials {
    ArmorMaterial PENDORITE = new ArmorMaterial(37, ArmorMaterials.makeDefense(3, 6, 8, 3, 20), 15, SoundEvents.ARMOR_EQUIP_COPPER, 3.0F, 0F, NetherDescentItemTags.INGOTS_PENDORITE, NetherDescentEquipmentAssets.PENDORITE);

    static void armorMaterials() {
        NetherDescent.LOGGER.info("Registering Nether Descent Armor Materials");
    }
}
