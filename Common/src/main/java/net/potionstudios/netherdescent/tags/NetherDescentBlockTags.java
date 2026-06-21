package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentBlockTags {

    public static final TagKey<Block> SYTHIAN_STALK_PLANTABLE_ON = create("sythian_stalk_plantable_on");
	public static final TagKey<Block> NETHER_MOSS_REPLACEABLE = create("nether_moss_replaceable");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, NetherDescent.id(name));
    }
}
