package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HangingNDBushBlock extends NetherDescentBush {
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private final VoxelShape SHAPE;
    private final VoxelShape HANGING_SHAPE;

    public HangingNDBushBlock(Properties properties, VoxelShape shape, VoxelShape hangingShape) {
        super(properties, shape);
        this.SHAPE = shape;
        this.HANGING_SHAPE = hangingShape;
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if (direction.getAxis().isVertical())
            return this.defaultBlockState().setValue(HANGING, direction == Direction.DOWN);
        return null;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockPos blockPos = state.getValue(HANGING) ? pos.above() : pos.below();
        return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HANGING));
    }

    @Override
    @NotNull
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        VoxelShape shape = state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
        return shape.move(vec3.x, vec3.y, vec3.z);
    }
}
