package net.potionstudios.netherdescent.forge;

import com.terraformersmc.biolith.impl.Biolith;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.commands.NetherDescentCommands;
import net.potionstudios.netherdescent.forge.client.NetherDescentClientForge;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.levelgen.biome.RegisterBiolith;
import net.potionstudios.netherdescent.world.level.levelgen.biome.RegisterTerraBlender;
import terrablender.core.TerraBlender;

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
        MOD_BUS.addListener((FMLLoadCompleteEvent event) -> event.enqueueWork(NetherDescent::postInit));
        MOD_BUS.addListener((EntityAttributeCreationEvent event) -> NetherDescentEntityType.registerEntityAttributes(event::put));
        MOD_BUS.addListener((SpawnPlacementRegisterEvent event) -> NetherDescentEntityType.registerSpawnPlacements((consumer) -> event.register(consumer.entityType().get(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate(), SpawnPlacementRegisterEvent.Operation.OR)));
        EVENT_BUS.addListener((RegisterCommandsEvent event) -> NetherDescentCommands.register(event.getDispatcher()::register));
        VanillaCompatForge.registerVanillaCompatEvents(EVENT_BUS);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NetherDescentClientForge.init(MOD_BUS));
    }

    /**
     * Should initialize everything where a specific event does not cover it.
     * @see FMLCommonSetupEvent
     */
    private void onInitialize(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetherDescent.commonSetup();
            VanillaCompatForge.init();
            if (ModList.get().isLoaded(Biolith.MOD_ID))
                RegisterBiolith.register();
            else if (ModList.get().isLoaded(TerraBlender.MOD_ID))
                RegisterTerraBlender.register();
            else NetherDescent.LOGGER.warn("TerraBlender or Biolith are not loaded, Nether Descent's biomes will not be added to the world!");
            ForgePlatformHandler.registerPottedPlants();
        });
    }
}
