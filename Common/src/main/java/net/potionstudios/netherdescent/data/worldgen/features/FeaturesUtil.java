package net.potionstudios.netherdescent.data.worldgen.features;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.Map;

public class FeaturesUtil {

    public static final Map<ResourceKey<Feature>, FeatureFactory> FEATURES_FACTORIES = new Reference2ObjectOpenHashMap<>();

    protected static ResourceKey<Feature> register(String id, FeatureFactory feature) {
        ResourceKey<Feature> configuredFeatureResourceKey = registerKey(id);
        FEATURES_FACTORIES.put(configuredFeatureResourceKey, feature);
        return configuredFeatureResourceKey;
    }

    private static ResourceKey<Feature> registerKey(String name) {
        return NetherDescent.key(Registries.FEATURE, name);
    }

    @FunctionalInterface
    public interface FeatureFactory {
        Feature generate(BootstrapContext<Feature> featureBootstrapContext);
    }
}
