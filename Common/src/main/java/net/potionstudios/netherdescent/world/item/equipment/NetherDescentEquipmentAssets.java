package net.potionstudios.netherdescent.world.item.equipment;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.potionstudios.netherdescent.NetherDescent;

public interface NetherDescentEquipmentAssets {
    ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(NetherDescent.id("equipment_asset"));
    ResourceKey<EquipmentAsset> PENDORITE = createId("pendorite");

    static ResourceKey<EquipmentAsset> createId(final String name) {
        return ResourceKey.create(ROOT_ID, NetherDescent.id(name));
    }
}
