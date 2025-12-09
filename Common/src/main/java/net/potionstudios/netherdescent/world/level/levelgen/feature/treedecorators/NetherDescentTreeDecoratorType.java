package net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentTreeDecoratorType<P extends TreeDecorator> {

    public static final Supplier<TreeDecoratorType<GrowingPlantVinesDecorator>> GROWING_PLANT = register("growing_plant", () -> GrowingPlantVinesDecorator.CODEC);
    public static final Supplier<TreeDecoratorType<HornetNestDecorator>> HORNET_NEST = register("hornet_nest", () -> HornetNestDecorator.CODEC);

    private static <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> register(String id, Supplier<MapCodec<P>> codec) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.TREE_DECORATOR_TYPE, id, () -> new TreeDecoratorType<>(codec.get()));
    }

    public static void treeDecoratorType() {
        NetherDescent.LOGGER.info("Registering Nether Descent Tree Decorator Types");
    }
}
