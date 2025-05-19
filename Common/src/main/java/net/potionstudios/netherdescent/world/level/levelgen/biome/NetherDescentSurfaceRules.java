package net.potionstudios.netherdescent.world.level.levelgen.biome;

import net.minecraft.world.level.levelgen.SurfaceRules;

/**
 * Surface rules for the overworld biomes in Nether Descent.
 * @see NetherDescentBiomes
 * @see terrablender.worldgen.TBSurfaceRuleData
 * @author Joseph T. McQuigg
 */
public class NetherDescentSurfaceRules {

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence();
    }

}
