package net.potionstudios.netherdescent.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.*;
import net.potionstudios.netherdescent.client.NetherDescentClient;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.EmburCaveMossBlock;
import net.potionstudios.netherdescent.world.level.block.custom.HangingMossyCarpetBlock;

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
		NetherDescentClient.registerBlockRenderTypes(BlockRenderLayerMap.INSTANCE::putBlock);
        NetherDescentClient.registerEntityRenderers(EntityRendererRegistry::register);
		NetherDescentClient.registerBlockEntityRenderers(BlockEntityRenderers::register);
        NetherDescentClient.registerLayerDefinitions((a, b) -> EntityModelLayerRegistry.registerModelLayer(a, b::get));
        NetherDescentClient.registerParticles((type, spriteProviderFactory) -> ParticleFactoryRegistry.getInstance().register(type, spriteProviderFactory::apply));
    }
}
