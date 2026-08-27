package net.potionstudios.netherdescent.world.level.levelgen.regions;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class NetherDescentRegion extends Region {
    public NetherDescentRegion(Identifier name, int weight) {
        super(name, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        NetherDescentBiomes.BIOME_FACTORIES.forEach(((biomeResourceKey, biomeDefinition) -> {
            if (WorldGenerationConfig.get().isEnabled(biomeResourceKey))
                this.addBiome(mapper, biomeDefinition.parameterPoint(), biomeResourceKey);
        }));
    }
}