package net.potionstudios.netherdescent.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.client.NetherDescentClient;

/**
 * This class is used to initialize the Forge client side of the mod.
 * @see NetherDescentClient
 * @author Joseph T. McQuigg
 */
@Mod(value = NetherDescent.MOD_ID, dist = Dist.CLIENT)
public class NetherDescentClientNeoForge {

	/**
	 * Constructor for the client side of the NeoForge mod.
	 * @param eventBus The event bus to register the client side of the mod to.
	 */
	public NetherDescentClientNeoForge(final IEventBus eventBus) {
		eventBus.addListener((FMLClientSetupEvent event) -> NetherDescentClient.onInitialize());
        eventBus.addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            NetherDescentClient.registerEntityRenderers(event::registerEntityRenderer);
            NetherDescentClient.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        });
        eventBus.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) -> NetherDescentClient.registerLayerDefinitions(event::registerLayerDefinition));
        eventBus.addListener((RegisterParticleProvidersEvent event) -> NetherDescentClient.registerParticles((type, spriteProviderFactory) -> event.registerSpriteSet(type, spriteProviderFactory::apply)));
    }
}
