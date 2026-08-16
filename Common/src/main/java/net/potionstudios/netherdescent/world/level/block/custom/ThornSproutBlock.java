package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThornSproutBlock extends HorizontalDirectionalBlock {
	private static final VoxelShape SHAPE = Block.box(0, 8, 0, 16, 14, 16);
	public static final EnumProperty<SegmentType> SEGMENT = EnumProperty.create("segment", SegmentType.class);
	public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 17);
	public static final BooleanProperty COUNTING = BooleanProperty.create("counting");

	public static final int RETRACT_DELAY_TICKS = 15 * 20;
	public static final int POLL_INTERVAL_TICKS = 5;
	public static final int RETRACT_STEP_INTERVAL_TICKS = 4;
	public static final int GROWTH_LENGTH = 3;
	public static final int MAX_CHAIN_SIZE = 18;
	private static final float PUSHBACK_STRENGTH = 0.6f;

	public ThornSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(SEGMENT, SegmentType.END)
				.setValue(FLOWERING, false)
				.setValue(SIZE, 0)
				.setValue(COUNTING, false));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		if (context.getClickedFace().getAxis().isHorizontal()) {
			BlockPos attachPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
			BlockState attachState = context.getLevel().getBlockState(attachPos);
			boolean extendingChain = attachState.is(this) && attachState.getValue(SEGMENT).equals(SegmentType.END) && attachState.getValue(SIZE) != 17;
			if (attachState.isFaceSturdy(context.getLevel(), attachPos, context.getClickedFace()) || extendingChain) {
				int size = extendingChain ? attachState.getValue(SIZE) + 1 : 0;
				return defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(SIZE, size);
			}
		}
		return null;
	}

	@Override
	public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);

		int size = state.getValue(SIZE);
		if (size > 0) {
			Direction backwards = state.getValue(FACING).getOpposite();
			BlockPos previousPos = pos.relative(backwards);
			BlockState previousState = level.getBlockState(previousPos);
			if (previousState.is(this) && previousState.getValue(SEGMENT) == SegmentType.END) {
				SegmentType convertedType = (size - 1 == 0) ? SegmentType.BASE : SegmentType.MIDDLE;
				boolean flowering = convertedType == SegmentType.MIDDLE && level.random.nextBoolean();
				level.setBlock(previousPos, previousState.setValue(SEGMENT, convertedType).setValue(FLOWERING, flowering).setValue(COUNTING, false), 3);
			}
		}

		if (!level.isClientSide() && state.getValue(SEGMENT) == SegmentType.END) {
			level.scheduleTick(pos, this, POLL_INTERVAL_TICKS);
		}
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return;
		if (!(entity instanceof Player)) return;
		if (state.getValue(SEGMENT) != SegmentType.END) return;
		grow(serverLevel, pos, state);
	}

	private void grow(ServerLevel level, BlockPos endPos, BlockState endState) {
		int currentSize = endState.getValue(SIZE);
		int remaining = MAX_CHAIN_SIZE - 1 - currentSize;
		int toGrow = Math.min(GROWTH_LENGTH, remaining);
		if (toGrow <= 0) return;

		Direction facing = endState.getValue(FACING);

		int canPlace = 0;
		BlockPos checkPos = endPos;
		for (int i = 1; i <= toGrow; i++) {
			checkPos = checkPos.relative(facing);
			if (!level.isEmptyBlock(checkPos)) break;
			canPlace++;
		}
		if (canPlace == 0) return;

		SegmentType convertedType = currentSize == 0 ? SegmentType.BASE : SegmentType.MIDDLE;
		boolean convertedFlowering = convertedType == SegmentType.MIDDLE && level.random.nextBoolean();
		level.setBlock(endPos, endState.setValue(SEGMENT, convertedType).setValue(FLOWERING, convertedFlowering).setValue(COUNTING, false), 3);
		level.playSound(null, endPos, getSoundType(endState).getPlaceSound(), SoundSource.BLOCKS);

		BlockPos cursor = endPos;
		for (int placed = 1; placed <= canPlace; placed++) {
			cursor = cursor.relative(facing);
			int newSize = currentSize + placed;
			boolean isNewEnd = placed == canPlace;

			pushEntitiesOutOfWay(level, cursor, facing);

			SegmentType type = isNewEnd ? SegmentType.END : SegmentType.MIDDLE;
			boolean flowering = type == SegmentType.MIDDLE && level.random.nextBoolean();

			BlockState newState = endState
					.setValue(SEGMENT, type)
					.setValue(SIZE, newSize)
					.setValue(FLOWERING, flowering)
					.setValue(COUNTING, false);
			level.setBlock(cursor, newState, 3);
			level.playSound(null, cursor, getSoundType(newState).getPlaceSound(), SoundSource.BLOCKS);
		}

		level.scheduleTick(cursor, this, POLL_INTERVAL_TICKS);
	}

	private void pushEntitiesOutOfWay(Level level, BlockPos pos, Direction facing) {
		List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos));
		if (entities.isEmpty()) return;
		Vec3 push = new Vec3(-facing.getStepX(), 0.15, -facing.getStepZ()).scale(PUSHBACK_STRENGTH);
		for (Entity entity : entities) {
			entity.setDeltaMovement(entity.getDeltaMovement().add(push));
			entity.hurtMarked = true;
		}
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(SEGMENT) != SegmentType.END) return; // stale tick from before this grew/retracted past here

		boolean present = isPlayerStandingOn(level, pos);
		boolean counting = state.getValue(COUNTING);

		if (present) {
			if (counting) {
				level.setBlock(pos, state.setValue(COUNTING, false), 3);
			}
			level.scheduleTick(pos, this, POLL_INTERVAL_TICKS);
			return;
		}

		if (!counting) {
			level.setBlock(pos, state.setValue(COUNTING, true), 3);
			level.scheduleTick(pos, this, RETRACT_DELAY_TICKS);
			return;
		}

		retractOneStep(level, pos, state);
	}

	private void retractOneStep(ServerLevel level, BlockPos endPos, BlockState endState) {
		int currentSize = endState.getValue(SIZE);
		if (currentSize <= 0) {
			level.setBlock(endPos, endState.setValue(COUNTING, false), 3);
			level.scheduleTick(endPos, this, POLL_INTERVAL_TICKS);
			return;
		}

		Direction backwards = endState.getValue(FACING).getOpposite();
		BlockPos previousPos = endPos.relative(backwards);
		BlockState previousState = level.getBlockState(previousPos);

		if (isPlayerStandingOn(level, previousPos)) {
			level.setBlock(endPos, endState.setValue(COUNTING, false), 3);
			level.scheduleTick(endPos, this, POLL_INTERVAL_TICKS);
			return;
		}

		level.removeBlock(endPos, false);

		if (previousState.is(this)) {
			level.setBlock(previousPos, previousState.setValue(SEGMENT, SegmentType.END).setValue(COUNTING, true), 3);
			level.scheduleTick(previousPos, this, RETRACT_STEP_INTERVAL_TICKS);
		}
	}

	private boolean isPlayerStandingOn(Level level, BlockPos pos) {
		AABB aabb = new AABB(pos.getX(), pos.getY() + 0.4, pos.getZ(), pos.getX() + 1, pos.getY() + 1.0, pos.getZ() + 1);
		return !level.getEntitiesOfClass(Player.class, aabb).isEmpty();
	}

	/**
	 * Breaking any segment breaks everything after it in the chain (toward the tip).
	 * Recurses naturally: destroying the next segment triggers its own onRemove.
	 */
	@Override
	protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (!movedByPiston && !state.is(newState.getBlock())) {
			if (state.getValue(SEGMENT) != SegmentType.END) {
				Direction facing = state.getValue(FACING);
				BlockPos nextPos = pos.relative(facing);
				BlockState nextState = level.getBlockState(nextPos);
				if (nextState.is(this) && nextState.getValue(SIZE) == state.getValue(SIZE) + 1) {
					level.destroyBlock(nextPos, false);
					level.playSound(null, nextPos, getSoundType(state).getBreakSound(), SoundSource.BLOCKS);
				}
			}

			if (state.getValue(SIZE) > 0) {
				Direction backwards = state.getValue(FACING).getOpposite();
				BlockPos prevPos = pos.relative(backwards);
				BlockState prevState = level.getBlockState(prevPos);
				if (prevState.is(this) && prevState.getValue(SIZE) == state.getValue(SIZE) - 1 && prevState.getValue(SEGMENT) != SegmentType.END) {
					level.setBlock(prevPos, prevState.setValue(SEGMENT, SegmentType.END).setValue(COUNTING, false), 3);
					if (level instanceof ServerLevel serverLevel) {
						serverLevel.scheduleTick(prevPos, this, POLL_INTERVAL_TICKS);
					}
				}
			}
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return simpleCodec(ThornSproutBlock::new);
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean isCollisionShapeFullBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.getValue(SEGMENT) != SegmentType.END;
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(FLOWERING) && random.nextInt(10) == 0)
			level.addParticle(NetherDescentParticles.ARISIAN_LEAF.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(SEGMENT, FLOWERING, SIZE, FACING, COUNTING));
	}

	public enum SegmentType implements StringRepresentable {
		END("end"),
		MIDDLE("middle"),
		BASE("base");

		private final String name;

		SegmentType(String name) {
			this.name = name;
		}

		@Override
		public @NotNull String getSerializedName() {
			return this.name;
		}
	}
}