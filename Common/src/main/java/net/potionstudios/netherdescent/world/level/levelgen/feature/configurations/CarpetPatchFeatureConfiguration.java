package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CarpetPatchFeatureConfiguration(Block base, Block carpet, IntProvider radius, float carpetChance) implements FeatureConfiguration {
	public static final Codec<CarpetPatchFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base").forGetter(CarpetPatchFeatureConfiguration::base),
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("carpet").forGetter(CarpetPatchFeatureConfiguration::carpet),
			IntProvider.codec(1, 32).fieldOf("radius").forGetter(CarpetPatchFeatureConfiguration::radius),
			Codec.floatRange(0.0F, 1.0F).fieldOf("carpet_chance").forGetter(CarpetPatchFeatureConfiguration::carpetChance)
		).apply(instance, CarpetPatchFeatureConfiguration::new)
	);
}
