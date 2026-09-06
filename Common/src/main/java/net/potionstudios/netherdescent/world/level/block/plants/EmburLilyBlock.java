package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

public class EmburLilyBlock extends WaterlilyBlock implements BonemealableBlock {
    public EmburLilyBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(@NonNull BlockState state, BlockGetter level, @NonNull BlockPos pos) {
        return level.getFluidState(pos).is(FluidTags.LAVA) && level.getFluidState(pos.above()).is(Fluids.EMPTY);
    }

    @Override
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
            BlockPos offsetPos = pos.relative(direction).below();
            if (mayPlaceOn(level.getBlockState(offsetPos), level, offsetPos)) {
                level.setBlockAndUpdate(offsetPos.above(), this.defaultBlockState());
                return;
            }
        }
    }
}
