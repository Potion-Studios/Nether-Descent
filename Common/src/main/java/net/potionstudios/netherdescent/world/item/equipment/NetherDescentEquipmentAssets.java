package net.potionstudios.netherdescent.world.item.equipment;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.potionstudios.netherdescent.NetherDescent;

public interface NetherDescentEquipmentAssets {
	ResourceKey<EquipmentAsset> PENDORITE = createId("pendorite");

	static ResourceKey<EquipmentAsset> createId(String name) {
		return NetherDescent.key(EquipmentAssets.ROOT_ID, name);
	}
}
