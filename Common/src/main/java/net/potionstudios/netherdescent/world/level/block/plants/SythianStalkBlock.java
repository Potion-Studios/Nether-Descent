package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

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
    protected boolean propagatesSkylightDown(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        VoxelShape voxelShape = state.getValue(LEAVES) == BambooLeaves.LARGE ? LARGE_SHAPE : SMALL_SHAPE;
        Vec3 vec3 = state.getOffset(pos);
        return voxelShape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected @NonNull VoxelShape getCollisionShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(pos);
        return COLLISION_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean isCollisionShapeFullBlock(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        return false;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
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
                else if (blockState.is(NetherDescentBlocks.SYTHIAN_STALK.get()))
                    return defaultBlockState().setValue(AGE, blockState.getValue(AGE) > 0 ? 1 : 0).setValue(HANGING, context.getClickedFace() == Direction.DOWN);
                else {
                    BlockState blockState2 = level.getBlockState(pos.above());
                    return blockState2.is(NetherDescentBlocks.SYTHIAN_STALK.get())
                            ? defaultBlockState().setValue(AGE, blockState2.getValue(AGE)).setValue(HANGING, context.getClickedFace() == Direction.DOWN)
                            : NetherDescentBlocks.SYTHIAN_SHOOT.get().defaultBlockState().setValue(SythianShootBlock.HANGING, context.getClickedFace() == Direction.DOWN);
                }
            }
        }

        return null;
    }

    @Override
    protected void tick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!state.canSurvive(level, pos))
            level.destroyBlock(pos, true);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(STAGE) == 0;
    }

    @Override
    protected void randomTick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (state.getValue(STAGE) == 0)
            if (random.nextInt(6) == 0 && level.isEmptyBlock(state.getValue(HANGING) ? pos.below() : pos.above())) {
                int i = state.getValue(HANGING)? getHeightAboveUpToMax(level, pos) + 1 : getHeightBelowUpToMax(level, pos) + 1;
                if (i < 16)
                    growSythianStalk(state, level, pos, random, i);
            }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, @NonNull BlockPos pos) {
        return level.getBlockState(state.getValue(HANGING) ? pos.above() : pos.below()).is(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON);
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos pos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        if (!state.canSurvive(level, pos))
            ticks.scheduleTick(pos, this, 1);

        if (((directionToNeighbour == Direction.UP && !state.getValue(HANGING)) || (directionToNeighbour == Direction.DOWN && state.getValue(HANGING))) && neighbourState.is(NetherDescentBlocks.SYTHIAN_STALK.get()) && neighbourState.getValue(AGE) > state.getValue(AGE))
            return state.cycle(AGE);

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

	@Override
	public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull RandomSource random) {
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
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, BlockState state) {
        int heightAbove = this.getHeightAboveUpToMax(level, pos);
        int heightBelow = this.getHeightBelowUpToMax(level, pos);
        int total = heightAbove + heightBelow + 1;
        boolean hanging = state.getValue(HANGING);

        BlockPos startPos = hanging ? pos.below(heightBelow) : pos.above(heightAbove);
        for (int i = 0; i < total; i++) {
            BlockPos currentPos = hanging ? startPos.above(i) : startPos.below(i);
            BlockState s = level.getBlockState(currentPos);
            if (!s.is(NetherDescentBlocks.SYTHIAN_STALK.get()))
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
        while (i < 16 && level.getBlockState(pos.above(i + 1)).is(NetherDescentBlocks.SYTHIAN_STALK.get()))
            i++;
        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter level, BlockPos pos) {
        int i = 0;
        while (i < 16 && level.getBlockState(pos.below(i + 1)).is(NetherDescentBlocks.SYTHIAN_STALK.get()))
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
            if (!blockState.is(NetherDescentBlocks.SYTHIAN_STALK.get()) || blockState.getValue(LEAVES) == BambooLeaves.NONE)
                bambooLeaves = BambooLeaves.SMALL;
            else if (blockState.is(NetherDescentBlocks.SYTHIAN_STALK.get()) && blockState.getValue(LEAVES) != BambooLeaves.NONE) {
                bambooLeaves = BambooLeaves.LARGE;
                if (blockState2.is(NetherDescentBlocks.SYTHIAN_STALK.get())) {
                    level.setBlock(hanging ? pos.above() : pos.below(), blockState.setValue(LEAVES, BambooLeaves.SMALL), 3);
                    level.setBlock(blockPos, blockState2.setValue(LEAVES, BambooLeaves.NONE), 3);
                }
            }

        int i = state.getValue(AGE) != 1 && !blockState2.is(NetherDescentBlocks.SYTHIAN_STALK.get()) ? 0 : 1;
        int j = (age < 11 || !(random.nextFloat() < 0.25F)) && age != 15 ? 0 : 1;
        level.setBlock(hanging ? pos.below() : pos.above(), defaultBlockState().setValue(AGE, i).setValue(LEAVES, bambooLeaves).setValue(STAGE, j).setValue(HANGING, hanging), 3);
    }
}
