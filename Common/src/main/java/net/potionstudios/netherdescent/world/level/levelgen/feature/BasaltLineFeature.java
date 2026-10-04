package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jspecify.annotations.NonNull;

public record BasaltLineFeature() implements Feature {
	public static final MapCodec<BasaltLineFeature> CODEC = MapCodec.unit(BasaltLineFeature::new);

	@Override
	public boolean place(@NonNull WorldGenLevel level, @NonNull ChunkGenerator chunkGenerator, @NonNull RandomSource random, @NonNull BlockPos origin) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(origin.getX(), origin.getY(), origin.getZ());
		int length = random.nextInt(10) + 5;
		Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Direction drift = random.nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();

		for (int i = 0; i < length; i++) {
			if (level.getBlockState(mutable).canBeReplaced() && level.getBlockState(mutable.below()).isFaceSturdy(level, mutable.below(), Direction.UP)) {
				placeBlockAndMove(level, mutable, dir, random);
			} else if (level.getBlockState(mutable.below()).canBeReplaced() && level.getBlockState(mutable.below().below()).isFaceSturdy(level, mutable.below().below(), Direction.UP)) {
				mutable.move(Direction.DOWN);
				placeBlockAndMove(level, mutable, dir, random);
			} else if (level.getBlockState(mutable.above()).canBeReplaced() && level.getBlockState(mutable.above().above()).isFaceSturdy(level, mutable.above().above(), Direction.UP)) {
				mutable.move(Direction.UP);
				placeBlockAndMove(level, mutable, dir, random);
			} else return i >= 3;

			if (random.nextInt(5) == 0)
				mutable.move(drift);
		}

		return true;
	}

	private void placeBlockAndMove(WorldGenLevel level, BlockPos.MutableBlockPos mutable, Direction dir, RandomSource random) {
		int height = random.nextInt(1,5);
		for (int j = 0; j < height; j++) {
			setBlock(level, mutable.above(j), Blocks.BASALT.defaultBlockState());
		}
		mutable.move(dir);
	}

	@Override
	public @NonNull MapCodec<? extends Feature> codec() {
		return CODEC;
	}
}
