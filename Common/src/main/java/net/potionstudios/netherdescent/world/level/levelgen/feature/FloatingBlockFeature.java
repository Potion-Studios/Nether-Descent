package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NonNull;

public record FloatingBlockFeature(Holder<BlockStateProvider> block, IntProvider distance) implements Feature {
    public static final MapCodec<FloatingBlockFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(FloatingBlockFeature::block),
            IntProviders.CODEC.fieldOf("distance").forGetter(FloatingBlockFeature::distance)
    ).apply(instance, FloatingBlockFeature::new));

    @Override
    public @NonNull MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(@NonNull WorldGenLevel level, @NonNull ChunkGenerator chunkGenerator, @NonNull RandomSource random, @NonNull BlockPos origin) {
        IntProvider intProvider = distance();

        int start = intProvider.sample(random);
        origin = origin.below(start);

        for (int i = start; i >= intProvider.minInclusive(); i--)
            if (level.getBlockState(origin).canBeReplaced() && level.getBlockState(origin.above()).isAir() && level.getBlockState(origin.below()).isAir()) {
                setBlock(level, origin, block().value().getState(level, random, origin));
                return true;
            } else origin = origin.above();

        return false;
    }
}
