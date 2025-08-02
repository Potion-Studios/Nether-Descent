package net.potionstudios.netherdescent.world.item.brewing;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.apache.logging.log4j.util.TriConsumer;

public class NetherDescentBrewingRecipes {

    public static void buildBrewingRecipes(TriConsumer<Holder<Potion>, Item, Holder<Potion>> consumer) {
        consumer.accept(Potions.WATER, NetherDescentItems.EMBUR_LILY.get(), Potions.FIRE_RESISTANCE);
    }
}
