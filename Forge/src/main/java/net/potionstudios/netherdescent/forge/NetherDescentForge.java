package net.potionstudios.netherdescent.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.potionstudios.netherdescent.NetherDescent;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(NetherDescent.MOD_ID)
public class NetherDescentForge {
    public NetherDescentForge(final FMLJavaModLoadingContext context) {
        IEventBus MOD_BUS = context.getModEventBus();
        IEventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
        NetherDescent.init();
        ForgePlatformHandler.register(MOD_BUS);
    }
}
