package net.potionstudios.netherdescent.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.potionstudios.netherdescent.client.model.HornetModel;
import net.potionstudios.netherdescent.client.model.geom.NetherDescentModelLayers;
import net.potionstudios.netherdescent.client.renderer.entity.HornetRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.PendoriteBlazeRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.SoulBlazeRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.SoulGhastRenderer;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.core.particles.FallingParticle;
import net.potionstudios.netherdescent.core.particles.RisingParticle;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityTypes;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The common client class for Nether Descent.
 * This class is used for client-side-only code.
 * @author Joseph T. McQuigg
 */
public class NetherDescentClient {

	public static void onInitialize() {
	}

    /**
     * Registers the Particle Providers.
     * @see ParticleProvider
     */
    public static void registerParticles(BiConsumer<SimpleParticleType, Function<SpriteSet, ParticleProvider<SimpleParticleType>>> consumer) {
		consumer.accept(NetherDescentParticles.SYTHIAN_LEAF.get(), FallingLeavesParticle.CherryProvider::new);
	    consumer.accept(NetherDescentParticles.EMBUR_GEL_DRIP.get(), FallingParticle.Provider::new);
		consumer.accept(NetherDescentParticles.GILL_LEVITATE.get(), RisingParticle.Provider::new);
		consumer.accept(NetherDescentParticles.GILL_LEVITATE_POWERED.get(), RisingParticle.Provider::new);
        consumer.accept(NetherDescentParticles.PENDORITE_FIRE_FLAME.get(), FlameParticle.Provider::new);
	    consumer.accept(NetherDescentParticles.ARISIAN_LEAF.get(), FallingLeavesParticle.CherryProvider::new);
    }

    /**
     * Registers the entity renderers.
     * @see EntityRenderers
     * @see NetherDescentEntityTypes
     */
    public static void registerEntityRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> consumer) {
        consumer.accept(NetherDescentEntityTypes.SOUL_BLAZE.get(), SoulBlazeRenderer::new);
		consumer.accept(NetherDescentEntityTypes.SOUL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 3.0F, true));
		consumer.accept(NetherDescentEntityTypes.SMALL_SOUL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 0.75F, true));
		consumer.accept(NetherDescentEntityTypes.PENDORITE_BLAZE.get(), PendoriteBlazeRenderer::new);
        consumer.accept(NetherDescentEntityTypes.HORNET.get(), HornetRenderer::new);
		consumer.accept(NetherDescentEntityTypes.SOUL_GHAST.get(), SoulGhastRenderer::new);
    }

	/**
	 * Registers the block key renderers.
	 * @see BlockEntityRenderers
	 * @see NetherDescentBlockEntityType
	 */
	public static void registerBlockEntityRenderers(BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> consumer) {
		consumer.accept(NetherDescentBlockEntityType.SIGNS.get(), StandingSignRenderer::new);
		consumer.accept(NetherDescentBlockEntityType.HANGING_SIGNS.get(), HangingSignRenderer::new);
		consumer.accept(NetherDescentBlockEntityType.CAMPFIRE.get(), CampfireRenderer::new);
	}

    /**
     * Registers Model Layer Definitions
     * @see ModelLayerLocation
     */
    public static void registerLayerDefinitions(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> consumer) {
        consumer.accept(NetherDescentModelLayers.HORNET, HornetModel::createBodyLayer);
    }
}
