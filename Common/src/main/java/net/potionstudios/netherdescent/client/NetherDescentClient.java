package net.potionstudios.netherdescent.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.client.model.HornetModel;
import net.potionstudios.netherdescent.client.model.geom.NetherDescentModelLayers;
import net.potionstudios.netherdescent.client.renderer.entity.HornetRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.PendoriteBlazeRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.SoulBlazeRenderer;
import net.potionstudios.netherdescent.client.renderer.entity.SoulGhastRenderer;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.core.particles.FallingParticle;
import net.potionstudios.netherdescent.core.particles.RisingParticle;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.EmburCaveMossBlock;
import net.potionstudios.netherdescent.world.level.block.custom.HangingMossyCarpetBlock;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import org.jetbrains.annotations.Nullable;

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
     * @see NetherDescentEntityType
     */
    public static void registerEntityRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> consumer) {
        consumer.accept(NetherDescentEntityType.SOUL_BLAZE.get(), SoulBlazeRenderer::new);
		consumer.accept(NetherDescentEntityType.SOUL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 3.0F, true));
		consumer.accept(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), context -> new ThrownItemRenderer<>(context, 0.75F, true));
		consumer.accept(NetherDescentEntityType.PENDORITE_BLAZE.get(), PendoriteBlazeRenderer::new);
        consumer.accept(NetherDescentEntityType.HORNET.get(), HornetRenderer::new);
		consumer.accept(NetherDescentEntityType.SOUL_GHAST.get(), SoulGhastRenderer::new);
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

	/**
	 * Registers the render types for the blocks.
	 */
	public static void registerBlockRenderTypes(BiConsumer<Block, ChunkSectionLayer> consumer) {
		NetherDescentBlocks.BLOCKS.forEach(entry -> {
			ChunkSectionLayer type = renderTypeBlock(entry.get());
			if (type != null) consumer.accept(entry.get(), type);
		});
		consumer.accept(NetherDescentBlocks.EMBUR_GEL_BLOCK.get(), ChunkSectionLayer.TRANSLUCENT);
		consumer.accept(NetherDescentBlocks.EMBUR.door(), ChunkSectionLayer.TRANSLUCENT);
		consumer.accept(NetherDescentBlocks.EMBUR.trapdoor(), ChunkSectionLayer.TRANSLUCENT);
		consumer.accept(NetherDescentBlocks.EMBUR_GEL_VINES.get(), ChunkSectionLayer.TRANSLUCENT);
		consumer.accept(NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get(), ChunkSectionLayer.TRANSLUCENT);
		consumer.accept(NetherDescentBlocks.CRIMSON_CARPET.get(), ChunkSectionLayer.CUTOUT);
		consumer.accept(NetherDescentBlocks.SYTHIAN_SHOOT.get(), ChunkSectionLayer.CUTOUT);
		consumer.accept(NetherDescentBlocks.SYTHIAN_STALK.get(), ChunkSectionLayer.CUTOUT);
		consumer.accept(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(), ChunkSectionLayer.CUTOUT);
		consumer.accept(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get(), ChunkSectionLayer.CUTOUT);
		consumer.accept(NetherDescentBlocks.THORN_SPROUT.get(), ChunkSectionLayer.CUTOUT);

	}

	@Nullable
	private static ChunkSectionLayer renderTypeBlock(Block block) {
		if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof BushBlock || block instanceof LanternBlock || block instanceof GlowLichenBlock
				|| block instanceof EmburCaveMossBlock || block instanceof MossyCarpetBlock || block instanceof HangingMossyCarpetBlock || block instanceof GrowingPlantBlock || block instanceof FlowerPotBlock || block instanceof HangingMossBlock
				|| block instanceof ScaffoldingBlock || block instanceof TransparentBlock || block instanceof RodBlock || block instanceof CampfireBlock || block instanceof BaseTorchBlock)
			return ChunkSectionLayer.CUTOUT;
		else if (block instanceof LeavesBlock || block instanceof VineBlock || block instanceof MangroveRootsBlock || block instanceof SporeBlossomBlock || block instanceof BaseCoralPlantTypeBlock || block instanceof IronBarsBlock || block instanceof ChainBlock)
			return ChunkSectionLayer.CUTOUT;
		else if (block instanceof StainedGlassPaneBlock || block instanceof HalfTransparentBlock)
			return ChunkSectionLayer.TRANSLUCENT;
		return null;
	}
}
