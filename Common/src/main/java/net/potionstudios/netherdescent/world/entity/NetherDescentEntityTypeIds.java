package net.potionstudios.netherdescent.world.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.potionstudios.netherdescent.NetherDescent;

public class NetherDescentEntityTypeIds {
    public static final ResourceKey<EntityType<?>> SOUL_BLAZE = create("soul_blaze");
    public static final ResourceKey<EntityType<?>> SOUL_FIREBALL = create("soul_fireball");
    public static final ResourceKey<EntityType<?>> SMALL_SOUL_FIREBALL = create("small_soul_fireball");
    public static final ResourceKey<EntityType<?>> PENDORITE_BLAZE = create("pendorite_blaze");
    public static final ResourceKey<EntityType<?>> HORNET = create("hornet");
    public static final ResourceKey<EntityType<?>> SOUL_GHAST = create("soul_ghast");

    private static ResourceKey<EntityType<?>> create(final String name) {
        return NetherDescent.key(Registries.ENTITY_TYPE, name);
    }
}
