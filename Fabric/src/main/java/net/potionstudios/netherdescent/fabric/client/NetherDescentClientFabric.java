package net.potionstudios.netherdescent.fabric.client;

import net.fabricmc.api.ClientModInitializer;
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
	}
}
