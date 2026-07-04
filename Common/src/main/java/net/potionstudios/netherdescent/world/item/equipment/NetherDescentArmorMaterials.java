package net.potionstudios.netherdescent.world.item.equipment;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentItemTags;

public interface NetherDescentArmorMaterials {
    ArmorMaterial PENDORITE = new ArmorMaterial(11, ArmorMaterials.makeDefense(1, 3, 4, 2, 4), 8, SoundEvents.ARMOR_EQUIP_COPPER, 0.0F, 0.0F, NetherDescentItemTags.REPAIRS_PENDORITE_ARMOR, NetherDescentEquipmentAssets.PENDORITE);

    static void armorMaterials() {
        NetherDescent.LOGGER.info("Registering Nether Descent Armor Materials");
    }
}
