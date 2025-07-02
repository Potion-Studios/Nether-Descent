package net.potionstudios.netherdescent.world.level.block.entities;

import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.block.entities.sign.NetherDescentHangingSignBlockEntity;
import net.potionstudios.netherdescent.world.level.block.entities.sign.NetherDescentSignBlockEntity;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class NetherDescentBlockEntityType {
    public static final Supplier<BlockEntityType<NetherDescentSignBlockEntity>> SIGNS = register("sign", () -> BlockEntityType.Builder.of(
            NetherDescentSignBlockEntity::new,
            Stream.concat(
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::sign),
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::wallSign)
            ).toArray(SignBlock[]::new)));

    public static final Supplier<BlockEntityType<NetherDescentHangingSignBlockEntity>> HANGING_SIGNS = register("hanging_sign", () -> BlockEntityType.Builder.of(
            NetherDescentHangingSignBlockEntity::new,
            Stream.concat(
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::hangingSign),
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::wallHangingSign)
            ).toArray(SignBlock[]::new)));

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String key, Supplier<BlockEntityType.Builder<T>> builder) {
        return PlatformHandler.PLATFORM_HANDLER.registerBlockEntity(key, builder);
    }

    public static void blockEntities() {
        NetherDescent.LOGGER.info("Registering Nether Descent Block Entities");
    }
}
