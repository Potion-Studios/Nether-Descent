package net.potionstudios.netherdescent.world.level.block.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class EmburVineBlock extends GrowingPlantHeadBlock {
	public static final MapCodec<EmburVineBlock> CODEC = simpleCodec(EmburVineBlock::new);
	private static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

	public EmburVineBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, true, 0.1D);
	}


	@Override
	protected @NotNull MapCodec<? extends GrowingPlantHeadBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull Block getBodyBlock() {
		return NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get();
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(@NotNull RandomSource random) {
		return NetherVines.getBlocksToGrowWhenBonemealed(random);
	}

	@Override
	protected boolean canGrowInto(@NotNull BlockState state) {
		return NetherVines.isValidGrowthState(state);
	}
}
