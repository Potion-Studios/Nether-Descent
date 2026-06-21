package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class SythianScaffoldingBlock extends ScaffoldingBlock {
    public SythianScaffoldingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        int i = getDistance(level, blockPos);
        return this.defaultBlockState()
                .setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER)
                .setValue(DISTANCE, i)
                .setValue(BOTTOM, this.isBottom(level, blockPos, i));
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        int i = getDistance(level, pos);
        BlockState blockState = state.setValue(DISTANCE, i).setValue(BOTTOM, this.isBottom(level, pos, i));
        if (blockState.getValue(DISTANCE) == 7) {
            if (state.getValue(DISTANCE) == 7) {
                FallingBlockEntity.fall(level, pos, blockState);
            } else {
                level.destroyBlock(pos, true);
            }
        } else if (state != blockState) {
            level.setBlock(pos, blockState, 3);
        }
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return getDistance(level, pos) < 7;
    }

    private boolean isBottom(BlockGetter level, BlockPos pos, int distance) {
        return distance > 0 && !level.getBlockState(pos.below()).is(this);
    }

    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.DOWN);
        BlockState blockState = level.getBlockState(mutableBlockPos);
        int i = 7;
        if (blockState.is(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get())) {
            i = blockState.getValue(DISTANCE);
        } else if (blockState.isFaceSturdy(level, mutableBlockPos, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
            if (blockState2.is(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get())) {
                i = Math.min(i, blockState2.getValue(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }

    /**
     * Override from IForgeBlock and IBlock
     * @see net.minecraftforge.common.extensions.IForgeBlock
     * @see net.neoforged.neoforge.common.extensions.IBlockExtension
     */
    public boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }
}
