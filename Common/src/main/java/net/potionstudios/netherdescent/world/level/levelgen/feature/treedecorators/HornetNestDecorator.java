package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.netherdescent.config.configs.MobSpawnConfig;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HornetNestDecorator extends TreeDecorator {
    public static final Codec<HornetNestDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(HornetNestDecorator::new, arg -> arg.probability).codec();
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
        if (MobSpawnConfig.INSTANCE.hornet.value()) {
            RandomSource randomSource = context.random();
            if (!(randomSource.nextFloat() >= this.probability)) {
                List<BlockPos> list = context.leaves();
                List<BlockPos> list2 = context.logs();
                int i = !list.isEmpty()
                        ? Math.max(list.get(0).getY() - 1, list2.get(0).getY() + 1)
                        : Math.min(list2.get(0).getY() + 1 + randomSource.nextInt(3), list2.get(list2.size() - 1).getY());
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
                            for (int j = 0; j < ix; j++) {
                                CompoundTag compoundTag = new CompoundTag();
                                compoundTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(NetherDescentEntityType.HORNET.get()).toString());
                                arg2.storeHornet(compoundTag, randomSource.nextInt(599));
                            }
                        });
                    }
                }
            }
        }
    }
}
