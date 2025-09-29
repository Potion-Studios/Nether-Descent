package net.potionstudios.netherdescent.world;

import net.minecraft.world.level.ItemLike;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.function.BiConsumer;

public class BlockItemFeatures {

    public static void registerCompostables(BiConsumer<ItemLike, Float> consumer) {
        compostItems(consumer, 0.5F, NetherDescentBlocks.EMBUR_SPROUTS.get());
        compostItems(consumer, 0.5F, NetherDescentBlocks.SYTHIAN_SPROUTS.get());
        compostItems(consumer, 0.6F, NetherDescentBlocks.EMBUR_GEL_VINES.get());
        compostItems(consumer, 0.65F, NetherDescentBlocks.EMBUR_ROOTS.get(), NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), NetherDescentBlocks.TALL_CRIMSON_ROOTS.get());
        NetherDescentWoodSet.woodsets().forEach(set -> compostItems(consumer, 0.65F, set.growerItem().getItem()));
    }

    private static void compostItems(BiConsumer<ItemLike, Float> consumer, float chance, ItemLike... items) {
        for (ItemLike item : items) consumer.accept(item, chance);
    }

}
