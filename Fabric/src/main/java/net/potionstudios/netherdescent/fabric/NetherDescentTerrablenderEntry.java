package net.potionstudios.netherdescent.fabric;

import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class NetherDescentTerrablenderEntry implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, NetherDescent.MOD_ID, NetherDescentSurfaceRules.makeRules());
    }
}
