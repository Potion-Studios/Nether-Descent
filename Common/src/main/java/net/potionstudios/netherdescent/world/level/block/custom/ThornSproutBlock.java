package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThornSproutBlock extends HorizontalDirectionalBlock {
	public static final EnumProperty<SegmentType> SEGMENT = EnumProperty.create("segment", SegmentType.class);
	public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 2);

	public ThornSproutBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SEGMENT, SegmentType.END).setValue(FLOWERING, false).setValue(SIZE, 0));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		if (context.getClickedFace().getAxis().isHorizontal()) {
			return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		}
		return null;
	}

	@Override
	protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return simpleCodec(ThornSproutBlock::new);
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (!level.isClientSide()) {
			if (state.getValue(SEGMENT) == SegmentType.END && state.getValue(SIZE) < 2) {
				//TODO: Grow the thorn 3 more
				level.setBlockAndUpdate(pos, state.setValue(SIZE, state.getValue(SIZE) + 1).setValue(SEGMENT, SegmentType.MIDDLE));
			}
		}
	}

	@Override
	protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);

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
