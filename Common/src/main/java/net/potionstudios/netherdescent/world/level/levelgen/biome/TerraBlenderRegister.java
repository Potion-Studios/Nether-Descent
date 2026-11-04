package net.potionstudios.netherdescent.world.level.levelgen.biome;

import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;
import net.potionstudios.netherdescent.world.level.levelgen.regions.NetherDescentRegion;
import terrablender.api.MaterialRuleManager;
import terrablender.api.Regions;

public class TerraBlenderRegister {
    public static void register() {
        Regions.register(new NetherDescentRegion(
                NetherDescent.id("nether_descent"),
                WorldGenerationConfig.get().regionWeight.value()
        ));
        MaterialRuleManager.addRules(MaterialRuleManager.RuleCategory.NETHER, NetherDescent.MOD_ID, NetherDescentMaterialRules.NETHER);
    }
}
