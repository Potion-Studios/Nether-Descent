package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

public class GrowingPlantVinesDecorator extends TreeDecorator {
    public static final MapCodec<GrowingPlantVinesDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockState.CODEC.fieldOf("head").forGetter(GrowingPlantVinesDecorator::head),
                    BlockState.CODEC.fieldOf("body").forGetter(GrowingPlantVinesDecorator::body)
            ).apply(instance, GrowingPlantVinesDecorator::new)
    );

    private final BlockState head;
    private final BlockState body;

    public GrowingPlantVinesDecorator(BlockState head, BlockState body) {
        this.head = head;
        this.body = body;
    }

    public GrowingPlantVinesDecorator(Block head, Block body) {
        this(head.defaultBlockState(), body.defaultBlockState());
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return NetherDescentTreeDecoratorType.GROWING_PLANT.get();
    }

    @Override
    public void place(@NotNull Context context) {
        RandomSource randomSource = context.random();
        LevelReader levelReader = (LevelReader) context.level();
        for (BlockPos blockPos: context.leaves()) {
            BlockPos blockPos1 = blockPos.below();
            if (head().canSurvive(levelReader, blockPos1) && randomSource.nextInt(3) > 0) {
                context.setBlock(blockPos1, head());
                while (body().canSurvive(levelReader, blockPos1.below()) && randomSource.nextInt(4) > 0) {
                    blockPos1 = blockPos1.below();
                    context.setBlock(blockPos1, head());
                    context.setBlock(blockPos1.above(), body());
                }
            }
        }
    }

    public BlockState head() {
        return this.head;
    }

    public BlockState body() {
        return this.body;
    }
}
