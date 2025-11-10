package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.SythianStalkBlock;
import org.jetbrains.annotations.NotNull;

public class SythianStalkFeature extends Feature<NoneFeatureConfiguration> {
	private static final BlockState STALK = NetherDescentBlocks.SYTHIAN_STALK.get()
			.defaultBlockState()
			.setValue(SythianStalkBlock.AGE, 1)
			.setValue(SythianStalkBlock.LEAVES, BambooLeaves.NONE)
			.setValue(SythianStalkBlock.STAGE, 0);
	private static final BlockState STALK_FINAL_LARGE = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE).setValue(SythianStalkBlock.STAGE, 1);
	private static final BlockState STALK_TOP_LARGE = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE);
	private static final BlockState STALK_TOP_SMALL = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.SMALL);

	public SythianStalkFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
		int i = 0;
		BlockPos blockPos = context.origin();
		WorldGenLevel worldGenLevel = context.level();
		RandomSource randomSource = context.random();
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		if (worldGenLevel.isEmptyBlock(blockPos)) {
			if (NetherDescentBlocks.SYTHIAN_STALK.get().defaultBlockState().canSurvive(worldGenLevel, mutableBlockPos)) {
				int j = randomSource.nextInt(12) + 5;
				for (int k = 0; k < j && worldGenLevel.isEmptyBlock(mutableBlockPos); k++) {
					worldGenLevel.setBlock(mutableBlockPos, STALK, 2);
					mutableBlockPos.move(Direction.UP, 1);
				}

				if (mutableBlockPos.getY() - blockPos.getY() >= 3) {
                    if (!worldGenLevel.isEmptyBlock(mutableBlockPos.offset(0, 3, 0)))
                        mutableBlockPos.move(Direction.DOWN, 3);
					worldGenLevel.setBlock(mutableBlockPos, STALK_FINAL_LARGE, 2);
					worldGenLevel.setBlock(mutableBlockPos.move(Direction.UP, 1), STALK_TOP_LARGE, 2);
					worldGenLevel.setBlock(mutableBlockPos.move(Direction.UP, 1), STALK_TOP_SMALL, 2);
				}
			}

			i++;
		}
		return i > 0;
	}
}
