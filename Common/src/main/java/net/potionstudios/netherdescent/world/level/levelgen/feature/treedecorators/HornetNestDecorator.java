package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HornetNestDecorator extends TreeDecorator {
    public static final MapCodec<HornetNestDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(HornetNestDecorator::new, arg -> arg.probability);
    private final float probability;

    public HornetNestDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return NetherDescentTreeDecoratorType.HORNET_NEST.get();
    }

    @Override
    public void place(@NotNull Context context) {
        RandomSource randomSource = context.random();
        if (!(randomSource.nextFloat() >= this.probability)) {
            List<BlockPos> list = context.leaves();
            List<BlockPos> list2 = context.logs();
            int i = !list.isEmpty()
                    ? Math.max(list.getFirst().getY() - 1, list2.getFirst().getY() + 1)
                    : Math.min(list2.getFirst().getY() + 1 + randomSource.nextInt(3), list2.getLast().getY());
            List<BlockPos> list3 = list2.stream()
                    .filter(pos -> pos.getY() == i)
                    .map(BlockPos::below)
                    .collect(Collectors.toList());

            if (!list3.isEmpty()) {
                Collections.shuffle(list3);
                Optional<BlockPos> optional = list3.stream()
                        .filter(pos -> context.isAir(pos) && context.isAir(pos.below()))
                        .findFirst();

                if (optional.isPresent()) {
                    context.setBlock(optional.get(), NetherDescentBlocks.HORNET_NEST.get().defaultBlockState());
                    context.level().getBlockEntity(optional.get(), NetherDescentBlockEntityType.HORNET_NEST.get()).ifPresent(arg2 -> {
                        int ix = 2 + randomSource.nextInt(2);
                        for (int j = 0; j < ix; j++)
                            arg2.storeHornet(HornetNestBlockEntity.Occupant.create(randomSource.nextInt(599)));
                    });
                }
            }
        }
    }
}
