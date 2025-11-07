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

public class SythianStalkDownwardFeature extends Feature<NoneFeatureConfiguration> {
	private static final BlockState STALK = NetherDescentBlocks.SYTHIAN_STALK.get()
			.defaultBlockState()
			.setValue(SythianStalkBlock.AGE, 1)
			.setValue(SythianStalkBlock.LEAVES, BambooLeaves.NONE)
			.setValue(SythianStalkBlock.STAGE, 0)
			.setValue(SythianStalkBlock.HANGING, true);
	private static final BlockState STALK_FINAL_LARGE = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE).setValue(SythianStalkBlock.STAGE, 1);
	private static final BlockState STALK_TOP_LARGE = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE);
	private static final BlockState STALK_TOP_SMALL = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.SMALL);

	public SythianStalkDownwardFeature(Codec<NoneFeatureConfiguration> codec) {
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
			if (NetherDescentBlocks.SYTHIAN_STALK.get().defaultBlockState().setValue(SythianStalkBlock.HANGING, true).canSurvive(worldGenLevel, mutableBlockPos)) {
				int j = randomSource.nextInt(12) + 5;
				for (int k = 0; k < j && worldGenLevel.isEmptyBlock(mutableBlockPos); k++) {
					worldGenLevel.setBlock(mutableBlockPos, STALK, 2);
					mutableBlockPos.move(Direction.DOWN, 1);
				}

				if (blockPos.getY() - mutableBlockPos.getY() >= 3) {
					worldGenLevel.setBlock(mutableBlockPos, STALK_FINAL_LARGE, 2);
					worldGenLevel.setBlock(mutableBlockPos.move(Direction.DOWN, 1), STALK_TOP_LARGE, 2);
					worldGenLevel.setBlock(mutableBlockPos.move(Direction.DOWN, 1), STALK_TOP_SMALL, 2);
				}
			}

			i++;
		}
		return i > 0;
	}
}
