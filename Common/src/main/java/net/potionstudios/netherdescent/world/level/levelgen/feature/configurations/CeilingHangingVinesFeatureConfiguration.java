package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CeilingHangingVinesFeatureConfiguration(Block ceiling, BlockState base, BlockState vine, IntProvider growth) implements FeatureConfiguration {
	public static final Codec<CeilingHangingVinesFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ceiling").forGetter(CeilingHangingVinesFeatureConfiguration::ceiling),
					BlockState.CODEC.fieldOf("base").forGetter(CeilingHangingVinesFeatureConfiguration::base),
					BlockState.CODEC.fieldOf("vine").forGetter(CeilingHangingVinesFeatureConfiguration::vine),
					IntProvider.codec(0, 100).fieldOf("growth").forGetter(CeilingHangingVinesFeatureConfiguration::growth)
			).apply(instance, CeilingHangingVinesFeatureConfiguration::new)
	);
}
