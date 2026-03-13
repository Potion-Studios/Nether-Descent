package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;

public class ArisianBlossomBlock extends HangingNDBushBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty PULSE = BooleanProperty.create("pulse");
	public static final IntegerProperty STRENGTH = IntegerProperty.create("strength", 0, 7);
	protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0);
	protected static final VoxelShape CEILING_SHAPE = Block.box(4.0, 2.0, 4.0, 12.0, 16.0, 12.0);

	public ArisianBlossomBlock(Properties properties) {
		super(properties, SHAPE, CEILING_SHAPE);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(STRENGTH, 0).setValue(PULSE, false).setValue(HANGING, false));
	}

	@Override
	protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		super.entityInside(state, level, pos, entity);
		if (!level.isClientSide() && entity instanceof LivingEntity)
			if (state.getValue(STRENGTH) != 7)
				updateState(state, level, pos, 7);
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);

		int currentStrength = state.getValue(STRENGTH);
		int targetStrength = 0;

		VoxelShape detectionShape = state.getValue(HANGING) ? CEILING_SHAPE : SHAPE;
		if (!level.getEntitiesOfClass(LivingEntity.class, detectionShape.bounds().inflate(0.2).move(pos)).isEmpty())
			targetStrength = 7;
		else {
			int maxNeighborStrength = 0;
			for (Direction direction : Direction.values()) {
				BlockPos neighborPos = pos.relative(direction);
				BlockState neighborState = level.getBlockState(neighborPos);

				if (neighborState.is(this)) {
					int neighborStrength = neighborState.getValue(STRENGTH);
					if (neighborStrength > maxNeighborStrength)
						maxNeighborStrength = neighborStrength;
				}
			}

			if (maxNeighborStrength > 0)
				targetStrength = maxNeighborStrength - 1;
		}

		if (targetStrength != currentStrength || state.getValue(PULSE))
			updateState(state, level, pos, targetStrength);

		if (targetStrength > 0 || state.getValue(PULSE)) {
			int tickDelay = state.getValue(PULSE) ? 2 : 4;
			level.scheduleTick(pos, this, tickDelay);
		}
	}

	@Override
	protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
		if (!level.isClientSide())
			level.scheduleTick(pos, this, 1);
	}

	private void updateState(@NotNull BlockState currentState, @NotNull Level level, @NotNull BlockPos pos, int newStrength) {
		boolean currentLit = currentState.getValue(LIT);
		boolean currentPulse = currentState.getValue(PULSE);

		boolean newLit = newStrength > 0;
		boolean newPulse = currentPulse;

		if (currentLit != newLit)
			newPulse = true;
		else if (currentPulse)
			newPulse = false;

		BlockState newState = currentState.setValue(STRENGTH, newStrength).setValue(LIT, newLit).setValue(PULSE, newPulse);

		if (!newState.equals(currentState)) {
			level.setBlockAndUpdate(pos, newState);
			level.updateNeighborsAt(pos, this);

			if (newPulse) {
				level.scheduleTick(pos, this, 2);
				if (!level.isClientSide())
					((ServerLevel) level).sendParticles(NetherDescentParticles.GILL_LEVITATE.get(), pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 1, 0.12, 0.02, 0.12, 0.02);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(LIT, STRENGTH, PULSE));
	}

	@Override
	protected boolean isSignalSource(@NotNull BlockState state) {
		return state.getValue(PULSE);
	}

	@Override
	protected int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		return state.getValue(PULSE) ? 6 : 0;
	}
}