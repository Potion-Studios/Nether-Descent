package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CarpetPatchFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class CarpetPatchFeature extends Feature<CarpetPatchFeatureConfiguration> {
	public CarpetPatchFeature(Codec<CarpetPatchFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<CarpetPatchFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();

		CarpetPatchFeatureConfiguration config = context.config();
		int radius = config.radius().sample(context.random());

		boolean placed = false;

		for (int x = -radius; x <= radius; x++)
			for (int z = -radius; z <= radius; z++) {
				BlockPos pos = origin.offset(x, 0, z);
				if (level.isEmptyBlock(pos)) continue;
				setBlock(level, pos, config.base().defaultBlockState());
				if (level.isEmptyBlock(pos.above()) && config.carpetChance() <= context.random().nextFloat())
					setBlock(level, pos.above(), config.carpet().defaultBlockState());
				placed = true;
			}

		return placed;
	}
}
