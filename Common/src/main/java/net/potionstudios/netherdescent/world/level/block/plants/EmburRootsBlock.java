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
import org.jetbrains.annotations.NotNull;

public class EmburRootsBlock extends NetherRootsBlock implements BonemealableBlock {
    public EmburRootsBlock(TagKey<Block> supportBlocks, Properties properties) {
        super(supportBlocks, properties);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockState doubleplantblock = NetherDescentBlocks.TALL_EMBUR_ROOTS.get().defaultBlockState();
        if (doubleplantblock.canSurvive(level, pos))
            DoublePlantBlock.placeAt(level,doubleplantblock, pos, Block.UPDATE_CLIENTS);
    }
}
