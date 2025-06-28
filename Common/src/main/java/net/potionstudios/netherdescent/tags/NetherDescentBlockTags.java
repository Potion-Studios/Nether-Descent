package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentBlockTags {

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, NetherDescent.id(name));
    }
}
