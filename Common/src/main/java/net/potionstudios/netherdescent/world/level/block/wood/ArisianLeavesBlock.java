package net.potionstudios.netherdescent.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;

public class ArisianLeavesBlock extends LeavesBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final IntegerProperty STRENGTH = IntegerProperty.create("strength", 0, 5);
	public ArisianLeavesBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(STRENGTH, 0));
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (random.nextInt(10) == 0) {
			BlockPos blockPos = pos.below();
			BlockState blockState = level.getBlockState(blockPos);
			if (!isFaceFull(blockState.getCollisionShape(level, blockPos), Direction.UP))
				ParticleUtils.spawnParticleBelow(level, pos, random, NetherDescentParticles.ARISIAN_LEAF.get());
		}
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (!level.isClientSide() && entity instanceof LivingEntity)
			if (state.getValue(STRENGTH) != 5)
				updateState(state, level, pos, 5);
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);
		int currentStrength = state.getValue(STRENGTH);
		int targetStrength = 0;

		if (!level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(0.2).move(pos)).isEmpty())
			targetStrength = 5;
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

		if (targetStrength != currentStrength)
			updateState(state, level, pos, targetStrength);
	}

	@Override
	protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
		if (!level.isClientSide())
			level.scheduleTick(pos, this, 1);
	}

	private void updateState(@NotNull BlockState currentState, @NotNull Level level, @NotNull BlockPos pos, int newStrength) {
		boolean newLit = newStrength > 0;

		BlockState newState = currentState.setValue(STRENGTH, newStrength).setValue(LIT, newLit);

		if (!newState.equals(currentState)) {
			level.setBlockAndUpdate(pos, newState);
			level.updateNeighborsAt(pos, this);
			level.scheduleTick(pos, this, 1);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(LIT, STRENGTH));
	}
}
