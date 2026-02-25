package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThornSproutBlock extends HorizontalDirectionalBlock {
	public static final EnumProperty<SegmentType> SEGMENT = EnumProperty.create("segment", SegmentType.class);
	public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 3);

	public ThornSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SEGMENT, SegmentType.END).setValue(FLOWERING, false).setValue(SIZE, 0));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		if (context.getClickedFace().getAxis().isHorizontal())
			return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		return null;
	}

	@Override
	protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return simpleCodec(ThornSproutBlock::new);
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (!level.isClientSide() && entity instanceof LivingEntity) {
			if (state.getValue(SEGMENT) == SegmentType.END && state.getValue(SIZE) < 3)
				growThorn(level, pos, state);

			BlockPos tipPos = findTip(level, pos, state);
			level.scheduleTick(tipPos, this, 300);
		}
	}

	private BlockPos findTip(Level level, BlockPos pos, BlockState state) {
		BlockPos current = pos;
		BlockState currentState = state;
		while (currentState.is(this) && currentState.getValue(SEGMENT) != SegmentType.END) {
			current = current.relative(currentState.getValue(FACING));
			currentState = level.getBlockState(current);
		}
		return current;
	}

	private void growThorn(Level level, BlockPos pos, BlockState state) {
		int currentSize = state.getValue(SIZE);
		if (currentSize >= 3) return;

		int newSize = currentSize + 1;
		level.setBlockAndUpdate(pos, state.setValue(SEGMENT, SegmentType.BASE).setValue(SIZE, newSize));
		level.playSound(null, pos, this.getSoundType(state).getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

		BlockPos nextPos = pos;
		for (int i = 1; i <= 3; i++) {
			nextPos = nextPos.relative(state.getValue(FACING));
			if (level.getBlockState(nextPos).canBeReplaced()) {
				SegmentType type = (i == 3) ? SegmentType.END : SegmentType.MIDDLE;
				
				AABB aabb = new AABB(nextPos);
				for (Entity entity : level.getEntitiesOfClass(Entity.class, aabb)) {
					Vec3 movement = Vec3.atLowerCornerOf(state.getValue(FACING).getNormal()).scale(0.5);
					entity.move(MoverType.SHULKER_BOX, movement);
				}

				level.setBlockAndUpdate(nextPos, defaultBlockState()
						.setValue(FACING, state.getValue(FACING))
						.setValue(FLOWERING, level.getRandom().nextBoolean())
						.setValue(SEGMENT, type)
						.setValue(SIZE, newSize));
				level.playSound(null, nextPos, this.getSoundType(state).getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
			} else {
				if (i > 1) {
					BlockPos prevPos = nextPos.relative(state.getValue(FACING).getOpposite());
					BlockState prevState = level.getBlockState(prevPos);
					if (prevState.is(this)) {
						level.setBlockAndUpdate(prevPos, prevState.setValue(SEGMENT, SegmentType.END));
						level.playSound(null, prevPos, this.getSoundType(state).getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
					}
				} else {
					level.setBlockAndUpdate(pos, state.setValue(SEGMENT, SegmentType.END).setValue(SIZE, currentSize));
					level.playSound(null, pos, this.getSoundType(state).getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
				}
				break;
			}
		}
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);

		if (isEntityOnBlock(level, pos)) {
			level.scheduleTick(pos, this, 20);
			return;
		}

		if (state.getValue(SEGMENT) == SegmentType.END) {
			retractThorn(level, pos, state);
		}
	}

	private boolean isEntityOnBlock(Level level, BlockPos pos) {
		AABB aabb = new AABB(pos).expandTowards(0, 0.5, 0);
		return !level.getEntitiesOfClass(LivingEntity.class, aabb).isEmpty();
	}

	private void retractThorn(ServerLevel level, BlockPos pos, BlockState state) {
		if (state.getValue(SEGMENT) != SegmentType.END) return;

		int currentSize = state.getValue(SIZE);
		BlockPos prevPos = pos.relative(state.getValue(FACING).getOpposite());
		BlockState prevState = level.getBlockState(prevPos);

		if (prevState.is(this) && prevState.getValue(FACING) == state.getValue(FACING) && prevState.getValue(SIZE) == currentSize) {
			level.destroyBlock(pos, false);
			level.playSound(null, pos, this.getSoundType(state).getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
			if (prevState.getValue(SEGMENT) == SegmentType.BASE) {
				BlockState nextState = prevState.setValue(SEGMENT, SegmentType.END).setValue(SIZE, currentSize - 1);
				level.setBlockAndUpdate(prevPos, nextState);
				if (nextState.getValue(SIZE) > 0 || nextState.getValue(SEGMENT) != SegmentType.END) {
					level.scheduleTick(prevPos, this, 5);
				}
			} else {
				level.setBlockAndUpdate(prevPos, prevState.setValue(SEGMENT, SegmentType.END));
				level.scheduleTick(prevPos, this, 5);
			}
		} else {
			if (currentSize > 0) {
				level.destroyBlock(pos, false);
				level.playSound(null, pos, this.getSoundType(state).getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
			} else {
				level.setBlockAndUpdate(pos, state.setValue(SEGMENT, SegmentType.END).setValue(SIZE, 0));
			}
		}
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean moved) {
		if (!state.is(newState.getBlock())) {
			BlockPos nextPos = pos.relative(state.getValue(FACING));
			BlockState nextState = level.getBlockState(nextPos);
			if (nextState.is(this) && nextState.getValue(FACING) == state.getValue(FACING)) {
				if (nextState.getValue(SIZE).equals(state.getValue(SIZE)) && nextState.getValue(SEGMENT) != SegmentType.BASE) {
					level.destroyBlock(nextPos, false);
					//level.playLocalSound(pos, this.getSoundType(state).getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f, true);
				}
			}

			if (state.getValue(SEGMENT) == SegmentType.END) {
				BlockPos prevPos = pos.relative(state.getValue(FACING).getOpposite());
				BlockState prevState = level.getBlockState(prevPos);
				if (prevState.is(this) && prevState.getValue(FACING) == state.getValue(FACING)) {
					level.setBlockAndUpdate(prevPos, prevState.setValue(SEGMENT, SegmentType.END));
					level.scheduleTick(prevPos, this, 5);
				}
			}
		}
		super.onRemove(state, level, pos, newState, moved);
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return Block.box(0, 8, 0, 16, 14, 16);
	}

	@Override
	protected boolean isCollisionShapeFullBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.getValue(SEGMENT) != SegmentType.END;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(SEGMENT, FLOWERING, SIZE, FACING));
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
