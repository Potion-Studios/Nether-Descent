package net.potionstudios.netherdescent.fabric;

import net.fabricmc.api.ModInitializer;
import net.potionstudios.netherdescent.NetherDescent;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 */
public class NetherDescentFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        NetherDescent.init();
        VanillaCompatFabric.init();
    }
}
