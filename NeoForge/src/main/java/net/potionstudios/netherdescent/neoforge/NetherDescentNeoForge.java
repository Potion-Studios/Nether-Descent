package net.potionstudios.netherdescent.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.potionstudios.netherdescent.NetherDescent;

/**
 * Main class for the mod on the NeoForge platform.
 */
@Mod(NetherDescent.MOD_ID)
public class NetherDescentNeoForge {
    public NetherDescentNeoForge(IEventBus eventBus) {
        NetherDescent.init();
    }
}
