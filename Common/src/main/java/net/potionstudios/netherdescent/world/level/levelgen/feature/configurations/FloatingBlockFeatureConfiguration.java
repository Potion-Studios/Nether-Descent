package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FloatingBlockFeatureConfiguration(BlockStateProvider block, IntProvider distance) implements FeatureConfiguration {
    public static final Codec<FloatingBlockFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(FloatingBlockFeatureConfiguration::block),
                    IntProvider.CODEC.fieldOf("distance").forGetter(FloatingBlockFeatureConfiguration::distance)
            ).apply(instance, FloatingBlockFeatureConfiguration::new)
    );
}
