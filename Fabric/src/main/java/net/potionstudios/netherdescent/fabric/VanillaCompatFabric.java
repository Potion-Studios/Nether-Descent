package net.potionstudios.netherdescent.fabric;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.potionstudios.netherdescent.world.BlockItemFeatures;

public class VanillaCompatFabric {

    public  static void init() {
        BlockItemFeatures.registerCompostables(CompostingChanceRegistry.INSTANCE::add);
    }

}
