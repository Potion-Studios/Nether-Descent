package net.potionstudios.netherdescent.world.item.brewing;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.item.alchemy.NetherDescentPotions;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.apache.logging.log4j.util.TriConsumer;

public class NetherDescentBrewingRecipes {

    public static void buildBrewingRecipes(TriConsumer<Potion, Item, Potion> consumer) {
        consumer.accept(Potions.AWKWARD, NetherDescentItems.EMBUR_LILY.get(), Potions.FIRE_RESISTANCE);
		consumer.accept(Potions.AWKWARD, NetherDescentBlocks.WAILING_BULB_BLOSSOM.get().asItem(), NetherDescentPotions.LEVITATION.get());
		consumer.accept(NetherDescentPotions.LEVITATION.get(), Items.REDSTONE, NetherDescentPotions.STRONG_LEVITATION.get());
    }
}
