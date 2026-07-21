package net.potionstudios.netherdescent.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class NDFungusBlock extends FungusBlock {
    public NDFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Block requiredBlock, Properties properties) {
        super(feature, requiredBlock, properties);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        super.performBonemeal(level, random, pos, state);
        this.getFeature(level).ifPresent((holder) -> {
            boolean b =  holder.value().place(level, level.getChunkSource().getGenerator(), random, pos);
            if (b && level.getBlockState(pos).is(this))
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        });
    }
}
