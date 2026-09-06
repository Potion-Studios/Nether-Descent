package net.potionstudios.netherdescent.forge.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.potionstudios.netherdescent.client.NetherDescentClient;

/**
 * This class is used to initialize the Forge client side of the mod.
 * @see NetherDescentClient
 * @author Joseph T. McQuigg
 */
@OnlyIn(Dist.CLIENT)
public class NetherDescentClientForge {

	/**
	 * Initializes the client side of the Forge mod.
	 * @param eventBus The event bus to register the client side of the mod to.
	 */
	public static void init(final BusGroup eventBus) {
		FMLClientSetupEvent.getBus(eventBus).addListener((FMLClientSetupEvent event) -> {
			NetherDescentClient.onInitialize();
			NetherDescentClient.registerBlockRenderTypes(ItemBlockRenderTypes::setRenderLayer);
		});
        EntityRenderersEvent.RegisterRenderers.BUS.addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            NetherDescentClient.registerEntityRenderers(event::registerEntityRenderer);
            NetherDescentClient.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        });
		EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener((EntityRenderersEvent.RegisterLayerDefinitions event) -> NetherDescentClient.registerLayerDefinitions(event::registerLayerDefinition));
		RegisterParticleProvidersEvent.BUS.addListener((RegisterParticleProvidersEvent event) -> NetherDescentClient.registerParticles((type, spriteProviderFactory) -> event.registerSpriteSet(type, spriteProviderFactory::apply)));
	}
}
