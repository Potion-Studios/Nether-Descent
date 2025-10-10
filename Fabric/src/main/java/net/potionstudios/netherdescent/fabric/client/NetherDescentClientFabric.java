package net.potionstudios.netherdescent.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.*;
import net.potionstudios.netherdescent.client.NetherDescentClient;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.EmburCaveMossBlock;
import net.potionstudios.netherdescent.world.level.block.custom.HangingMossBlock;
import net.potionstudios.netherdescent.world.level.block.custom.MossyCarpetBlock;

/**
 * Initializes the Fabric client.
 * @see ClientModInitializer#onInitializeClient()
 * @see NetherDescentClient
 * @author Joseph T. McQuigg
 */
public class NetherDescentClientFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		NetherDescentClient.onInitialize();
		registerRenderTypes();
		NetherDescentClient.registerBlockEntityRenderers(BlockEntityRenderers::register);
        NetherDescentClient.registerParticles((type, spriteProviderFactory) -> ParticleFactoryRegistry.getInstance().register(type, spriteProviderFactory::apply));
    }

	/**
	 * Registers the render types for the blocks.
	 * @see BlockRenderLayerMap
	 */
	private void registerRenderTypes() {
		NetherDescentBlocks.BLOCKS.forEach(entry -> renderTypeBlock(entry.get()));
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), NetherDescentBlocks.EMBUR_GEL_BLOCK.get(), NetherDescentBlocks.EMBUR.door(), NetherDescentBlocks.EMBUR.trapdoor(),
		        NetherDescentBlocks.EMBUR_GEL_VINES.get(), NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get());
	}

	private void renderTypeBlock(Block block) {
		if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof BushBlock || block instanceof LanternBlock || block instanceof GlowLichenBlock
		|| block instanceof EmburCaveMossBlock || block instanceof MossyCarpetBlock || block instanceof GrowingPlantBlock || block instanceof FlowerPotBlock || block instanceof HangingMossBlock
        || block instanceof ScaffoldingBlock || block instanceof TransparentBlock)
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
		else if (block instanceof LeavesBlock || block instanceof VineBlock || block instanceof MangroveRootsBlock || block instanceof SporeBlossomBlock || block instanceof BaseCoralPlantTypeBlock)
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutoutMipped());
		else if (block instanceof StainedGlassPaneBlock || block instanceof HalfTransparentBlock)
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent());
	}
}
