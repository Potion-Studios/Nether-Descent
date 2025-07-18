package net.potionstudios.netherdescent.world.level.block.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class EmburVinePlantBlock extends GrowingPlantBodyBlock {
	public static final MapCodec<EmburVinePlantBlock> CODEC = simpleCodec(EmburVinePlantBlock::new);
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public EmburVinePlantBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, true);
	}

	@Override
	protected @NotNull MapCodec<? extends GrowingPlantBodyBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
		return NetherDescentBlocks.EMBUR_GEL_VINES.get();
	}

	@Override
	protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
	}
}
