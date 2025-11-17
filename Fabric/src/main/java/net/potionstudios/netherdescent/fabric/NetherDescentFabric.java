package net.potionstudios.netherdescent.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 */
public class NetherDescentFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        init();
    }

    protected static void init() {
        NetherDescent.init();
        VanillaCompatFabric.init();
        NetherDescent.commonSetup();
        NetherDescent.postInit();
        NetherDescentEntityType.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
    }
}
