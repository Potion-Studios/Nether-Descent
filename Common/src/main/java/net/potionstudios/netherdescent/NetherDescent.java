package net.potionstudios.netherdescent;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class NetherDescent {

    /** The mod id for  examplemod. */
    public static final String MOD_ID = "netherdescent";

    /** The logger for examplemod. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Initializes the mod.
     */
    public static void init() {

    }

    /**
     * Creates a new resource location for Oh The Biomes We've Gone.
     * @param name the name of the resource
     * @return the new resource location with the Biomes We've Gone location
     */
    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    /**
     * Creates a new resource key for Oh The Biomes We've Gone.
     * @param registryKey the registry key for the resource
     * @param name the name of the resource
     * @return the new resource key with the Biomes We've Gone location
     */
    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, id(name));
    }
}
