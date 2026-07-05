package net.potionstudios.netherdescent.config.configs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.netherdescent.config.ConfigLoader;
import net.potionstudios.netherdescent.config.ConfigUtils;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class WorldGenerationConfig {
    private static final String FILE_NAME = "world_generation";
    private static final int DEFAULT_REGION_WEIGHT = 10;

    private static final WorldGenerationConfig INSTANCE = ConfigLoader.loadConfig(WorldGenerationConfig.class, FILE_NAME);

    public static WorldGenerationConfig get() {
        return INSTANCE;
    }

    public ConfigUtils.CommentValue<Map<ResourceLocation, Boolean>> biomes = ConfigUtils.CommentValue.of("Set Entry to False to disable generation of that biome", getDefaultBiomes());
    public ConfigUtils.CommentValue<Integer> regionWeight = ConfigUtils.CommentValue.of("Only used when using with Terrablender", DEFAULT_REGION_WEIGHT);
    public boolean blue_fortress = true;

    public boolean isEnabled(ResourceKey<Biome> key) {
        return biomes.value().getOrDefault(key.location(), true);
    }

    private static @NotNull Map<ResourceLocation, Boolean> getDefaultBiomes() {
        return NetherDescentBiomes.BIOME_FACTORIES.keySet().stream()
                .map(ResourceKey::location)
                .sorted(Comparator.comparing(ResourceLocation::toString))
                .collect(Collectors.toMap(loc -> loc, loc -> true, (a, b) -> a, LinkedHashMap::new));
    }
}