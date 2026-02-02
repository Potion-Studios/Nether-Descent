package net.potionstudios.netherdescent.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;

public class ArisianLeavesBlock extends LeavesBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public ArisianLeavesBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
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
		if (!level.isClientSide()) {
			if (!state.getValue(LIT))
				level.setBlockAndUpdate(pos, state.setValue(LIT, true));
			level.scheduleTick(pos, this, 20);
		}
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(LIT))
			if (level.getEntitiesOfClass(Entity.class, new AABB(pos).expandTowards(0, 0.2, 0)).isEmpty())
				state = state.setValue(LIT, false);
			else level.scheduleTick(pos, this, 20);
		super.tick(state, level, pos, random);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(LIT));
	}
}
