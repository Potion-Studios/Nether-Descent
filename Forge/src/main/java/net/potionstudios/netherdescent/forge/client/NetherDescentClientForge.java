package net.potionstudios.netherdescent.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
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
	public static void init(final IEventBus eventBus) {
		eventBus.addListener((FMLClientSetupEvent event) -> NetherDescentClient.onInitialize());
	}
}
