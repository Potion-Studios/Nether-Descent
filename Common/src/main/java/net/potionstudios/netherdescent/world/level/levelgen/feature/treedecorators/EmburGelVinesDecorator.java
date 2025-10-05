package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class EmburGelVinesDecorator extends TreeDecorator {
    public static final MapCodec<EmburGelVinesDecorator> CODEC = MapCodec.unit(() -> EmburGelVinesDecorator.INSTANCE);
    public static final EmburGelVinesDecorator INSTANCE = new EmburGelVinesDecorator();

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return NetherDescentTreeDecoratorType.EMBUR_GEL_VINES.get();
    }

    @Override
    public void place(@NotNull Context context) {
        RandomSource randomSource = context.random();
        context.leaves().forEach(blockPos -> {
            BlockPos blockPos1 = blockPos.below();
            if (context.isAir(blockPos1) && randomSource.nextInt(3) > 0) {
                context.setBlock(blockPos1, NetherDescentBlocks.EMBUR_GEL_VINES.get().defaultBlockState());
                while (context.isAir(blockPos1.below()) && randomSource.nextInt(4) > 0) {
                    blockPos1 = blockPos1.below();
                    context.setBlock(blockPos1, NetherDescentBlocks.EMBUR_GEL_VINES.get().defaultBlockState());
                    context.setBlock(blockPos1.above(), NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get().defaultBlockState());
                }
            }
        });
    }
}
