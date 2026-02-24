package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HangingDoublePlantBlock extends NetherDescentDoublePlantBlock {
	public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
	public HangingDoublePlantBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false).setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		if (context.getClickedFace() == Direction.DOWN) {
			BlockPos blockPos = context.getClickedPos();
			Level level = context.getLevel();
			return blockPos.getY() > level.getMinBuildHeight() + 1 && level.getBlockState(blockPos.below()).canBeReplaced(context)
					? defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(HANGING, true)
					: null;
		} else {
			return super.getStateForPlacement(context);
		}
	}

	@Override
	protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		if (state.getValue(HANGING)) {
			if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
				return mayPlaceOn(level.getBlockState(pos.above()), level, pos);
			} else {
				BlockState blockstate = level.getBlockState(pos.above());
				return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
			}
		}
		NetherDescent.LOGGER.info("HangingDoublePlantBlock.canSurvive: {} at {}", state, pos);
		return super.canSurvive(state, level, pos);
	}

	@Override
	protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		if (state.getValue(HANGING)) {
			DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
			if (direction.getAxis() != Direction.Axis.Y
					|| doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.DOWN)
					|| neighborState.is(this) && neighborState.getValue(HALF) != doubleBlockHalf) {
				return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.UP && !state.canSurvive(level, pos)
						? Blocks.AIR.defaultBlockState()
						: super.updateShape(state, direction, neighborState, level, pos, neighborPos);
			} else {
				return Blocks.AIR.defaultBlockState();
			}
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull LivingEntity placer, @NotNull ItemStack stack) {
		if (state.getValue(HANGING))
			level.setBlockAndUpdate(pos.below(), DoublePlantBlock.copyWaterloggedFrom(level, pos.below(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(HANGING, true)));
		else super.setPlacedBy(level, pos, state, placer, stack);
	}

//	@Override
//	public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
//		if (!level.isClientSide() && state.getValue(HANGING)) {
//			if (player.isCreative())
//				preventDropFromBottomPart(level, pos, state, player);
//			else dropResources(state, level, pos, null, player, player.getMainHandItem());
//			this.spawnDestroyParticles(level, player, pos, state);
//			if (state.is(BlockTags.GUARDED_BY_PIGLINS))
//				PiglinAi.angerNearbyPiglins(player, false);
//
//			level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));
//			return state;
//		}
//		return super.playerWillDestroy(level, pos, state, player);
//	}

	protected static void preventDropFromBottomPart(@NotNull Level level, @NotNull BlockPos pos, BlockState state, @NotNull Player player) {
		DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
		if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
			BlockPos blockPos = pos.above();
			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.is(state.getBlock()) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
				BlockState blockState2 = blockState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				level.setBlock(blockPos, blockState2, 35);
				level.levelEvent(player, 2001, blockPos, Block.getId(blockState));
			}
		}
	}

	public static void placeHangingAt(LevelAccessor level, BlockState state, BlockPos pos, int flags) {
		BlockPos blockPos = pos.below();
		level.setBlock(pos, copyWaterloggedFrom(level, pos, state.setValue(HALF, DoubleBlockHalf.LOWER).setValue(HANGING, true)), flags);
		level.setBlock(blockPos, copyWaterloggedFrom(level, blockPos, state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(HANGING, true)), flags);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(HANGING));
	}
}
