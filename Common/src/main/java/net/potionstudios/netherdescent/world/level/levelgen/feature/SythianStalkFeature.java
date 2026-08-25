package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.SythianStalkBlock;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.SythianStalkFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class SythianStalkFeature extends Feature<SythianStalkFeatureConfiguration> {
	private static final BlockState STALK = NetherDescentBlocks.SYTHIAN_STALK.getBlockState()
			.setValue(SythianStalkBlock.AGE, 1)
			.setValue(SythianStalkBlock.LEAVES, BambooLeaves.NONE)
			.setValue(SythianStalkBlock.STAGE, 0);
	private static final BlockState STALK_FINAL_LARGE = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE).setValue(SythianStalkBlock.STAGE, 1);
	private static final BlockState STALK_TOP_LARGE = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE);
	private static final BlockState STALK_TOP_SMALL = STALK.setValue(SythianStalkBlock.LEAVES, BambooLeaves.SMALL);

	public SythianStalkFeature(Codec<SythianStalkFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<SythianStalkFeatureConfiguration> context) {
		int i = 0;
		BlockPos blockPos = context.origin();
		WorldGenLevel worldGenLevel = context.level();
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		if (worldGenLevel.isEmptyBlock(blockPos)) {
			boolean hanging = context.config().hanging();
			if (NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.HANGING, hanging).canSurvive(worldGenLevel, mutableBlockPos)) {
				int j = context.random().nextInt(12) + 5;
				Direction move = hanging ? Direction.DOWN : Direction.UP;
				for (int k = 0; k < j && worldGenLevel.isEmptyBlock(mutableBlockPos); k++) {
					worldGenLevel.setBlock(mutableBlockPos, STALK.setValue(SythianStalkBlock.HANGING, hanging), 2);
					mutableBlockPos.move(move);
				}

				int check = hanging ? blockPos.getY() - mutableBlockPos.getY() : mutableBlockPos.getY() - blockPos.getY();

				if (check >= 3) {
                    if (!worldGenLevel.isEmptyBlock(mutableBlockPos.offset(0, hanging ? -3 : 3, 0)))
                        mutableBlockPos.move(move.getOpposite(), 3);
					worldGenLevel.setBlock(mutableBlockPos, STALK_FINAL_LARGE.setValue(SythianStalkBlock.HANGING, hanging), 2);
					worldGenLevel.setBlock(mutableBlockPos.move(move), STALK_TOP_LARGE.setValue(SythianStalkBlock.HANGING, hanging), 2);
					worldGenLevel.setBlock(mutableBlockPos.move(move), STALK_TOP_SMALL.setValue(SythianStalkBlock.HANGING, hanging), 2);
				}
			}

			i++;
		}
		return i > 0;
	}
}
