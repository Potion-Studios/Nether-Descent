package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GrowingPlantVinesDecorator extends TreeDecorator {
    public static final Codec<GrowingPlantVinesDecorator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockState.CODEC.fieldOf("head").forGetter(GrowingPlantVinesDecorator::head),
                    BlockState.CODEC.fieldOf("body").forGetter(GrowingPlantVinesDecorator::body),
                    BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("attachable").forGetter(decorator -> decorator.attachable)
            ).apply(instance, GrowingPlantVinesDecorator::new)
    );

    private final BlockState head;
    private final BlockState body;
    private final Optional<Block> attachable;

    public GrowingPlantVinesDecorator(BlockState head, BlockState body) {
        this(head, body, Optional.empty());
    }

    public GrowingPlantVinesDecorator(Block head, Block body) {
        this(head.defaultBlockState(), body.defaultBlockState());
    }

    public GrowingPlantVinesDecorator(Block head, Block body, Block attachable) {
        this(head.defaultBlockState(), body.defaultBlockState(), Optional.of(attachable));
    }

    public GrowingPlantVinesDecorator(BlockState head, BlockState body, Optional<Block> attachable) {
        this.head = head;
        this.body = body;
        this.attachable = attachable;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return NetherDescentTreeDecoratorType.GROWING_PLANT.get();
    }

    @Override
    public void place(@NotNull Context context) {
        RandomSource randomSource = context.random();
        LevelReader levelReader = (LevelReader) context.level();
        ObjectArrayList<BlockPos> list = new ObjectArrayList<>();
        list.addAll(context.leaves());
        list.addAll(context.logs());
        list.addAll(context.roots());
        for (BlockPos blockPos: list) {
            if (attachable.isEmpty() || levelReader.getBlockState(blockPos).is(attachable.get())) {
                BlockPos blockPos1 = blockPos.below();
                if (head().canSurvive(levelReader, blockPos1) && randomSource.nextInt(3) > 0) {
                    if (levelReader.isEmptyBlock(blockPos1)) {
                        context.setBlock(blockPos1, head());
                        while (levelReader.isEmptyBlock(blockPos1.below()) && body().canSurvive(levelReader, blockPos1.below()) && randomSource.nextInt(4) > 0) {
                            blockPos1 = blockPos1.below();
                            context.setBlock(blockPos1, head());
                            context.setBlock(blockPos1.above(), body());
                        }
                    }
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
