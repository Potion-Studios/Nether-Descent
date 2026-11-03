package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jspecify.annotations.NonNull;

public record HangingPlantFeature(Block base, Block plantHeadBlock, Block plantBodyBlock) implements Feature {
	public static final MapCodec<HangingPlantFeature> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base").forGetter(HangingPlantFeature::base),
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("head").forGetter(HangingPlantFeature::plantHeadBlock),
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body").forGetter(HangingPlantFeature::plantBodyBlock)
			).apply(instance, HangingPlantFeature::new)
	);

	@Override
	public boolean place(@NonNull WorldGenLevel level, @NonNull ChunkGenerator chunkGenerator, @NonNull RandomSource random, @NonNull BlockPos origin) {
		if (!level.isEmptyBlock(origin)) {
			return false;
		} else {
			BlockState blockState = level.getBlockState(origin.above());
			if (!blockState.is(base)) {
				return false;
			} else {
				this.placeRoofHangingPlants(level, random, origin, base, plantHeadBlock, plantBodyBlock);
				return true;
			}
		}
	}

	private void placeRoofHangingPlants(LevelAccessor level, RandomSource random, BlockPos pos, Block base, Block plantHeadBlock, Block plantBodyBlock) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for (int i = 0; i < 100; i++) {
			mutableBlockPos.setWithOffset(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
			if (level.isEmptyBlock(mutableBlockPos)) {
				BlockState blockState = level.getBlockState(mutableBlockPos.above());
				if (blockState.is(base)) {
					int j = Mth.nextInt(random, 1, 8);
					if (random.nextInt(6) == 0) {
						j *= 2;
					}

					if (random.nextInt(5) == 0) {
						j = 1;
					}

					placePlantsColumn(level, random, mutableBlockPos, plantHeadBlock, plantBodyBlock, j, 17, 25);
				}
			}
		}
	}

	private static void placePlantsColumn(LevelAccessor level, RandomSource random, BlockPos.MutableBlockPos pos, Block plantHeadBlock, Block plantBodyBlock, int height, int minAge, int maxAge) {
		for (int i = 0; i <= height; i++) {
			if (level.isEmptyBlock(pos)) {
				if (i == height || !level.isEmptyBlock(pos.below())) {
					level.setBlock(pos, plantHeadBlock.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, minAge, maxAge)), 2);
					break;
				}

				level.setBlock(pos, plantBodyBlock.defaultBlockState(), 2);
			}

			pos.move(Direction.DOWN);
		}
	}

	@Override
	public @NonNull MapCodec<? extends Feature> codec() {
		return CODEC;
	}
}
