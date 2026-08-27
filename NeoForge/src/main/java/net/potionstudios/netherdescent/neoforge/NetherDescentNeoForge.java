package net.potionstudios.netherdescent.neoforge;

import com.terraformersmc.biolith.impl.Biolith;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.commands.NetherDescentCommands;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.levelgen.biome.BiolithRegister;
import net.potionstudios.netherdescent.world.level.levelgen.biome.TerraBlenderRegister;
import terrablender.core.TerraBlender;

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
            if (ModList.get().isLoaded(Biolith.MOD_ID))
                BiolithRegister.register();
            else if (ModList.get().isLoaded(TerraBlender.MOD_ID))
                TerraBlenderRegister.register();
            else
                NetherDescent.LOGGER.warn("TerraBlender or Biolith are not loaded, Nether Descent's biomes will not be added to the world!");
            NeoForgePlatformHandler.registerPottedPlants();
        });
    }
}
