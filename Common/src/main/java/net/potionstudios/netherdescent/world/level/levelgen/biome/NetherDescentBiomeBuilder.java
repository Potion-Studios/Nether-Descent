package net.potionstudios.netherdescent.world.level.levelgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.data.worldgen.placement.NetherDescentOrePlacements;
import net.potionstudios.netherdescent.sounds.NetherDescentSounds;
import net.potionstudios.netherdescent.data.worldgen.placement.NetherDescentPlacements;
import net.potionstudios.netherdescent.data.worldgen.placement.NetherDescentTreePlacements;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;

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

    protected static Biome arisianUndergrowth(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);

        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherDescentOrePlacements.ORE_PENDORITE);

        generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, NetherDescentPlacements.BASALT_LINE);

        addVegetal(generationSettings, NetherDescentTreePlacements.ARISIAN_ROOTS_HANGING);
        addVegetal(generationSettings, NetherDescentTreePlacements.ARISIAN_TREES_HANGING);
        addVegetal(generationSettings, NetherDescentTreePlacements.ARISIAN_TREES);
        addVegetal(generationSettings, NetherDescentPlacements.HANGING_ARISIAN_TANGLE_ROOTS);
        addVegetal(generationSettings, NetherDescentPlacements.ARISIAN_UNDERGROWTH_VEGETATION);
        addVegetal(generationSettings, NetherDescentPlacements.ARISIAN_UNDERGROWTH_HANGING_VEGETATION);
        addVegetal(generationSettings, NetherDescentPlacements.ARISIAN_MOSS_CARPET_PATCH);
        addVegetal(generationSettings, NetherDescentPlacements.HANGING_ARISIAN_MOSS_CARPET_PATCH);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.ENDERMAN, 20, 2, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);

        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(2765878).skyColor(2765878).ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F)).ambientLoopSound(NetherDescentSounds.AMBIENT_ARISIAN_UNDERGROWTH_LOOP.get()).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(NetherDescentSounds.AMBIENT_ARISIAN_UNDERGROWTH_ADDITIONS.get(), 0.0011D)).backgroundMusic(Musics.createGameMusic(NetherDescentSounds.MUSIC_BIOME_ARISIAN_UNDERGROWTH.get())).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome crimsonGardens(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);
        addVegetal(generationSettings, NetherPlacements.WEEPING_VINES);
        addVegetal(generationSettings, NetherPlacements.CRIMSON_FOREST_VEGETATION);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherDescentOrePlacements.ORE_PENDORITE);

        addVegetal(generationSettings, NetherDescentTreePlacements.BONE_TREES);
        addVegetal(generationSettings, NetherDescentTreePlacements.HANGING_BONE_TREES);
        addVegetal(generationSettings, NetherDescentTreePlacements.CRIMSON_FUNGI_TREES_HANGING);
        addVegetal(generationSettings, NetherDescentPlacements.CRIMSON_GARDEN_VEGETATION);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.SKELETON, 20, 5, 5);
		addSpawn(spawnSettings, EntityType.GHAST, 50, 4, 4);
		addSpawn(spawnSettings, EntityType.ENDERMAN, 1, 4, 4);
		addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
		spawnSettings.addMobCharge(EntityType.SKELETON, 0.7, 0.15)
				.addMobCharge(EntityType.GHAST, 0.7, 0.15)
				.addMobCharge(EntityType.ENDERMAN, 0.7, 0.15)
				.addMobCharge(EntityType.STRIDER, 0.7, 0.15);
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3343107).skyColor(3343107).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.01428F)).ambientLoopSound(NetherDescentSounds.AMBIENT_CRIMSON_GARDENS_LOOP.get()).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(NetherDescentSounds.AMBIENT_CRIMSON_GARDENS_ADDITIONS.get(), 0.0011D)).backgroundMusic(Musics.createGameMusic(NetherDescentSounds.MUSIC_BIOME_CRIMSON_GARDENS.get())).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome emburBog(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);
        generationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
        BiomeDefaultFeatures.addAncientDebris(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherDescentOrePlacements.ORE_BLUE_GOLD_NETHER);
        generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherDescentOrePlacements.ORE_BLUE_QUARTZ_NETHER);

		addVegetal(generationSettings, NetherDescentPlacements.EMBUR_MOSS_CARPET_PATCH);
        addVegetal(generationSettings, NetherDescentPlacements.EMBUR_LILY);
        addVegetal(generationSettings, NetherDescentPlacements.EMBUR_SPROUT);
        addVegetal(generationSettings, NetherDescentPlacements.EMBUR_BOG_VEGETATION);
        addVegetal(generationSettings, NetherDescentTreePlacements.EMBUR_WARTS);
        addVegetal(generationSettings, NetherDescentPlacements.EMBUR_CAVE_MOSS);
		addVegetal(generationSettings, NetherDescentPlacements.HANGING_EMBUR_MOSS);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.BLAZE, 10, 2, 4);
        addSpawn(spawnSettings, EntityType.ZOMBIFIED_PIGLIN, 80, 4, 4);
        addSpawn(spawnSettings, EntityType.MAGMA_CUBE, 100, 2, 5);
        addSpawn(spawnSettings, EntityType.PIGLIN, 15, 4, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(15110510).skyColor(15110510).ambientParticle(new AmbientParticleSettings(ParticleTypes.FLAME, 0.00228F)).ambientLoopSound(NetherDescentSounds.AMBIENT_EMBUR_BOG_LOOP.get()).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(NetherDescentSounds.AMBIENT_EMBUR_BOG_ADDITIONS.get(), 0.0011D)).backgroundMusic(Musics.createGameMusic(NetherDescentSounds.MUSIC_BIOME_EMBUR_BOG.get())).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome sythianTorrids(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);

        addVegetal(generationSettings, NetherDescentPlacements.SYTHIAN_TORRIDS_VEGETATION);
        addVegetal(generationSettings, NetherDescentTreePlacements.SYTHIAN_FUNGI_TREES);
		addVegetal(generationSettings, NetherDescentPlacements.HANGING_SYTHIAN_ROOTS);

		addVegetal(generationSettings, NetherDescentPlacements.SYTHIAN_STALKS);
		addVegetal(generationSettings, NetherDescentPlacements.SYTHIAN_STALKS_DOWNWARD);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        addSpawn(spawnSettings, EntityType.GHAST, 50, 4, 4);
        addSpawn(spawnSettings, EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4);
        addSpawn(spawnSettings, EntityType.MAGMA_CUBE, 2, 4, 4);
        addSpawn(spawnSettings, EntityType.ENDERMAN, 1, 4, 4);
        addSpawn(spawnSettings, EntityType.PIGLIN, 15, 4, 4);
        addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(16572546).skyColor(16572546).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.01428F)).ambientLoopSound(NetherDescentSounds.AMBIENT_SYTHIAN_TORRIDS_LOOP.get()).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(NetherDescentSounds.AMBIENT_SYTHIAN_TORRIDS_ADDITIONS.get(), 0.0011D)).backgroundMusic(Musics.createGameMusic(NetherDescentSounds.MUSIC_BIOME_SYTHIAN_TORRIDS.get())).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static Biome wailingGarth(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder generationSettings = setupDefaultNetherGeneration(placedFeatureHolderGetter, carverGetter);

        addVegetal(generationSettings, NetherPlacements.NETHER_SPROUTS);
        BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);

        addVegetal(generationSettings, NetherDescentPlacements.WAILING_GARTH_VEGETATION);
        addVegetal(generationSettings, NetherDescentTreePlacements.WAILING_FUNGI_TREES);
        addVegetal(generationSettings, NetherDescentTreePlacements.WAILING_CAGES);
        addVegetal(generationSettings, NetherDescentPlacements.WAILING_BULB_BLOSSOM);

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
	    addSpawn(spawnSettings, EntityType.SKELETON, 20, 5, 5);
	    addSpawn(spawnSettings, NetherDescentEntityType.SOUL_GHAST.get(), 50, 4, 4);
	    addSpawn(spawnSettings, EntityType.ENDERMAN, 1, 4, 4);
	    addSpawn(spawnSettings, EntityType.STRIDER, 60, 1, 2);
	    spawnSettings.addMobCharge(EntityType.SKELETON, 0.7, 0.15)
			    .addMobCharge(NetherDescentEntityType.SOUL_GHAST.get(), 0.7, 0.15)
			    .addMobCharge(EntityType.ENDERMAN, 0.7, 0.15)
			    .addMobCharge(EntityType.STRIDER, 0.7, 0.15);

        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(2).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(4529794).skyColor(4529794).ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F)).ambientLoopSound(NetherDescentSounds.AMBIENT_WAILING_GARTH_LOOP.get()).ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D)).ambientAdditionsSound(new AmbientAdditionsSettings(NetherDescentSounds.AMBIENT_WAILING_GARTH_ADDITIONS.get(), 0.0011D)).backgroundMusic(Musics.createGameMusic(NetherDescentSounds.MUSIC_BIOME_WAILING_GARTH.get())).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
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
