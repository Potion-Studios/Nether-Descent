package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.FloatingBlockFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class FloatingBlockFeature extends Feature<FloatingBlockFeatureConfiguration> {
    public FloatingBlockFeature(Codec<FloatingBlockFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<FloatingBlockFeatureConfiguration> context) {
        FloatingBlockFeatureConfiguration config = context.config();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        IntProvider intProvider = config.distance();

        int start = intProvider.sample(context.random());
        origin = origin.below(start);

        for (int i = start; i >= intProvider.getMinValue(); i--)
            if (level.getBlockState(origin).canBeReplaced() && level.getBlockState(origin.above()).isAir() && level.getBlockState(origin.below()).isAir()) {
                setBlock(level, origin, config.block().getState(context.random(), origin));
                return true;
            } else origin = origin.above();


        return false;
    }
}
