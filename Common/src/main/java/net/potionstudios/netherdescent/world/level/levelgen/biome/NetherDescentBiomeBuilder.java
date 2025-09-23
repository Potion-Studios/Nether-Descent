package net.potionstudios.netherdescent.world.level.levelgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.NetherDescentPlacedFeatures;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.NetherDescentTreePlacedFeatures;

public class NetherDescentBiomeBuilder {

    private static void addDefaultNetherGeneration(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        vanillaNetherFeatures(builder);
    }

    private static BiomeGenerationSettings.Builder setupDefaultNetherGeneration(HolderGetter<PlacedFeature> placedFeatureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatureGetter, carverGetter);
        addDefaultNetherGeneration(generationSettings);
        return generationSettings;
    }

    protected static Biome crimsonGardens(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);
        addVegetal(generationSettings, NetherPlacements.WEEPING_VINES);
        addVegetal(generationSettings, TreePlacements.CRIMSON_FUNGI);
        addVegetal(generationSettings, NetherPlacements.CRIMSON_FOREST_VEGETATION);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.HOGLIN, 9, 3, 4);
        addSpawn(spawnSettings, EntityType.ZOMBIFIED_PIGLIN, 80, 4, 4);
        addSpawn(spawnSettings, EntityType.MAGMA_CUBE, 100, 2, 5);
        addSpawn(spawnSettings, EntityType.PIGLIN, 15, 4, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3343107).skyColor(3343107).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.01428F)).ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111D)).backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome emburBog(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);
        generationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
        BiomeDefaultFeatures.addAncientDebris(generationSettings);

        addVegetal(generationSettings, NetherDescentPlacedFeatures.EMBUR_LILY);
        addVegetal(generationSettings, NetherDescentPlacedFeatures.EMBUR_SPROUT);
        addVegetal(generationSettings, NetherDescentPlacedFeatures.EMBUR_BOG_VEGETATION);
        addVegetal(generationSettings, NetherDescentTreePlacedFeatures.EMBUR_WARTS);
        addVegetal(generationSettings, NetherDescentPlacedFeatures.EMBUR_CAVE_MOSS);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.BLAZE, 20, 2, 4);
        addSpawn(spawnSettings, EntityType.ZOMBIFIED_PIGLIN, 80, 4, 4);
        addSpawn(spawnSettings, EntityType.MAGMA_CUBE, 100, 2, 5);
        addSpawn(spawnSettings, EntityType.PIGLIN, 15, 4, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(15110510).skyColor(15110510).ambientParticle(new AmbientParticleSettings(ParticleTypes.FLAME, 0.00228F)).ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D)).backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS)).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome sythianTorrids(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);

        addVegetal(generationSettings, NetherDescentPlacedFeatures.SYTHIAN_SPROUT);
        addVegetal(generationSettings, NetherDescentTreePlacedFeatures.SYTHIAN_FUNGI_TREES);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.GHAST, 50, 4, 4);
        addSpawn(spawnSettings, EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4);
        addSpawn(spawnSettings, EntityType.MAGMA_CUBE, 2, 4, 4);
        addSpawn(spawnSettings, EntityType.ENDERMAN, 1, 4, 4);
        addSpawn(spawnSettings, EntityType.PIGLIN, 15, 4, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(16572546).skyColor(16572546).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.01428F)).ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111D)).backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome wailingGarth(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);

        addVegetal(generationSettings, NetherPlacements.NETHER_SPROUTS);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.GHAST, 50, 4, 4);
        addSpawn(spawnSettings, EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4);
        addSpawn(spawnSettings, EntityType.MAGMA_CUBE, 2, 4, 4);
        addSpawn(spawnSettings, EntityType.ENDERMAN, 1, 4, 4);
        addSpawn(spawnSettings, EntityType.PIGLIN, 15, 4, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
        addSpawn(spawnSettings, EntityType.HOGLIN, 60, 1, 2);

        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(4529794).skyColor(4529794).ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F)).ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D)).backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY)).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    private static void addSpawn(MobSpawnSettings.Builder builder, EntityType<?> entityType, int weight, int minGroupSize, int maxGroupSize) {
        builder.addSpawn(entityType.getCategory(), new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize));
    }

    private static void vanillaNetherFeatures(BiomeGenerationSettings.Builder generationSettings) {
        addUnderGroundDecoration(generationSettings, NetherPlacements.SPRING_OPEN);
        addUnderGroundDecoration(generationSettings, NetherPlacements.PATCH_FIRE);
        addUnderGroundDecoration(generationSettings, NetherPlacements.PATCH_SOUL_FIRE);
        addUnderGroundDecoration(generationSettings, NetherPlacements.GLOWSTONE_EXTRA);
        addUnderGroundDecoration(generationSettings, NetherPlacements.GLOWSTONE);
        addUnderGroundDecoration(generationSettings, VegetationPlacements.BROWN_MUSHROOM_NETHER);
        addUnderGroundDecoration(generationSettings, VegetationPlacements.RED_MUSHROOM_NETHER);
        addUnderGroundDecoration(generationSettings, OrePlacements.ORE_MAGMA);
        addUnderGroundDecoration(generationSettings, NetherPlacements.SPRING_CLOSED);
    }

    private static void addUnderGroundDecoration(BiomeGenerationSettings.Builder builder, ResourceKey<PlacedFeature> feature) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, feature);
    }

    private static void addVegetal(BiomeGenerationSettings.Builder builder, ResourceKey<PlacedFeature> feature) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, feature);
    }
}
