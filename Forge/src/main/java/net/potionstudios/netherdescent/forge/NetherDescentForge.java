package net.potionstudios.netherdescent.forge;

import net.minecraftforge.fml.common.Mod;
import net.potionstudios.netherdescent.NetherDescent;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(NetherDescent.MOD_ID)
public class NetherDescentForge {
    public NetherDescentForge() {
        NetherDescent.init();
    }
}
