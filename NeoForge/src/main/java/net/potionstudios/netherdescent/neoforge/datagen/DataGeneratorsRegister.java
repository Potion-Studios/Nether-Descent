package net.potionstudios.netherdescent.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.potionstudios.netherdescent.neoforge.datagen.generators.*;
import net.potionstudios.netherdescent.neoforge.datagen.generators.loot.LootGenerator;
import net.potionstudios.netherdescent.world.damagesource.NetherDescentDamageTypes;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.ConfiguredFeaturesUtil;
import net.potionstudios.netherdescent.world.level.levelgen.feature.placed.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.world.level.levelgen.structure.NetherDescentStructureSets;
import net.potionstudios.netherdescent.world.level.levelgen.structure.NetherDescentStructures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NetherDescent.MOD_ID)
class DataGeneratorsRegister {

    @SubscribeEvent
    private static void onGatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, BUILDER, Set.of(NetherDescent.MOD_ID));
        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);
        lookupProvider = datapackBuiltinEntriesProvider.getRegistryProvider();

        ModelGenerators.init(generator, event.includeClient(), output, existingFileHelper);
        generator.addProvider(event.includeClient(), new LangGenerator(output, "en_us"));
        generator.addProvider(event.includeServer(), new RecipeGenerator(output, lookupProvider));
        generator.addProvider(event.includeServer(), new LootGenerator(output, lookupProvider));
        TagsGenerator.init(generator, event.includeServer(), output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), new DataMapGenerator(output, lookupProvider));
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, configuredFeatureHolderGetter -> ConfiguredFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> configuredFeatureHolderGetter.register(biomeResourceKey, biomeFactory.generate(configuredFeatureHolderGetter)))))
            .add(Registries.PLACED_FEATURE, pContext -> PlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.BIOME, biomeBootstapContext -> NetherDescentBiomes.BIOME_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> biomeBootstapContext.register(biomeResourceKey, biomeFactory.generate(biomeBootstapContext.lookup(Registries.PLACED_FEATURE), biomeBootstapContext.lookup(Registries.CONFIGURED_CARVER))))))
		    .add(Registries.STRUCTURE, context -> NetherDescentStructures.STRUCTURE_FACTORIES.forEach((structureResourceKey, structureFactory) -> context.register(structureResourceKey, structureFactory.generate(context))))
		    .add(Registries.STRUCTURE_SET, context -> NetherDescentStructureSets.STRUCTURE_SET_FACTORIES.forEach((structureSetResourceKey, structureSetFactory) -> context.register(structureSetResourceKey, structureSetFactory.generate(context.lookup(Registries.STRUCTURE)))))
            .add(Registries.DAMAGE_TYPE, pContext -> NetherDescentDamageTypes.DAMAGE_TYPE_FACTORIES.forEach(((damageTypeResourceKey, damageTypeFactory) -> pContext.register(damageTypeResourceKey, damageTypeFactory.generate(pContext)))));
}
