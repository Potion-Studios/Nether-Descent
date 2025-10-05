package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CeilingHangingVinesFeatureConfiguration(Block ceiling, BlockState base, BlockState vine, float growth, int patchRadius, int tries) implements FeatureConfiguration {
	public static final Codec<CeilingHangingVinesFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ceiling").forGetter(CeilingHangingVinesFeatureConfiguration::ceiling),
					BlockState.CODEC.fieldOf("base").forGetter(CeilingHangingVinesFeatureConfiguration::base),
					BlockState.CODEC.fieldOf("vine").forGetter(CeilingHangingVinesFeatureConfiguration::vine),
					Codec.floatRange(0.0F, 1.0F).fieldOf("growth").forGetter(CeilingHangingVinesFeatureConfiguration::growth),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(CeilingHangingVinesFeatureConfiguration::patchRadius),
                    ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(CeilingHangingVinesFeatureConfiguration::tries)
			).apply(instance, CeilingHangingVinesFeatureConfiguration::new)
	);
}
