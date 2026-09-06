package net.potionstudios.netherdescent.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.potionstudios.netherdescent.client.NetherDescentClient;

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
        NetherDescentClient.registerEntityRenderers(EntityRenderers::register);
		NetherDescentClient.registerBlockEntityRenderers(BlockEntityRenderers::register);
        NetherDescentClient.registerLayerDefinitions((a, b) -> EntityModelLayerRegistry.registerModelLayer(a, b::get));
        NetherDescentClient.registerParticles((type, spriteProviderFactory) -> ParticleProviderRegistry.getInstance().register(type, spriteProviderFactory::apply));
    }
}
