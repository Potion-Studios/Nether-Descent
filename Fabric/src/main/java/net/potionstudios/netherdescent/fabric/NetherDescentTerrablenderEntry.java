package net.potionstudios.netherdescent.fabric;

import net.potionstudios.netherdescent.world.level.levelgen.biome.RegisterTerraBlender;
import terrablender.api.TerraBlenderApi;

public class NetherDescentTerrablenderEntry implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        NetherDescentFabric.init();
        RegisterTerraBlender.register();
    }
}
