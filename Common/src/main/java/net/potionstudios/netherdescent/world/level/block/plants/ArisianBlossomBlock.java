package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class ArisianBlossomBlock extends NetherDescentBush {
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	public static final BooleanProperty PULSE = BooleanProperty.create("pulse");
	protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0);

	public ArisianBlossomBlock(Properties properties, TagKey<Block> placeableOn) {
		super(properties, placeableOn);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(PULSE, false));
	}

	@Override
	protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		super.entityInside(state, level, pos, entity);
		if (!state.getValue(LIT) && entity instanceof LivingEntity) {
			level.setBlockAndUpdate(pos, state.setValue(LIT, true).setValue(PULSE, true));
			level.scheduleTick(pos, this, 1);
		}
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);
		BlockState newState = state;
		boolean changed = false;

		if (state.getValue(LIT))
			if (level.getEntitiesOfClass(LivingEntity.class, SHAPE.bounds().move(pos)).isEmpty()) {
				newState = newState.setValue(LIT, false).setValue(PULSE, true);
				changed = true;
			} else level.scheduleTick(pos, this, 20);

		if (state.getValue(PULSE)) {
			newState = newState.setValue(PULSE, false);
			changed = true;
		}

		if (changed) {
			level.setBlockAndUpdate(pos, newState);
			if (newState.getValue(PULSE))
				level.scheduleTick(pos, this, 1);
		}
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		//if (state.getValue(LIT))
			//level.addParticle(ParticleTypes.);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(LIT).add(PULSE));
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		Vec3 vec3 = state.getOffset(level, pos);
		return SHAPE.move(vec3.x, vec3.y, vec3.z);
	}

//	@Override
//	protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
//		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
//		BlockState neighborState = level.getBlockState(neighborPos);
//		if (neighborState.is(NetherDescentBlocks.ARISIAN_BLOSSOM.get())) {
//			if (neighborState.getValue(LIT) && !state.getValue(LIT)) {
//				level.setBlockAndUpdate(pos, state.setValue(LIT, true).setValue(PULSE, true));
//				level.scheduleTick(pos, this, 1);
//			} else if (!neighborState.getValue(LIT) && state.getValue(LIT)) {
//				level.setBlockAndUpdate(pos, state.setValue(LIT, false).setValue(PULSE, true));
//				level.scheduleTick(pos, this, 1);
//			}
//		}
//	}

	@Override
	protected boolean isSignalSource(@NotNull BlockState state) {
		return true;
	}

	@Override
	protected int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		return state.getValue(PULSE) ? 6 : 0;
	}
}
