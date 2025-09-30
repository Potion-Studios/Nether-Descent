package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CeilingHangingVinesFeatureConfiguration;

public class CeilingHangingVinesFeature extends Feature<CeilingHangingVinesFeatureConfiguration> {
	public CeilingHangingVinesFeature(Codec<CeilingHangingVinesFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<CeilingHangingVinesFeatureConfiguration> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		CeilingHangingVinesFeatureConfiguration config = context.config();
		if (worldGenLevel.isEmptyBlock(blockPos.below())) {
			if (worldGenLevel.getBlockState(blockPos).is(config.ceiling())) {
				BlockPos.MutableBlockPos mutableBlockPos = blockPos.below().mutable();
				worldGenLevel.setBlock(mutableBlockPos, config.base(), 2);
				mutableBlockPos.move(0, -1, 0);
				worldGenLevel.setBlock(mutableBlockPos, config.vine(), 2);
				while (worldGenLevel.isEmptyBlock(mutableBlockPos.below()) && randomSource.nextFloat() < config.growth()) {
					worldGenLevel.setBlock(mutableBlockPos.move(0, -1, 0), config.vine(), 2);
				}
				return true;
			}
		}
		return false;
	}
}
