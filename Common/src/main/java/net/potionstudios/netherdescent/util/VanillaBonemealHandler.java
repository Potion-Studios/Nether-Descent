package net.potionstudios.netherdescent.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentTreeFeatures;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.Optional;

public class VanillaBonemealHandler {
    public static boolean boneMealEventHandler(Level level, BlockPos blockPos, BlockState state, ItemStack stack) {
        if (state.is(Blocks.CRIMSON_FUNGUS))
            return crimsonFungusHandler(level, blockPos, state, stack);
        else if (state.is(Blocks.SOUL_SOIL))
            return netherrackLikeHandler(level, blockPos, state, stack, NetherDescentBlocks.WAILING_NYLIUM.get());
        else if (state.is(Blocks.BLACKSTONE))
            return netherrackLikeHandler(level, blockPos, state, stack, NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get());
        else if (state.is(Blocks.NETHERRACK))
            return netherrackHandler(level, blockPos, state, stack, NetherDescentBlocks.SYTHIAN_NYLIUM.get());

        return false;
    }

    private static boolean crimsonFungusHandler(Level level, BlockPos blockPos, BlockState state, ItemStack stack) {
        if (level.getBlockState(blockPos.below()).is(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get())) {
            if (state.getBlock() instanceof BonemealableBlock bonemealableBlock) {
                if (bonemealableBlock.isBonemealSuccess(level, level.getRandom(), blockPos, state)) {
                    if (level instanceof ServerLevel serverLevel) {
                        getFeature(serverLevel).ifPresent(holder -> holder.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), blockPos));
                        stack.shrink(1);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static boolean netherrackLikeHandler(Level level, BlockPos pos, BlockState state, ItemStack stack, Block nylium) {
        if (level.getBlockState(pos.above()).propagatesSkylightDown(level, pos)) {
            for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(nylium)) {
                    level.setBlockAndUpdate(pos, nylium.defaultBlockState());
                    stack.shrink(1);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean netherrackHandler(Level level, BlockPos pos, BlockState state, ItemStack stack, Block nylium) {
        if (level.getBlockState(pos.above()).propagatesSkylightDown(level, pos)) {
            boolean crimson = false;
            boolean warped = false;
            boolean n = false;

            for(BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                BlockState blockState = level.getBlockState(blockPos);
                if (blockState.is(Blocks.WARPED_NYLIUM))
                    warped = true;
                else if (blockState.is(Blocks.CRIMSON_NYLIUM))
                    crimson = true;
                else if (blockState.is(nylium))
                    n = true;

                if (warped && crimson && n) break;
            }

            if (!n) return false;

            if (warped && crimson) {
                BlockState state1;
                int random = level.getRandom().nextInt(0, 2);
                if (random == 0)
                    state1 = Blocks.WARPED_NYLIUM.defaultBlockState();
                else if (random == 1) {
                    state1 = Blocks.CRIMSON_NYLIUM.defaultBlockState();
                } else {
                    state1 = nylium.defaultBlockState();
                }
                level.setBlock(pos, state1, 3);
            } else if (crimson) {
                level.setBlock(pos, level.getRandom().nextBoolean() ? Blocks.CRIMSON_NYLIUM.defaultBlockState() : nylium.defaultBlockState(), 3);
            } else if (warped) {
                level.setBlock(pos, level.getRandom().nextBoolean() ? nylium.defaultBlockState() : Blocks.WARPED_NYLIUM.defaultBlockState(), 3);
            }  else {
                level.setBlock(pos, nylium.defaultBlockState(), 3);
            }
            stack.shrink(1);
            return true;
        }
        return false;
    }

    private static Optional<? extends Holder<ConfiguredFeature<?, ?>>> getFeature(LevelReader level) {
        return level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(NetherDescentTreeFeatures.CRIMSON_FUNGUS_PLANTED);
    }
}
