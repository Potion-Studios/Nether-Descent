package net.potionstudios.netherdescent.data.worldgen.placement;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.*;
import java.util.function.Supplier;

public class PlacedFeaturesUtil {
    public static final Map<ResourceKey<PlacedFeature>, PlacedFeatureFactory> PLACED_FEATURE_FACTORIES = new Reference2ObjectOpenHashMap<>();

    private static final NoiseThresholdCountPlacement CLEARING_NOISE = NoiseThresholdCountPlacement.of(0.545, 1, 0);

    protected static Supplier<List<PlacementModifier>> clearingTreePlacement(PlacementModifier placementModifier) {
        List<PlacementModifier> placementModifiers = new ArrayList<>(VegetationPlacements.treePlacement(placementModifier));
        placementModifiers.add(CLEARING_NOISE);
        return () -> placementModifiers;
    }

    public static Holder<PlacedFeature> createPlacedFeatureDirect(Holder<Feature> feature, PlacementModifier... placementModifiers) {
        return createPlacedFeatureDirect(feature, List.of(placementModifiers));
    }

    protected static Holder<PlacedFeature> createPlacedFeatureDirect(Holder<Feature> feature, List<PlacementModifier> placementModifiers) {
        return Holder.direct(new PlacedFeature(feature, placementModifiers));
    }

    protected static ResourceKey<PlacedFeature> createPlacedFeature(String id, ResourceKey<Feature> feature, Supplier<List<PlacementModifier>> placementModifiers) {
        ResourceKey<PlacedFeature> placedFeatureKey = registerKey(id);
        PLACED_FEATURE_FACTORIES.put(placedFeatureKey, featureHolderGetter -> new PlacedFeature(featureHolderGetter.getOrThrow(feature), placementModifiers.get()));
        return placedFeatureKey;
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return NetherDescent.key(Registries.PLACED_FEATURE, name);
    }

    @FunctionalInterface
    public interface PlacedFeatureFactory {
        PlacedFeature generate(HolderGetter<Feature> configuredFeatureHolderGetter);
    }
}
