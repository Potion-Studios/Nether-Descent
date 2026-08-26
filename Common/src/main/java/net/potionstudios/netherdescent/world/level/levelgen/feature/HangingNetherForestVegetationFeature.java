package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.potionstudios.netherdescent.world.level.block.plants.HangingDoublePlantBlock;
import org.jetbrains.annotations.NotNull;

public class HangingNetherForestVegetationFeature extends Feature<NetherForestVegetationConfig> {
    public HangingNetherForestVegetationFeature(Codec<NetherForestVegetationConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NetherForestVegetationConfig> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        NetherForestVegetationConfig netherForestVegetationConfig = context.config();
        RandomSource randomSource = context.random();
        int i = blockPos.getY();
        if (i >= worldGenLevel.getMinY() + 1 && i + 1 < worldGenLevel.getMaxY()) {
            int j = 0;

            for(int k = 0; k < netherForestVegetationConfig.spreadWidth * netherForestVegetationConfig.spreadWidth; ++k) {
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(netherForestVegetationConfig.spreadWidth) - randomSource.nextInt(netherForestVegetationConfig.spreadWidth), randomSource.nextInt(netherForestVegetationConfig.spreadHeight) - randomSource.nextInt(netherForestVegetationConfig.spreadHeight), randomSource.nextInt(netherForestVegetationConfig.spreadWidth) - randomSource.nextInt(netherForestVegetationConfig.spreadWidth));
                BlockState blockState2 = netherForestVegetationConfig.stateProvider.getState(randomSource, blockPos2);
                if (blockState2.hasProperty(BlockStateProperties.HANGING)) {
                    blockState2 = blockState2.setValue(BlockStateProperties.HANGING, true);
                    if (blockState2.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                        if (blockPos2.getY() - 1 > worldGenLevel.getMinY() && worldGenLevel.isEmptyBlock(blockPos2) && worldGenLevel.isEmptyBlock(blockPos2.below()) && blockState2.canSurvive(worldGenLevel, blockPos2)) {
                            HangingDoublePlantBlock.placeHangingAt(worldGenLevel, blockState2, blockPos2, 2);
                            ++j;
                        }
                    } else if (worldGenLevel.isEmptyBlock(blockPos2) && blockPos2.getY() > worldGenLevel.getMinY() && blockState2.canSurvive(worldGenLevel, blockPos2)) {
                        worldGenLevel.setBlock(blockPos2, blockState2, 2);
                        ++j;
                    }
                }
            }

            return j > 0;
        } else {
            return false;
        }
    }
}
