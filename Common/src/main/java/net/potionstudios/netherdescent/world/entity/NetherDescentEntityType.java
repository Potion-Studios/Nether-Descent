package net.potionstudios.netherdescent.world.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import net.potionstudios.netherdescent.world.entity.monster.PendoriteBlaze;
import net.potionstudios.netherdescent.world.entity.monster.SoulBlaze;
import net.potionstudios.netherdescent.world.entity.monster.SoulGhast;
import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NetherDescentEntityType {

    public static final Supplier<EntityType<SoulBlaze>> SOUL_BLAZE = createEntity("soul_blaze", EntityType.Builder.of(SoulBlaze::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 1.8F).clientTrackingRange(8));
    public static final Supplier<EntityType<SmallSoulFireball>> SMALL_SOUL_FIREBALL = createEntity("small_soul_fireball", EntityType.Builder.<SmallSoulFireball>of(SmallSoulFireball::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10));
	public static final Supplier<EntityType<PendoriteBlaze>> PENDORITE_BLAZE = createEntity("pendorite_blaze", EntityType.Builder.of(PendoriteBlaze::new, MobCategory.CREATURE).fireImmune().sized(0.6F, 1.8F).clientTrackingRange(8));
    public static final Supplier<EntityType<Hornet>> HORNET = createEntity("hornet", EntityType.Builder.of(Hornet::new, MobCategory.CREATURE).sized(0.7F, 0.6F).eyeHeight(0.3F).clientTrackingRange(8).fireImmune());
    public static final Supplier<EntityType<SoulGhast>> SOUL_GHAST = createEntity("soul_ghast", EntityType.Builder.of(SoulGhast::new, MobCategory.MONSTER).fireImmune().sized(4.0F, 4.0F).eyeHeight(2.6F).passengerAttachments(4.0625F).ridingOffset(0.5F).clientTrackingRange(10));

    private static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.Builder<E> Builder) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ENTITY_TYPE, id, () -> Builder.build(id));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Mob> void registerSpawnPlacements(Consumer<SpawnPlacement<T>> consumer) {
        consumer.accept((SpawnPlacement<T>) new SpawnPlacement<>(SOUL_GHAST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SoulGhast::checkSoulGhastSpawnRules));
        consumer.accept((SpawnPlacement<T>) new SpawnPlacement<>(SOUL_BLAZE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules));
    }

    public record SpawnPlacement<T extends Mob>(EntityType<T> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {}


    /**
     * Registers Entity Attributes
     */
    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> consumer) {
        consumer.accept(SOUL_BLAZE.get(), SoulBlaze.createAttributes().build());
		consumer.accept(PENDORITE_BLAZE.get(), PendoriteBlaze.createAttributes().build());
        consumer.accept(HORNET.get(), Hornet.createAttributes().build());
        consumer.accept(SOUL_GHAST.get(), SoulGhast.createAttributes().build());
    }

    public static void entityTypes() {
        NetherDescent.LOGGER.info("Registering Nether Descent Entity Types");
    }
}
