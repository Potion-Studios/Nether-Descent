package net.potionstudios.netherdescent.fabric;

import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.config.WorldGenerationConfig;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import net.potionstudios.netherdescent.world.level.levelgen.regions.NetherDescentRegion;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class NetherDescentTerrablenderEntry implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        NetherDescentFabric.init();
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, NetherDescent.MOD_ID, NetherDescentSurfaceRules.makeRules());
        Regions.register(new NetherDescentRegion(
                NetherDescent.id("nether_descent"),
                WorldGenerationConfig.get().regionWeight
        ));
    }
}
