package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jspecify.annotations.NonNull;

public class BlueNetherrackBlock extends NetherrackBlock {
    public BlueNetherrackBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, BlockPos pos, @NonNull BlockState state) {
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState neighborState = level.getBlockState(blockPos);
            if (neighborState.is(NetherDescentBlocks.EMBUR_NYLIUM.get()))
                level.setBlock(pos, neighborState, 2);
        }
    }
}
