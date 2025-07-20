package net.potionstudios.netherdescent.world;

import net.minecraft.world.level.ItemLike;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.function.BiConsumer;

public class BlockItemFeatures {

    public static void registerCompostables(BiConsumer<ItemLike, Float> consumer) {
        compostItems(consumer, 0.5F, NetherDescentBlocks.EMBUR_SPROUTS.get());
        compostItems(consumer, 0.6F, NetherDescentBlocks.EMBUR_GEL_VINES.get());
        compostItems(consumer, 0.65F, NetherDescentBlocks.EMBUR_ROOTS.get(), NetherDescentBlocks.TALL_EMBUR_ROOTS.get());
    }

    private static void compostItems(BiConsumer<ItemLike, Float> consumer, float chance, ItemLike... items) {
        for (ItemLike item : items) consumer.accept(item, chance);
    }

}
