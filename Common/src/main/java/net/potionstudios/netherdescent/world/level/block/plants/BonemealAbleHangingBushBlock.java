package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BonemealAbleHangingBushBlock extends HangingNDBushBlock implements BonemealableBlock {
	private final Supplier<NetherDescentDoublePlantBlock> block;
	public BonemealAbleHangingBushBlock(Properties properties, Supplier<NetherDescentDoublePlantBlock> block, VoxelShape shape, VoxelShape hangingShape) {
		super(properties, shape, hangingShape);
		this.block = block;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (level.getBlockState(pos.above()).isAir()) {
			BlockState blockState = block.get().defaultBlockState();
			level.setBlock(pos, blockState, 2);
			level.setBlock(pos.above(), blockState.setValue(NetherDescentDoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
		}
	}
}
