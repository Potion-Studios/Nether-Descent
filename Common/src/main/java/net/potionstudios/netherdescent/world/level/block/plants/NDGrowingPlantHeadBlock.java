package net.potionstudios.netherdescent.world.level.block.plants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class NDGrowingPlantHeadBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<NDGrowingPlantHeadBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    propertiesCodec(),
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body_block").forGetter(NDGrowingPlantHeadBlock::getBodyBlock)
            ).apply(instance, NDGrowingPlantHeadBlock::new)
    );
	private static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    private final Supplier<? extends Block> bodyBlock;

	public NDGrowingPlantHeadBlock(Properties properties, Supplier<? extends Block> bodyBlock) {
		super(properties, Direction.DOWN, SHAPE, true, 0.1D);
        this.bodyBlock = bodyBlock;
	}

    protected NDGrowingPlantHeadBlock(Properties properties, Block bodyBlock) {
        this(properties, () -> bodyBlock);
    }

    @Override
    public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (state.is(NetherDescentBlocks.EMBUR_GEL_VINES.get()) && level.isEmptyBlock(pos.below()) && random.nextInt(10) == 0) {
            Vec3 vec3 = state.getOffset(pos);
            double e = (double)pos.getX() + (double)0.5F + vec3.x;
            double f = (double)((float)(pos.getY() + 1) - 0.6875F) - (double)0.0625F;
            double g = (double)pos.getZ() + (double)0.5F + vec3.z;
            level.addParticle(NetherDescentParticles.EMBUR_GEL_DRIP.get(), e, f, g, 0.0F, 0.0F, 0.0F);
        }
    }

    @Override
	protected @NonNull MapCodec<? extends GrowingPlantHeadBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NonNull Block getBodyBlock() {
		return bodyBlock.get();
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(@NonNull RandomSource random) {
		return NetherVines.getBlocksToGrowWhenBonemealed(random);
	}

	@Override
	protected boolean canGrowInto(@NonNull BlockState state) {
		return NetherVines.isValidGrowthState(state);
	}
}
