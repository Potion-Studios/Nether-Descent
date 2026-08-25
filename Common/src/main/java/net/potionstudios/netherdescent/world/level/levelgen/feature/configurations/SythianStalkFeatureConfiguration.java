package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SythianStalkFeatureConfiguration(boolean hanging) implements FeatureConfiguration {
    public static final Codec<SythianStalkFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("hanging").forGetter(SythianStalkFeatureConfiguration::hanging)
            ).apply(instance, SythianStalkFeatureConfiguration::new)
    );
}
