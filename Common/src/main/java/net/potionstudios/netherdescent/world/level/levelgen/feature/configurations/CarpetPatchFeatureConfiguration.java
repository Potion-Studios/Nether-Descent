package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CarpetPatchFeatureConfiguration(Block carpet, boolean hanging) implements FeatureConfiguration {
	public static final Codec<CarpetPatchFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("carpet").forGetter(CarpetPatchFeatureConfiguration::carpet),
					Codec.BOOL.fieldOf("hanging").orElse(false).forGetter(CarpetPatchFeatureConfiguration::hanging)
		).apply(instance, CarpetPatchFeatureConfiguration::new)
	);
}
