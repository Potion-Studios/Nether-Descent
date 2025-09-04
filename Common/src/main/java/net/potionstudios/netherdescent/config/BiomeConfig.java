package net.potionstudios.netherdescent.config;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BiomeConfig {

    private static final String FILE_NAME = "biomes";

    private static BiomeConfig INSTANCE;

    public static BiomeConfig get() {
        return INSTANCE;
    }

    public static void init() {
        BiomeConfig cfg = ConfigLoader.loadConfig(BiomeConfig.class, FILE_NAME);
        boolean changed = cfg.ensureAllKnownBiomesPresent();
        if (changed)
            ConfigLoader.saveConfig(cfg, FILE_NAME);
        INSTANCE = cfg;
    }

    // Keep order stable for nicer diffs
    public Map<String, Boolean> biomes = new LinkedHashMap<>();

    public boolean isEnabled(ResourceKey<Biome> key) {
        String id = key.location().toString().replace("netherdescent:", "");
        return biomes.getOrDefault(id, true);
    }

    private boolean ensureAllKnownBiomesPresent() {
        boolean changed = false;
        for (ResourceKey<Biome> key : NetherDescentBiomes.BIOME_FACTORIES.keySet()) {
            String id = key.location().toString().replace("netherdescent:", "");
            if (!biomes.containsKey(id)) {
                biomes.put(id, true); // default enabled
                changed = true;
            }
        }
        return changed;
    }
}