package net.potionstudios.netherdescent.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import terrablender.api.SurfaceRuleManager;

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
        MOD_BUS.addListener(this::onInitialize);
    }

    /**
     * Should initialize everything where a specific event does not cover it.
     * @see FMLCommonSetupEvent
     */
    private void onInitialize(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, NetherDescent.MOD_ID, NetherDescentSurfaceRules.makeRules());
        });
    }
}
