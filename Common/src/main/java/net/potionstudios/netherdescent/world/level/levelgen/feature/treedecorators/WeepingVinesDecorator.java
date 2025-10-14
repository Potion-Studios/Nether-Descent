package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class WeepingVinesDecorator extends TreeDecorator {
    public static final MapCodec<WeepingVinesDecorator> CODEC = MapCodec.unit(() -> WeepingVinesDecorator.INSTANCE);
    public static final WeepingVinesDecorator INSTANCE = new WeepingVinesDecorator();

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return NetherDescentTreeDecoratorType.WEEPING_VINES.get();
    }

    @Override
    public void place(@NotNull Context context) {
        RandomSource randomSource = context.random();
        context.leaves().forEach(blockPos -> {
            BlockPos blockPos1 = blockPos.below();
            if (context.isAir(blockPos1) && randomSource.nextInt(3) > 0) {
                context.setBlock(blockPos1, Blocks.WEEPING_VINES.defaultBlockState());
                while (context.isAir(blockPos1.below()) && randomSource.nextInt(4) > 0) {
                    blockPos1 = blockPos1.below();
                    context.setBlock(blockPos1, Blocks.WEEPING_VINES.defaultBlockState());
                    context.setBlock(blockPos1.above(), Blocks.WEEPING_VINES_PLANT.defaultBlockState());
                }
            }
        });
    }
}
