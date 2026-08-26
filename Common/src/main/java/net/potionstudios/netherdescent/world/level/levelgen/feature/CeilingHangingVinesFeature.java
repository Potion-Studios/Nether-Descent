package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;

/**
 * Feature that Places a Base Block then Places a Vine that grows down from the ceiling
 * @see CeilingHangingVinesFeatureConfiguration
 * @author Joseph T. McQuigg
 */
public class CeilingHangingVinesFeature extends Feature<CeilingHangingVinesFeatureConfiguration> {
    public CeilingHangingVinesFeature(Codec<CeilingHangingVinesFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CeilingHangingVinesFeatureConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos origin = context.origin();
        RandomSource randomSource = context.random();

        if (worldGenLevel.isEmptyBlock(origin) && worldGenLevel.isEmptyBlock(origin.below())) {
            CeilingHangingVinesFeatureConfiguration config = context.config();
            if (worldGenLevel.getBlockState(origin.above()).is(context.config().ceiling())) {
                setBlock(worldGenLevel, origin, config.base());
                BlockState vine = config.vine().setValue(HangingMossBlock.TIP, false);
                int maxHeight = config.growth().sample(randomSource);
                BlockPos.MutableBlockPos mutableBlockPos = origin.below().mutable();
                for (int i = 0; i <= maxHeight; i++) {
                    if ((i == maxHeight) || !worldGenLevel.isEmptyBlock(mutableBlockPos.below())) {
                        setBlock(worldGenLevel, mutableBlockPos, vine.setValue(HangingMossBlock.TIP, true));
                        return true;
                    } else {
                        setBlock(worldGenLevel, mutableBlockPos, vine);
                        mutableBlockPos.move(Direction.DOWN);
                    }
                }
            }
        }

        return false;
    }
}