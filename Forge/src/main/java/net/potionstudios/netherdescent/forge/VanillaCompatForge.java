package net.potionstudios.netherdescent.forge;

import net.minecraft.world.level.block.ComposterBlock;
import net.potionstudios.netherdescent.world.BlockItemFeatures;

public class VanillaCompatForge {

    public static void init() {
        BlockItemFeatures.registerCompostables((item, chance) -> ComposterBlock.COMPOSTABLES.put(item.asItem(), chance.floatValue()));
    }
}
