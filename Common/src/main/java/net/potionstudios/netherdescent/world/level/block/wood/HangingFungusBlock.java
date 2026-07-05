package net.potionstudios.netherdescent.world.level.block.wood;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.plants.HangingNDBushBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HangingFungusBlock extends HangingNDBushBlock implements BonemealableBlock {
    public static final MapCodec<HangingFungusBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(fungusBlock -> fungusBlock.feature),
                            ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("hanging_feature").forGetter(fungusBlock -> fungusBlock.hangingFeature),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grows_on").forGetter(fungusBlock -> fungusBlock.requiredBlock),
                            propertiesCodec()
                    )
                    .apply(instance, HangingFungusBlock::new)
    );
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    private final Block requiredBlock;
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;
    private final ResourceKey<ConfiguredFeature<?, ?>> hangingFeature;
    public HangingFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, ResourceKey<ConfiguredFeature<?, ?>> hangingFeature, Block requiredBlock, BlockBehaviour.Properties properties) {
        super(properties, SHAPE, SHAPE);
        this.feature = feature;
        this.hangingFeature = hangingFeature;
        this.requiredBlock = requiredBlock;
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(Blocks.MYCELIUM) || super.mayPlaceOn(state, level, pos);
    }

    private Optional<? extends Holder<ConfiguredFeature<?, ?>>> getFeature(LevelReader level, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(feature);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return state.getValue(HANGING) ? level.getBlockState(pos.above()).is(this.requiredBlock) : level.getBlockState(pos.below()).is(this.requiredBlock);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        this.getFeature(level, state.getValue(HANGING) ? this.hangingFeature : this.feature).ifPresent(holder -> holder.value().place(level, level.getChunkSource().getGenerator(), random, pos));
    }
}
