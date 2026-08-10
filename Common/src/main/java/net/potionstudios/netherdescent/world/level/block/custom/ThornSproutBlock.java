package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
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
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThornSproutBlock extends HorizontalDirectionalBlock {
	private static final VoxelShape SHAPE = Block.box(0, 8, 0, 16, 14, 16);
	public static final EnumProperty<SegmentType> SEGMENT = EnumProperty.create("segment", SegmentType.class);
	public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 17);

	public ThornSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SEGMENT, SegmentType.END).setValue(FLOWERING, false).setValue(SIZE, 0));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		if (context.getClickedFace().getAxis().isHorizontal()) {
			BlockPos attachPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
			BlockState attachState = context.getLevel().getBlockState(attachPos);
			if (attachState.isFaceSturdy(context.getLevel(), attachPos, context.getClickedFace()) || (attachState.is(this) && attachState.getValue(SEGMENT).equals(SegmentType.END) && attachState.getValue(SIZE) != 17))
				return defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(SIZE, 0);
		}
		return null;
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
