package net.potionstudios.netherdescent.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;

import java.util.function.BiConsumer;

public class BlockItemFeatures {
    public static void registerFurnaceFuels(BiConsumer<ItemLike, Integer> consumer) {
        consumer.accept(NetherDescentItems.SOUL_BLAZE_POWDER.get(), 1200);
    }

    public static void onPlaceBlock(LevelAccessor level, Entity entity, BlockState placedAgainst, BlockState placed, BlockPos pos) {
        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            if (placed.is(BlockTags.FLOWERS))
                if (!level.getEntitiesOfClass(Hornet.class, new AABB(pos).inflate(5.0)).isEmpty())
                    NetherDescentCriteriaTriggers.PLACE_FLOWER_NEAR_HORNET.get().trigger(serverPlayer);
        }
    }
}
