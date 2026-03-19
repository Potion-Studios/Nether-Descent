package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BasaltLineFeature extends Feature<NoneFeatureConfiguration> {
	public BasaltLineFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(origin.getX(), origin.getY(), origin.getZ());
		int length = random.nextInt(10) + 5;
		Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		Direction drift = random.nextBoolean() ? dir.getClockWise() : dir.getCounterClockWise();

		for (int i = 0; i < length; i++) {
			if (level.getBlockState(mutable).canBeReplaced() && level.getBlockState(mutable.below()).isFaceSturdy(level, mutable.below(), Direction.DOWN)) {
				int height = random.nextInt(1,5);
				for (int j = 0; j < height; j++) {
					setBlock(level, mutable.above(j), Blocks.BASALT.defaultBlockState());
				}
				mutable.move(dir);
			}
			if (random.nextInt(5) == 0)
				mutable.move(drift);
		}

		return true;
	}
}
