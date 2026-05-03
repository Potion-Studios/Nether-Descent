package net.potionstudios.netherdescent.world.level.levelgen.biome;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;

public class BiolithRegister {

    public static void register() {
        NetherDescentBiomes.BIOME_FACTORIES.forEach((biomeKey, biomeDefinition) -> {
            if (WorldGenerationConfig.get().isEnabled(biomeKey))
                BiomePlacement.addNether(biomeKey, biomeDefinition.parameterPoint());
        });
        SurfaceGeneration.addNetherSurfaceRules(NetherDescent.id("rules/nether"), NetherDescentSurfaceRules.makeRules());
    }
}
