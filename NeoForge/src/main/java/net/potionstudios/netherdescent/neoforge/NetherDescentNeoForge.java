package net.potionstudios.netherdescent.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.commands.NetherDescentCommands;
import net.potionstudios.netherdescent.config.configs.WorldGenerationConfig;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import net.potionstudios.netherdescent.world.level.levelgen.regions.NetherDescentRegion;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

/**
 * Main class for the mod on the NeoForge platform.
 */
@Mod(NetherDescent.MOD_ID)
public class NetherDescentNeoForge {
    public NetherDescentNeoForge(final IEventBus eventBus) {
        IEventBus EVENT_BUS = NeoForge.EVENT_BUS;
        NetherDescent.init();
        NeoForgePlatformHandler.register(eventBus);
        eventBus.addListener(this::onInitialize);
        eventBus.addListener((FMLLoadCompleteEvent event) -> event.enqueueWork(NetherDescent::postInit));
        eventBus.addListener((EntityAttributeCreationEvent event) -> NetherDescentEntityType.registerEntityAttributes(event::put));
        eventBus.addListener((RegisterSpawnPlacementsEvent event) -> NetherDescentEntityType.registerSpawnPlacements((consumer) -> event.register(consumer.entityType().get(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate(), RegisterSpawnPlacementsEvent.Operation.OR)));
        EVENT_BUS.addListener((RegisterCommandsEvent event) -> NetherDescentCommands.register(event.getDispatcher()::register));
        VanillaCompatNeoForge.registerVanillaCompatEvents(EVENT_BUS);
    }

    /**
     * Should initialize everything where a specific event does not cover it.
     * @see FMLCommonSetupEvent
     */
    private void onInitialize(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetherDescent.commonSetup();
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.NETHER, NetherDescent.MOD_ID, NetherDescentSurfaceRules.makeRules());
            Regions.register(new NetherDescentRegion(
                    NetherDescent.id("nether_descent"),
                    WorldGenerationConfig.get().regionWeight
            ));
            NeoForgePlatformHandler.registerPottedPlants();
        });
    }
}
