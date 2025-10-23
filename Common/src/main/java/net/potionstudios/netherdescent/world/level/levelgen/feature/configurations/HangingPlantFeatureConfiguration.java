package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record HangingPlantFeatureConfiguration(Block base, Block plantHeadBlock, Block plantBodyBlock) implements FeatureConfiguration {
	public static final Codec<HangingPlantFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base").forGetter(HangingPlantFeatureConfiguration::base),
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("head").forGetter(HangingPlantFeatureConfiguration::plantHeadBlock),
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body").forGetter(HangingPlantFeatureConfiguration::plantBodyBlock)
			).apply(instance, HangingPlantFeatureConfiguration::new)
	);
}
