package net.potionstudios.netherdescent.config.configs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.netherdescent.config.ConfigLoader;
import net.potionstudios.netherdescent.config.ConfigUtils;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;

import java.util.LinkedHashMap;
import java.util.Map;

public final class WorldGenerationConfig {

    private static final String FILE_NAME = "world_generation";
    private static final int DEFAULT_REGION_WEIGHT = 10;

    private static WorldGenerationConfig INSTANCE;

    public static WorldGenerationConfig get() {
        return INSTANCE;
    }

    public static void init() {
        WorldGenerationConfig cfg = ConfigLoader.loadConfig(WorldGenerationConfig.class, FILE_NAME);
        boolean changed = cfg.ensureAllKnownBiomesPresent();
        if (changed)
            ConfigLoader.saveConfig(cfg, FILE_NAME);
        INSTANCE = cfg;
    }

    public ConfigUtils.CommentValue<Map<String, Boolean>> biomes = ConfigUtils.CommentValue.of("Only for Terrablender and Biolith", new LinkedHashMap<>());
    public ConfigUtils.CommentValue<Integer> regionWeight = ConfigUtils.CommentValue.of("Only used when using with Terrablender", DEFAULT_REGION_WEIGHT);
    public boolean blue_fortress = true;

    public boolean isEnabled(ResourceKey<Biome> key) {
        String id = key.location().toString().replace("netherdescent:", "");
        return biomes.value().getOrDefault(id, true);
    }

    private boolean ensureAllKnownBiomesPresent() {
        boolean changed = false;
        for (ResourceKey<Biome> key : NetherDescentBiomes.BIOME_FACTORIES.keySet()) {
            String id = key.location().toString().replace("netherdescent:", "");
            if (!biomes.value().containsKey(id)) {
                biomes.value().put(id, true);
                changed = true;
            }
        }
        return changed;
    }
}