package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentItemTags {

	public static final TagKey<Item> INGOTS_PENDORITE = createCommon("ingots/pendorite");
    public static final TagKey<Item> NUGGETS_PENDORITE = createCommon("nuggets/pendorite");
	public static final TagKey<Item> STORAGE_BLOCKS_PENDORITE = createCommon("storage_blocks/pendorite");
	public static final TagKey<Item> STORAGE_BLOCKS_RAW_PENDORITE = createCommon("storage_blocks/raw_pendorite");
	public static final TagKey<Item> REPAIRS_PENDORITE_ARMOR = create("repairs_pendorite_armor");

	private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, NetherDescent.id(name));
    }

	private static TagKey<Item> createCommon(String name) {
		return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name));
	}
}
