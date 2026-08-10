package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThornSproutBlock extends HorizontalDirectionalBlock {
	private static final VoxelShape SHAPE = Block.box(0, 8, 0, 16, 14, 16);
	public static final EnumProperty<SegmentType> SEGMENT = EnumProperty.create("segment", SegmentType.class);
	public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 17);
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public ThornSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SEGMENT, SegmentType.END).setValue(FLOWERING, false).setValue(SIZE, 0));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		if (context.getClickedFace().getAxis().isHorizontal()) {
			BlockPos attachPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
			BlockState attachState = context.getLevel().getBlockState(attachPos);
			if (attachState.isFaceSturdy(context.getLevel(), attachPos, context.getClickedFace()) || (attachState.is(this) && attachState.getValue(SEGMENT).equals(SegmentType.END) && attachState.getValue(SIZE) != 17)){
				if (attachState.is(this)) {
					context.getLevel().setBlockAndUpdate(attachPos, attachState.setValue(SEGMENT, SegmentType.MIDDLE));
				}
				return defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(SIZE, 0);
			}

		}
		return null;
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (entity instanceof LivingEntity) {
			int size = state.getValue(SIZE);
			if (size < 17 && state.getValue(SEGMENT).equals(SegmentType.END)) {
				extend(level, pos, state, level.getRandom());
			}
		}
	}

	private void extend(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull RandomSource randomSource) {
		int size = state.getValue(SIZE);
		Direction direction = state.getValue(FACING);
		for (int i = 1; i <= 3; i++) {
			int spotSize = size + i;
			BlockPos spotPos = pos.relative(direction, i);
			if (spotSize > 17 || !level.isEmptyBlock(spotPos))
				return;
			else {
				level.setBlockAndUpdate(spotPos.relative(direction.getOpposite(), 1), state.setValue(SEGMENT, state.getValue(SIZE) == 0 ? SegmentType.BASE : SegmentType.MIDDLE).setValue(FLOWERING, randomSource.nextBoolean()));
				level.scheduleTick(spotPos.relative(direction.getOpposite(), 1), this, 301);
				level.setBlockAndUpdate(spotPos, state.setValue(SEGMENT, SegmentType.END));
				level.scheduleTick(spotPos, this, 300);
			}
		}
	}

	@Override
	protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
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
		super.createBlockStateDefinition(builder.add(SEGMENT, FLOWERING, SIZE, FACING, PERSISTENT));
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
