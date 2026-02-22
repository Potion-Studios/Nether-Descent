package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

public class BasaltLineFeature extends Feature<NoneFeatureConfiguration> {
	public BasaltLineFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();

		boolean placed = false;
		int length = random.nextInt(5, 15);

		for (int i = 0; i < length; i++) {
			BlockPos pos = new BlockPos(origin.getX() + i, origin.above().getY(), origin.getZ());
			if (!level.isEmptyBlock(pos) || !level.getBlockState(pos.below()).is(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get())) {
				NetherDescent.LOGGER.info("Failed to place Basalt Line at {}, {}, {} because the spot was not empty or the block below was not Arisian Moss Block", pos.getX(), pos.getY(), pos.getZ());
				break;
			}
			setBlock(level, pos, Blocks.BASALT.defaultBlockState());
			if (level.isEmptyBlock(pos.above())) {
				int height = random.nextInt(1, 5);
				for (int j = 1; j < height; j++) {
					if (level.isEmptyBlock(pos.above(j)))
						setBlock(level, pos.above(j), Blocks.BASALT.defaultBlockState());
				}
			}


			NetherDescent.LOGGER.info("Placed Basalt Line at {}, {}, {}", origin.getX(), origin.getY(), origin.getZ());
			placed = true;
		}

		NetherDescent.LOGGER.info("Placed Basalt Line at {}, {}, {}", origin.getX(), origin.getY(), origin.getZ());
		return placed;
	}
}
