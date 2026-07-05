package net.potionstudios.netherdescent.compat.lithostitched;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.lithostitched.api.predicate.LoadPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;

public record ConfigLoadPredicate(ResourceKey<Biome> biome) implements LoadPredicate {
	public static final MapCodec<ConfigLoadPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceKey.codec(Registries.BIOME).fieldOf("biome").forGetter(ConfigLoadPredicate::biome)
	).apply(instance, ConfigLoadPredicate::new));

	@Override
	public boolean test() {
		return WorldGenerationConfig.get().isEnabled(biome);
	}

	@Override
	public MapCodec<? extends LoadPredicate> codec() {
		return CODEC;
	}
}
