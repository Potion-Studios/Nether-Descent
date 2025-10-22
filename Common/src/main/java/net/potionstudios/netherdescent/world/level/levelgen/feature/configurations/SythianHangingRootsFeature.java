package net.potionstudios.netherdescent.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class SythianHangingRootsFeature extends Feature<NoneFeatureConfiguration> {
	public SythianHangingRootsFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		if (!worldGenLevel.isEmptyBlock(blockPos)) {
			return false;
		} else {
			BlockState blockState = worldGenLevel.getBlockState(blockPos.above());
			if (!blockState.is(Blocks.NETHERRACK)) {
				return false;
			} else {
				this.placeRoofSythianRoots(worldGenLevel, randomSource, blockPos);
				return true;
			}
		}
	}

	private void placeRoofSythianRoots(LevelAccessor level, RandomSource random, BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for (int i = 0; i < 100; i++) {
			mutableBlockPos.setWithOffset(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
			if (level.isEmptyBlock(mutableBlockPos)) {
				BlockState blockState = level.getBlockState(mutableBlockPos.above());
				if (blockState.is(Blocks.NETHERRACK)) {
					int j = Mth.nextInt(random, 1, 8);
					if (random.nextInt(6) == 0) {
						j *= 2;
					}

					if (random.nextInt(5) == 0) {
						j = 1;
					}

					placeSythianRootsColumn(level, random, mutableBlockPos, j, 17, 25);
				}
			}
		}
	}

	private static void placeSythianRootsColumn(LevelAccessor level, RandomSource random, BlockPos.MutableBlockPos pos, int height, int minAge, int maxAge) {
		for (int i = 0; i <= height; i++) {
			if (level.isEmptyBlock(pos)) {
				if (i == height || !level.isEmptyBlock(pos.below())) {
					level.setBlock(pos, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, minAge, maxAge)), 2);
					break;
				}

				level.setBlock(pos, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get().defaultBlockState(), 2);
			}

			pos.move(Direction.DOWN);
		}
	}
}
