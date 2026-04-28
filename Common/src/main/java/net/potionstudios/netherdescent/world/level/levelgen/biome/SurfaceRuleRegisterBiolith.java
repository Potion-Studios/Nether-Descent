package net.potionstudios.netherdescent.world.level.levelgen.biome;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.minecraft.world.level.biome.Climate;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;

public class SurfaceRuleRegisterBiolith {

    public static void registerSurfaceRules() {
        NetherDescentBiomes.BIOME_FACTORIES.forEach((biomeKey, biomeFactory) -> {
            if (WorldGenerationConfig.get().isEnabled(biomeKey))
                BiomePlacement.addNether(biomeKey, Climate.parameters(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.2F, 0.0F));
        });
        SurfaceGeneration.addNetherSurfaceRules(NetherDescent.id("rules/nether"), NetherDescentSurfaceRules.makeRules());
    }
}
