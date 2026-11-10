package net.potionstudios.netherdescent.world.level.block.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.plants.HangingNDBushBlock;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class HangingFungusBlock extends HangingNDBushBlock implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    private final Block requiredBlock;
    private final ResourceKey<Feature> feature;
    private final ResourceKey<Feature> hangingFeature;
    public HangingFungusBlock(ResourceKey<Feature> feature, ResourceKey<Feature> hangingFeature, Block requiredBlock, BlockBehaviour.Properties properties) {
        super(properties, SHAPE, SHAPE);
        this.feature = feature;
        this.hangingFeature = hangingFeature;
        this.requiredBlock = requiredBlock;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        return state.is(Blocks.MYCELIUM) || super.mayPlaceOn(state, level, pos);
    }

    private Optional<? extends Holder<Feature>> getFeature(LevelReader level, ResourceKey<Feature> feature) {
        return level.registryAccess().lookupOrThrow(Registries.FEATURE).get(feature);
    }

    @Override
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, BlockState state, @NonNull BonemealSource source) {
        return state.getValue(HANGING) ? level.getBlockState(pos.above()).is(this.requiredBlock) : level.getBlockState(pos.below()).is(this.requiredBlock);
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, BlockState state, @NonNull BonemealSource source) {
        this.getFeature(level, state.getValue(HANGING) ? this.hangingFeature : this.feature).ifPresent(holder -> {
            boolean b = holder.value().place(level, level.getChunkSource().getGenerator(), random, pos);
            if (b && level.getBlockState(pos).is(this))
                level.destroyBlock(pos, false);
        });
    }
}
