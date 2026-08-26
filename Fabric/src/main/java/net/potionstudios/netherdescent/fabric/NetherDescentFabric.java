package net.potionstudios.netherdescent.fabric;

import com.terraformersmc.biolith.impl.Biolith;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.SpawnPlacements;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.commands.NetherDescentCommands;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.levelgen.biome.BiolithRegister;
import terrablender.core.TerraBlender;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 */
public class NetherDescentFabric implements ModInitializer {

    private static boolean initialized = false;

    @Override
    public void onInitialize() {
        init();
    }

    protected static void init() {
        if (initialized) return;
        initialized = true;
        NetherDescent.init();
        VanillaCompatFabric.init();
	    NetherDescentEntityType.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
	    NetherDescentEntityType.registerSpawnPlacements((consumer) -> SpawnPlacements.register(consumer.entityType().get(), consumer.spawnPlacementType(), consumer.heightmapType(), consumer.predicate()));
        NetherDescent.commonSetup();
        if (FabricLoader.getInstance().isModLoaded(Biolith.MOD_ID))
            BiolithRegister.register();
        else if (!FabricLoader.getInstance().isModLoaded(TerraBlender.MOD_ID))
            NetherDescent.LOGGER.warn("TerraBlender or Biolith are not loaded, Nether Descent's biomes will not be added to the world!");
        NetherDescent.postInit();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> NetherDescentCommands.register(dispatcher::register));
    }
}
