package net.potionstudios.netherdescent.world.level.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.entity.sign.NetherDescentHangingSignBlockEntity;
import net.potionstudios.netherdescent.world.level.block.entity.sign.NetherDescentSignBlockEntity;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NetherDescentBlockEntityType {
    public static final Supplier<BlockEntityType<NetherDescentSignBlockEntity>> SIGNS = register("sign", () -> new BlockEntityType<>(
            NetherDescentSignBlockEntity::new,
            Stream.concat(
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::sign),
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::wallSign)
            ).collect(Collectors.toSet())));

    public static final Supplier<BlockEntityType<NetherDescentHangingSignBlockEntity>> HANGING_SIGNS = register("hanging_sign", () -> new BlockEntityType<>(
            NetherDescentHangingSignBlockEntity::new,
            Stream.concat(
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::hangingSign),
                    NetherDescentWoodSet.woodsets().stream().map(NetherDescentWoodSet::wallHangingSign)
            ).collect(Collectors.toSet())));

	public static final Supplier<BlockEntityType<WailingGillsBlockEntity>> WAILING_GILLS = register("wailing_gills", () -> new BlockEntityType<>(WailingGillsBlockEntity::new, Set.of(NetherDescentBlocks.WAILING_GILLS.get())));

	public static final Supplier<BlockEntityType<NetherDescentCampfireBlockEntity>> CAMPFIRE = register("campfire", () -> new BlockEntityType<>(NetherDescentCampfireBlockEntity::new, Set.of(NetherDescentBlocks.PENDORITE_CAMPFIRE.get())));

	public static final Supplier<BlockEntityType<HornetNestBlockEntity>> HORNET_NEST = register("hornet_nest", () -> new BlockEntityType<>(HornetNestBlockEntity::new, Set.of(NetherDescentBlocks.HORNET_NEST.get())));

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String key, Supplier<BlockEntityType<T>> blockEntity) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, blockEntity);
    }

    public static void blockEntities() {
        NetherDescent.LOGGER.info("Registering Nether Descent Block Entities");
    }
}
