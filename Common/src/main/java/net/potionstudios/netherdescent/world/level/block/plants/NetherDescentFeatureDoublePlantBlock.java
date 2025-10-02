package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NetherDescentFeatureDoublePlantBlock extends NetherDescentDoublePlantBlock implements BonemealableBlock {

    private final Supplier<ConfiguredFeature<?, ?>> feature;

    public NetherDescentFeatureDoublePlantBlock(Properties properties, Supplier<ConfiguredFeature<?, ?>> feature) {
        super(properties);
        this.feature = feature;
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
        feature.get().place(level, level.getChunkSource().getGenerator(), random, pos);
    }
}
