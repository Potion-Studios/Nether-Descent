package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentItemTags {

    public static final TagKey<Item> INGOTS_PENDORITE = create("ingots/pendorite");
    public static final TagKey<Item> NUGGETS_PENDORITE = create("nuggets/pendorite");
    public static final TagKey<Item> RAW_MATERIALS_PENDORITE = create("raw_materials/pendorite");
    public static final TagKey<Item> STORAGE_BLOCKS_PENDORITE = create("storage_blocks/pendorite");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_PENDORITE = create("storage_blocks/raw_pendorite");

	private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, NetherDescent.id(name));
    }
}
