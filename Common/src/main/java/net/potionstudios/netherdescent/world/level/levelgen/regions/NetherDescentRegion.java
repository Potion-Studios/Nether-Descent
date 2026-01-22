package net.potionstudios.netherdescent.world.level.levelgen.regions;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class NetherDescentRegion extends Region {
    public NetherDescentRegion(ResourceLocation name, int weight) {
        super(name, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addIfEnabled(mapper,
                Climate.Parameter.point(0.0F), Climate.Parameter.point(-0.5F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                0.0F, NetherDescentBiomes.EMBUR_BOG);

        addIfEnabled(mapper,
                Climate.Parameter.point(0.4F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                0.0F, NetherDescentBiomes.WAILING_GARTH);

        addIfEnabled(mapper,
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.5F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                0.375F, NetherDescentBiomes.SYTHIAN_TORRIDS);

        addIfEnabled(mapper,
                Climate.Parameter.point(-0.5F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                0.175F, NetherDescentBiomes.CRIMSON_GARDENS);

        addIfEnabled(mapper,
                Climate.Parameter.point(-0.4F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F), Climate.Parameter.point(0.0F),
                0.175F, NetherDescentBiomes.ARISIAN_UNDERGROWTH);
    }

    private void addIfEnabled(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper,
                              Climate.Parameter temperature, Climate.Parameter humidity,
                              Climate.Parameter continentalness, Climate.Parameter erosion,
                              Climate.Parameter depth, Climate.Parameter weirdness,
                              float offset, ResourceKey<Biome> biome) {
        if (WorldGenerationConfig.get().isEnabled(biome))
            this.addBiome(mapper, temperature, humidity, continentalness, erosion, depth, weirdness, offset, biome);
    }
}