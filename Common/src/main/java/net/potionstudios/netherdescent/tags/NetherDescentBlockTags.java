package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentBlockTags {

	public static final TagKey<Block> STORAGE_BLOCKS_PENDORITE = createCommon("storage_blocks/pendorite");
	public static final TagKey<Block> STORAGE_BLOCKS_RAW_PENDORITE = createCommon("storage_blocks/raw_pendorite");
    public static final TagKey<Block> SYTHIAN_STALK_PLANTABLE_ON = create("sythian_stalk_plantable_on");
	public static final TagKey<Block> NETHER_MOSS_REPLACEABLE = create("nether_moss_replaceable");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, NetherDescent.id(name));
    }

	private static TagKey<Block> createCommon(String name) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
	}
}
