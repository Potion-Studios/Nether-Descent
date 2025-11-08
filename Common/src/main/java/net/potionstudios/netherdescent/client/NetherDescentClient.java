package net.potionstudios.netherdescent.client;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.core.particles.FallingParticle;
import net.potionstudios.netherdescent.world.level.block.entities.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.function.BiConsumer;
import java.util.function.Function;

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
//		consumer.accept(NetherDescentParticles.SYTHIAN_LEAF.get(), null);
	    consumer.accept(NetherDescentParticles.EMBUR_GEL_DRIP.get(), FallingParticle.Provider::new);
//		consumer.accept(NetherDescentParticles.GILL_LEVITATE.get(), null);
//		consumer.accept(NetherDescentParticles.GILL_LEVITATE_POWERED.get(), null);
    }

	/**
	 * Registers the block key renderers.
	 * @see BlockEntityRenderers
	 * @see NetherDescentBlockEntityType
	 */
	public static void registerBlockEntityRenderers(BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> consumer) {
		consumer.accept(NetherDescentBlockEntityType.SIGNS.get(), SignRenderer::new);
		consumer.accept(NetherDescentBlockEntityType.HANGING_SIGNS.get(), HangingSignRenderer::new);
	}
}
