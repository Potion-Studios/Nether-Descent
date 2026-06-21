package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NDGrowingPlantHeadBlock extends GrowingPlantHeadBlock {
	private static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    private final Supplier<? extends Block> bodyBlock;

	public NDGrowingPlantHeadBlock(Properties properties, Supplier<? extends Block> bodyBlock) {
		super(properties, Direction.DOWN, SHAPE, true, 0.1D);
        this.bodyBlock = bodyBlock;
	}

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (state.is(NetherDescentBlocks.EMBUR_GEL_VINES.get()) && level.isEmptyBlock(pos.below()) && random.nextInt(10) == 0) {
            Vec3 vec3 = state.getOffset(level, pos);
            double e = (double)pos.getX() + (double)0.5F + vec3.x;
            double f = (double)((float)(pos.getY() + 1) - 0.6875F) - (double)0.0625F;
            double g = (double)pos.getZ() + (double)0.5F + vec3.z;
            level.addParticle(NetherDescentParticles.EMBUR_GEL_DRIP.get(), e, f, g, 0.0F, 0.0F, 0.0F);
        }
    }


	@Override
	protected @NotNull Block getBodyBlock() {
		return bodyBlock.get();
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
