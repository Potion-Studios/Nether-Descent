package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.potionstudios.netherdescent.NetherDescent;

public class NetherDescentEntityTypeTags {
	public static final TagKey<EntityType<?>> SOUL_FIRE_FLAME = create("soul_fire_flame");

	private static TagKey<EntityType<?>> create(String name) {
		return TagKey.create(Registries.ENTITY_TYPE, NetherDescent.id(name));
	}
}
