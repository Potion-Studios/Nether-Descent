package net.potionstudios.netherdescent.forge;

import com.terraformersmc.biolith.impl.Biolith;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.commands.NetherDescentCommands;
import net.potionstudios.netherdescent.forge.client.NetherDescentClientForge;
import net.potionstudios.netherdescent.forge.loot.LootModifiersRegister;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.levelgen.biome.BiolithRegister;
import net.potionstudios.netherdescent.world.level.levelgen.biome.TerraBlenderRegister;
import terrablender.core.TerraBlender;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(NetherDescent.MOD_ID)
public class NetherDescentForge {
    public NetherDescentForge(final FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();
        NetherDescent.init();
        ForgePlatformHandler.register(modBusGroup);
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::onInitialize);
        FMLLoadCompleteEvent.getBus(modBusGroup).addListener((FMLLoadCompleteEvent event) -> event.enqueueWork(NetherDescent::postInit));
        EntityAttributeCreationEvent.BUS.addListener((EntityAttributeCreationEvent event) -> NetherDescentEntityType.registerEntityAttributes(event::put));
        SpawnPlacementRegisterEvent.BUS.addListener((SpawnPlacementRegisterEvent event) -> NetherDescentEntityType.registerSpawnPlacements((consumer) -> event.register(consumer.entityType().get(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate(), SpawnPlacementRegisterEvent.Operation.OR)));
        RegisterCommandsEvent.BUS.addListener((RegisterCommandsEvent event) -> NetherDescentCommands.register(event.getDispatcher()::register));
        VanillaCompatForge.registerVanillaCompatEvents(modBusGroup);
        LootModifiersRegister.register(modBusGroup);
        if (FMLEnvironment.dist.isClient()) NetherDescentClientForge.init(modBusGroup);
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
                BiolithRegister.register();
            else if (ModList.get().isLoaded(TerraBlender.MOD_ID))
                TerraBlenderRegister.register();
            else NetherDescent.LOGGER.warn("TerraBlender or Biolith are not loaded, Nether Descent's biomes will not be added to the world!");
            ForgePlatformHandler.registerPottedPlants();
        });
    }
}
