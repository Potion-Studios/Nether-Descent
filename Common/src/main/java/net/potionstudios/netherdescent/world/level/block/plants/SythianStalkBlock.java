package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.tags.NetherDescentBlockTags;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SythianStalkBlock extends Block implements BonemealableBlock {
    protected static final VoxelShape SMALL_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    protected static final VoxelShape LARGE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape COLLISION_SHAPE = Block.box(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public SythianStalkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(LEAVES, BambooLeaves.NONE).setValue(STAGE, 0).setValue(HANGING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(AGE, LEAVES, STAGE, HANGING));
    }

    @Override
    protected boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape voxelShape = state.getValue(LEAVES) == BambooLeaves.LARGE ? LARGE_SHAPE : SMALL_SHAPE;
        Vec3 vec3 = state.getOffset(level, pos);
        return voxelShape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return COLLISION_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean isCollisionShapeFullBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return false;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.getFluidState(pos).isEmpty())
            return null;
        else {
            BlockState blockState;
            if (context.getClickedFace() == Direction.DOWN)
                blockState = level.getBlockState(pos.above());
            else blockState = level.getBlockState(pos.below());

            if (blockState.is(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON)) {
                if (blockState.is(NetherDescentBlocks.SYTHIAN_SHOOT.get()))
                    return defaultBlockState().setValue(AGE, 0).setValue(HANGING, context.getClickedFace() == Direction.DOWN);
                else if (blockState.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()))
                    return defaultBlockState().setValue(AGE, blockState.getValue(AGE) > 0 ? 1 : 0).setValue(HANGING, context.getClickedFace() == Direction.DOWN);
                else {
                    BlockState blockState2 = level.getBlockState(pos.above());
                    return blockState2.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock())
                            ? defaultBlockState().setValue(AGE, blockState2.getValue(AGE)).setValue(HANGING, context.getClickedFace() == Direction.DOWN)
                            : NetherDescentBlocks.SYTHIAN_SHOOT.get().defaultBlockState().setValue(SythianShootBlock.HANGING, context.getClickedFace() == Direction.DOWN);
                }
            }
        }

        return null;
    }

    @Override
    protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.canSurvive(level, pos))
            level.destroyBlock(pos, true);
    }

    @Override
    protected boolean isRandomlyTicking(@NotNull BlockState state) {
        return state.getValue(STAGE) == 0;
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(STAGE) == 0)
            if (random.nextInt(6) == 0 && level.isEmptyBlock(state.getValue(HANGING) ? pos.below() : pos.above())) {
                int i = state.getValue(HANGING)? getHeightAboveUpToMax(level, pos) + 1 : getHeightBelowUpToMax(level, pos) + 1;
                if (i < 16)
                    growSythianStalk(state, level, pos, random, i);
            }
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return level.getBlockState(state.getValue(HANGING) ? pos.above() : pos.below()).is(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (!state.canSurvive(level, pos))
            level.scheduleTick(pos, this, 1);

        if (((direction == Direction.UP && !state.getValue(HANGING)) || (direction == Direction.DOWN && state.getValue(HANGING))) && neighborState.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()) && neighborState.getValue(AGE) > state.getValue(AGE))
            level.setBlock(pos, state.cycle(AGE), 2);

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
		BambooLeaves leaves = state.getValue(LEAVES);
		if ((leaves == BambooLeaves.LARGE && random.nextInt(10) >= 3) || (leaves == BambooLeaves.SMALL && random.nextInt(2) >= 0)) {
			BlockPos below = pos.below();
			BlockState blockState = level.getBlockState(below);
			if (!isFaceFull(blockState.getCollisionShape(level, pos), Direction.UP))
				ParticleUtils.spawnParticleBelow(level, pos, random, NetherDescentParticles.SYTHIAN_LEAF.get());
		}
	}

	@Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        int heightAbove = this.getHeightAboveUpToMax(level, pos);
        int heightBelow = this.getHeightBelowUpToMax(level, pos);
        int total = heightAbove + heightBelow + 1;
        boolean hanging = state.getValue(HANGING);

        BlockPos startPos = hanging ? pos.below(heightBelow) : pos.above(heightAbove);
        for (int i = 0; i < total; i++) {
            BlockPos currentPos = hanging ? startPos.above(i) : startPos.below(i);
            BlockState s = level.getBlockState(currentPos);
            if (!s.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()))
                break;

            BambooLeaves leaves = s.getValue(LEAVES);
            if (leaves == BambooLeaves.NONE) {
                level.setBlock(currentPos, s.setValue(LEAVES, BambooLeaves.SMALL), 3);
                break;
            } else if (leaves == BambooLeaves.SMALL) {
                level.setBlock(currentPos, s.setValue(LEAVES, BambooLeaves.LARGE), 3);
                break;
            }
        }
    }

    protected int getHeightAboveUpToMax(BlockGetter level, BlockPos pos) {
        int i = 0;
        while (i < 16 && level.getBlockState(pos.above(i + 1)).is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()))
            i++;
        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter level, BlockPos pos) {
        int i = 0;
        while (i < 16 && level.getBlockState(pos.below(i + 1)).is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()))
            i++;
        return i;
    }

    protected void growSythianStalk(BlockState state, Level level, BlockPos pos, RandomSource random, int age) {
        boolean hanging = state.getValue(HANGING);
        BlockState blockState = level.getBlockState(hanging ? pos.above() : pos.below());
        BlockPos blockPos = hanging ? pos.above(2) : pos.below(2);
        BlockState blockState2 = level.getBlockState(blockPos);
        BambooLeaves bambooLeaves = BambooLeaves.NONE;
        if (age >= 1)
            if (!blockState.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()) || blockState.getValue(LEAVES) == BambooLeaves.NONE)
                bambooLeaves = BambooLeaves.SMALL;
            else if (blockState.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()) && blockState.getValue(LEAVES) != BambooLeaves.NONE) {
                bambooLeaves = BambooLeaves.LARGE;
                if (blockState2.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock())) {
                    level.setBlock(hanging ? pos.above() : pos.below(), blockState.setValue(LEAVES, BambooLeaves.SMALL), 3);
                    level.setBlock(blockPos, blockState2.setValue(LEAVES, BambooLeaves.NONE), 3);
                }
            }

        int i = state.getValue(AGE) != 1 && !blockState2.is(NetherDescentBlocks.SYTHIAN_STALK.getBlock()) ? 0 : 1;
        int j = (age < 11 || !(random.nextFloat() < 0.25F)) && age != 15 ? 0 : 1;
        level.setBlock(hanging ? pos.below() : pos.above(), defaultBlockState().setValue(AGE, i).setValue(LEAVES, bambooLeaves).setValue(STAGE, j).setValue(HANGING, hanging), 3);
    }
}
