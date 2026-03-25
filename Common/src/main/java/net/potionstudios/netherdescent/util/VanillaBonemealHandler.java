package net.potionstudios.netherdescent.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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

    private static Optional<? extends Holder<ConfiguredFeature<?, ?>>> getFeature(LevelReader level) {
        return level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(NetherDescentTreeFeatures.CRIMSON_FUNGUS_PLANTED);
    }
}
