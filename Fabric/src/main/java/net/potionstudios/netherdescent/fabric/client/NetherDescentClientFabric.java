package net.potionstudios.netherdescent.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.*;
import net.potionstudios.netherdescent.client.NetherDescentClient;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

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
	}

	/**
	 * Registers the render types for the blocks.
	 * @see BlockRenderLayerMap
	 */
	private void registerRenderTypes() {
		NetherDescentBlocks.BLOCKS.forEach(entry -> renderTypeBlock(entry.get()));
	}

	private void renderTypeBlock(Block block) {
		if (block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof BushBlock || block instanceof LanternBlock || block instanceof GlowLichenBlock)
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
		else if (block instanceof LeavesBlock || block instanceof VineBlock || block instanceof MangroveRootsBlock
				|| block instanceof FlowerPotBlock || block instanceof SporeBlossomBlock || block instanceof BaseCoralPlantTypeBlock)
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutoutMipped());
		else if (block instanceof StainedGlassPaneBlock || block instanceof HalfTransparentBlock)
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent());
	}
}
