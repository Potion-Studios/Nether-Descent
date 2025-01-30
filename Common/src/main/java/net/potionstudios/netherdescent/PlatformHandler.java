package net.potionstudios.netherdescent;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * This class handles the registration of all content
 * Also handles making custom objects that are needed for each platforms
 * @author Joseph T. McQuigg
 */
public interface PlatformHandler {

	PlatformHandler PLATFORM_HANDLER = load(PlatformHandler.class);

	/**
	 * Gets the name of the current platform
	 *
	 * @return The name of the current platform.
	 */
	Platform getPlatform();

	/**
	 * Gets the path to the config directory
	 * @return The path to the config directory
	 */
	Path configPath();

	/**
	 * Registers a block entity with the specified parameters
	 * @see BlockEntityType
	 * @param key The id/name of the block entity
	 * @param builder The builder for the block entity
	 * @return Supplier of the BlockEntityType
	 */
	<T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String key, Supplier<BlockEntityType.Builder<T>> builder);

	/**
	 * Creates a spawn egg with the specified parameters
	 * @see SpawnEggItem
	 * @param entity The entity to be spawned from the spawn egg
	 * @param backgroundColor The background color of the spawn egg
	 * @param highlightColor The highlight color of the spawn egg
	 * @return Supplier of the SpawnEggItem
	 */
	default Supplier<SpawnEggItem> createSpawnEgg(Supplier<EntityType<? extends Mob>> entity, int backgroundColor, int highlightColor) {
		return () -> new SpawnEggItem(entity.get(), backgroundColor, highlightColor, new Item.Properties());
	}

	/**
	 * Registers a potted block with the specified block
	 * @see FlowerPotBlock
	 * @param block The block to be potted
	 * @return Supplier of the FlowerPotBlock
	 */
	default Supplier<FlowerPotBlock> createPottedBlock(Supplier<? extends Block> block) {
		return () -> new FlowerPotBlock(block.get(), FlowerPotBlock.Properties.ofFullCopy(Blocks.FLOWER_POT));
	}

	/**
	 * Creates a mob bucket with the specified parameters
	 * @see MobBucketItem
	 * @param entity The entity to be spawned from the mob bucket
	 * @param fluid The fluid to be stored in the mob bucket
	 * @param sound The sound to be played when the mob bucket is used
	 * @return Supplier of the MobBucketItem
	 */
	default Supplier<MobBucketItem> createMobBucket(Supplier<EntityType<? extends Mob>> entity, Supplier<Fluid> fluid, Supplier<SoundEvent> sound) {
		return () -> new MobBucketItem(entity.get(), fluid.get(), sound.get(), new Item.Properties().stacksTo(1));
	}

	/**
	 * Registers a wood type with the specified id and block set type
	 * @see WoodType
	 * @param id The id/name of the wood type
	 * @param setType The block set type of the wood type
	 * @return WoodType
	 */
	WoodType createWoodType(String id, @NotNull BlockSetType setType);

	/**
	 * Registers a particle with the specified id
	 * @see SimpleParticleType
	 * @param name The id/name of the particle
	 * @return Supplier of the SimpleParticleType
	 */
	Supplier<SimpleParticleType> registerCreateParticle(String name);

	/**
	 * Creates and Registers a creative tab
	 * @see CreativeModeTab
	 * @param name The name of the creative tab
	 * @param icon The icon of the creative tab
	 * @param items The items to be added to the creative tab
	 * @return Supplier of the CreativeModeTab
	 */
	Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items);

	private static <T> T load(Class<T> clazz) {
		final T loadedService = ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
		NetherDescent.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
		return loadedService;
	}

	/**
	 * Registers a value with the specified name and registry
	 * @param registry The registry to register the value with
	 * @param name The name of the value
	 * @param value the value to be registered
	 * @return Supplier of the value
	 * @param <T> The type of the value
	 */
	<T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value);

	/**
	 * Registers a value with the specified name and registry and returns a Holder.Reference of the value
	 * @param registry The registry to register the value with
	 * @param name The name of the value
	 * @param value the value to be registered
	 * @return Supplier of a Holder.Reference of the value
	 * @param <T> The type of the value
	 */
	<T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value);

	enum Platform {
		FORGE,
		FABRIC,
		NEOFORGE
	}
}
