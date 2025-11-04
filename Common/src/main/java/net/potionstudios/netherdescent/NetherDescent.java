package net.potionstudios.netherdescent;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.potionstudios.netherdescent.config.BiomeConfig;
import net.potionstudios.netherdescent.sounds.NetherDescentSounds;
import net.potionstudios.netherdescent.world.item.NetherDescentCreativeTabs;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.entities.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentFeatures;
import net.potionstudios.netherdescent.data.worldgen.placement.NetherDescentPlacements;
import net.potionstudios.netherdescent.world.level.levelgen.feature.NetherDescentFeature;
import net.potionstudios.netherdescent.world.level.levelgen.feature.treedecorators.NetherDescentTreeDecoratorType;
import net.potionstudios.netherdescent.world.level.levelgen.structure.NetherDescentStructureType;
import net.potionstudios.netherdescent.world.level.levelgen.structure.NetherDescentTemplatePools;
import net.potionstudios.netherdescent.world.level.levelgen.structure.pieces.NetherDescentStructurePieceType;
import net.potionstudios.netherdescent.world.level.levelgen.surfacerules.NetherDescentRuleSources;
import org.slf4j.Logger;

public class NetherDescent {

    /** The mod id for Nether Descent*/
    public static final String MOD_ID = "netherdescent";

    /** The logger for Nether Descent*/
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Initializes the mod.
     */
    public static void init() {
        NetherDescentItems.items();
        NetherDescentBlocks.blocks();
        NetherDescentBlockEntityType.blockEntities();
        NetherDescentCreativeTabs.tabs();
	    NetherDescentSounds.sounds();
        NetherDescentRuleSources.ruleSources();
		NetherDescentFeature.features();
        NetherDescentFeatures.features();
        NetherDescentPlacements.placements();
	    NetherDescentStructurePieceType.structurePieceTypes();
	    NetherDescentStructureType.structureTypes();
        NetherDescentTreeDecoratorType.treeDecoratorType();
        NetherDescentTemplatePools.templatePools();
    }

    /**
     * Ran later in the initialization process to set up common things.
     */
    public static void commonSetup() {
        BiomeConfig.init();
    }

    /**
     * Creates a new resource location for Nether Descent.
     * @param name the name of the resource
     * @return the new resource location with the Biomes We've Gone location
     */
    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    /**
     * Creates a new resource key for Nether Descent.
     * @param registryKey the registry key for the resource
     * @param name the name of the resource
     * @return the new resource key with the Biomes We've Gone location
     */
    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, id(name));
    }
}
