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
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.function.BiConsumer;

public class BlockItemFeatures {

    public static void registerCompostables(BiConsumer<ItemLike, Float> consumer) {
        compostItems(consumer, 0.3F, NetherDescentItems.CRIMSON_BERRIES.get(), NetherDescentBlocks.CRIMSON_CARPET.get(), NetherDescentBlocks.ARISIAN_LEAVES.get(), NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), NetherDescentBlocks.EMBUR_HANGING_MOSS.get(),
                NetherDescentBlocks.ARISIAN_MOSS_CARPET.get());
        compostItems(consumer, 0.5F, NetherDescentBlocks.EMBUR_SPROUTS.get(), NetherDescentBlocks.SYTHIAN_SPROUTS.get(), NetherDescentBlocks.ARISIAN_SPROUTS.get(), NetherDescentBlocks.TALL_ARISIAN_SPROUTS.get());
        compostItems(consumer, 0.6F, NetherDescentBlocks.EMBUR_GEL_VINES.get(), NetherDescentBlocks.WAILING_VINES.get(), NetherDescentBlocks.HANGING_SYTHIAN_ROOTS.get(), NetherDescentBlocks.ARISIAN_TANGLE_ROOTS.get());
        compostItems(consumer, 0.65F, NetherDescentBlocks.EMBUR_ROOTS.get(), NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(),
                NetherDescentBlocks.FUNGAL_BULBS.get(), NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), NetherDescentBlocks.SYTHIAN_ROOTS.get(), NetherDescentBlocks.ARISIAN_DANDELIONS.get(),
                NetherDescentBlocks.TALL_ARISIAN_DANDELIONS.get(), NetherDescentBlocks.ARISIAN_BLOSSOM.getItem(), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get(), NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get());
        compostItems(consumer, 0.85F, NetherDescentBlocks.SYTHIAN_WART_BLOCK.get(), NetherDescentBlocks.WAILING_WART_BLOCK.get());
        compostItems(consumer, 1F, NetherDescentItems.CRIMSON_BERRY_PIE.get());
        NetherDescentWoodSet.woodsets().forEach(set -> compostItems(consumer, 0.65F, set.growerItem().getItem()));
    }

    private static void compostItems(BiConsumer<ItemLike, Float> consumer, float chance, ItemLike... items) {
        for (ItemLike item : items) consumer.accept(item, chance);
    }

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
