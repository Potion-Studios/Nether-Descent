package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentItemTags {

	private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, NetherDescent.id(name));
    }
}
