package net.potionstudios.netherdescent.world.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.entity.monster.SoulBlaze;
import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class NetherDescentEntityType {

    public static final Supplier<EntityType<SoulBlaze>> SOUL_BLAZE = createEntity("soul_blaze", EntityType.Builder.of(SoulBlaze::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 1.8F).clientTrackingRange(8));
    public static final Supplier<EntityType<SmallSoulFireball>> SMALL_SOUL_FIREBALL = createEntity("small_soul_fireball", EntityType.Builder.<SmallSoulFireball>of(SmallSoulFireball::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10));

    private static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.Builder<E> Builder) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ENTITY_TYPE, id, () -> Builder.build(id));
    }

    /**
     * Registers Entity Attributes
     */
    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> consumer) {
        consumer.accept(SOUL_BLAZE.get(), SoulBlaze.createAttributes().build());
    }

    public static void entityTypes() {
        NetherDescent.LOGGER.info("Registering Nether Descent Entity Types");
    }
}
