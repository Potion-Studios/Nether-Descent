package net.potionstudios.netherdescent.world.level.levelgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

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

    protected static Biome wailingGarth(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);

        addVegetal(generationSettings, NetherPlacements.NETHER_SPROUTS);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 2, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 15, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));
        spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 60, 1, 2));

        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(4529794).skyColor(4529794).ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F)).ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D)).backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY)).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
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
