package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NetherDescentNyliumBlock extends NyliumBlock {

    private final Supplier<Block> spreadBlock;
    private final Supplier<ResourceKey<ConfiguredFeature<?, ?>>> feature;

    public NetherDescentNyliumBlock(Properties properties, Supplier<Block> spreadBlock, Supplier<ResourceKey<ConfiguredFeature<?, ?>>> feature) {
        super(properties);
        this.spreadBlock = spreadBlock;
        this.feature = feature;
    }

    public NetherDescentNyliumBlock(Properties properties, Block spreadBlock, Supplier<ResourceKey<ConfiguredFeature<?, ?>>> feature) {
        this(properties, () -> spreadBlock, feature);
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!NyliumBlock.canBeNylium(state, level, pos))
            level.setBlockAndUpdate(pos, spreadBlock.get().defaultBlockState());
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
	    level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(feature.get()).ifPresent(reference -> reference.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }
}
