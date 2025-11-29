package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.HangingPlantFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class HangingPlantFeature extends Feature<HangingPlantFeatureConfiguration> {
	public HangingPlantFeature(Codec<HangingPlantFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<HangingPlantFeatureConfiguration> context) {
		HangingPlantFeatureConfiguration config = context.config();
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		if (!worldGenLevel.isEmptyBlock(blockPos)) {
			return false;
		} else {
			BlockState blockState = worldGenLevel.getBlockState(blockPos.above());
			if (!blockState.is(config.base())) {
				return false;
			} else {
				this.placeRoofHangingPlants(worldGenLevel, randomSource, blockPos, config);
				return true;
			}
		}
	}

	private void placeRoofHangingPlants(LevelAccessor level, RandomSource random, BlockPos pos, HangingPlantFeatureConfiguration config) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for (int i = 0; i < 100; i++) {
			mutableBlockPos.setWithOffset(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
			if (level.isEmptyBlock(mutableBlockPos)) {
				BlockState blockState = level.getBlockState(mutableBlockPos.above());
				if (blockState.is(config.base())) {
					int j = Mth.nextInt(random, 1, 8);
					if (random.nextInt(6) == 0) {
						j *= 2;
					}

					if (random.nextInt(5) == 0) {
						j = 1;
					}

					placePlantsColumn(level, random, mutableBlockPos, config, j, 17, 25);
				}
			}
		}
	}

	private static void placePlantsColumn(LevelAccessor level, RandomSource random, BlockPos.MutableBlockPos pos, HangingPlantFeatureConfiguration config, int height, int minAge, int maxAge) {
		for (int i = 0; i <= height; i++) {
			if (level.isEmptyBlock(pos)) {
				if (i == height || !level.isEmptyBlock(pos.below())) {
					level.setBlock(pos, config.plantHeadBlock().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, minAge, maxAge)), 2);
					break;
				}

				level.setBlock(pos, config.plantBodyBlock().defaultBlockState(), 2);
			}

			pos.move(Direction.DOWN);
		}
	}
}
