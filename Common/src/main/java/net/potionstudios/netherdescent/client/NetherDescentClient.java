package net.potionstudios.netherdescent.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.client.model.HornetModel;
import net.potionstudios.netherdescent.client.model.geom.NetherDescentModelLayers;
import net.potionstudios.netherdescent.client.renderer.entity.HornetRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.PendoriteBlazeRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.SoulBlazeRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.SoulGhastRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.nertling.NertlingRenderer;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.core.particles.FallingParticle;
import net.potionstudios.netherdescent.core.particles.RisingParticle;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

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
		NetherDescentWoodSet.woodsets().forEach(set -> registerWoodTypes(set.woodType()));
	}

	/**
	 * Registers the wood types for the sign materials.
	 * @param woodType the wood type to register
	 */
	private static void registerWoodTypes(WoodType woodType) {
		Sheets.SIGN_MATERIALS.put(woodType, Sheets.createSignMaterial(woodType));
		Sheets.HANGING_SIGN_MATERIALS.put(woodType, Sheets.createHangingSignMaterial(woodType));
	}

    /**
     * Registers the Particle Providers.
     * @see ParticleProvider
     */
    public static void registerParticles(BiConsumer<SimpleParticleType, Function<SpriteSet, ParticleProvider<SimpleParticleType>>> consumer) {
		consumer.accept(NetherDescentParticles.SYTHIAN_LEAF.get(), arg -> (arg2, arg3, d, e, f, g, h, i) -> new CherryParticle(arg3, d, e, f, arg));
	    consumer.accept(NetherDescentParticles.EMBUR_GEL_DRIP.get(), FallingParticle.Provider::new);
		consumer.accept(NetherDescentParticles.GILL_LEVITATE.get(), RisingParticle.Provider::new);
		consumer.accept(NetherDescentParticles.GILL_LEVITATE_POWERED.get(), RisingParticle.Provider::new);
        consumer.accept(NetherDescentParticles.PENDORITE_FIRE_FLAME.get(), FlameParticle.Provider::new);
	    consumer.accept(NetherDescentParticles.ARISIAN_LEAF.get(), arg -> (arg2, arg3, d, e, f, g, h, i) -> new CherryParticle(arg3, d, e, f, arg));
    }

    /**
     * Registers the entity renderers.
     * @see EntityRenderers
     * @see NetherDescentEntityType
     */
    public static void registerEntityRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> consumer) {
        consumer.accept(NetherDescentEntityType.SOUL_BLAZE.get(), SoulBlazeRenderer::new);
		consumer.accept(NetherDescentEntityType.SOUL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 3.0F, true));
		consumer.accept(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 0.75F, true));
		consumer.accept(NetherDescentEntityType.PENDORITE_BLAZE.get(), PendoriteBlazeRenderer::new);
        consumer.accept(NetherDescentEntityType.HORNET.get(), HornetRenderer::new);
		consumer.accept(NetherDescentEntityType.SOUL_GHAST.get(), SoulGhastRenderer::new);
		consumer.accept(NetherDescentEntityType.NERTLING.get(), NertlingRenderer::new);
    }

	/**
	 * Registers the block key renderers.
	 * @see BlockEntityRenderers
	 * @see NetherDescentBlockEntityType
	 */
	public static void registerBlockEntityRenderers(BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> consumer) {
		consumer.accept(NetherDescentBlockEntityType.SIGNS.get(), SignRenderer::new);
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
