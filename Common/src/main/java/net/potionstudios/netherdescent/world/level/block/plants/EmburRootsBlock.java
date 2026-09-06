package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jspecify.annotations.NonNull;

public class EmburRootsBlock extends NetherRootsBlock implements BonemealableBlock {
    public EmburRootsBlock(Properties properties, TagKey<Block> tag) {
        super(tag, properties);
    }

    @Override
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        BlockState doubleplantblock = NetherDescentBlocks.TALL_EMBUR_ROOTS.get().defaultBlockState();
        if (doubleplantblock.canSurvive(level, pos))
            DoublePlantBlock.placeAt(level,doubleplantblock, pos, Block.UPDATE_CLIENTS);
    }
}
