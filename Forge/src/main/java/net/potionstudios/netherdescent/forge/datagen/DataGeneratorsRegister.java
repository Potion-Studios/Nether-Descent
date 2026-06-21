package net.potionstudios.netherdescent.forge.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentProcessorLists;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructureSets;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentTemplatePools;
import net.potionstudios.netherdescent.data.worldgen.features.ConfiguredFeaturesUtil;
import net.potionstudios.netherdescent.data.worldgen.placement.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.forge.datagen.generators.*;
import net.potionstudios.netherdescent.forge.datagen.generators.loot.LootGenerator;
import net.potionstudios.netherdescent.world.damagesource.NetherDescentDamageTypes;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * This class is used to register the data generators for the mod.
 * @see GatherDataEvent
 * @author Joseph T. McQuigg
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = NetherDescent.MOD_ID)
class DataGeneratorsRegister {

    @SubscribeEvent
    protected static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, BUILDER, Set.of(NetherDescent.MOD_ID, ResourceLocation.DEFAULT_NAMESPACE));
        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);
        lookupProvider = datapackBuiltinEntriesProvider.getRegistryProvider();

        generator.addProvider(event.includeClient(), new LangGenerator(output, "en_us"));
        generator.addProvider(event.includeServer(), new LootGenerator(output));
        ModelGenerators.init(generator, event.includeClient(), output, existingFileHelper);
        TagsGenerator.init(generator, event.includeServer(), output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), new ForgeAdvancementProvider(output, lookupProvider, existingFileHelper, ImmutableList.of(new AdvancementGenerator())));
        generator.addProvider(event.includeClient(), new ParticleDescriptionGenerator(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new SoundDefinitionsGenerator(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new RecipeGenerator(output));
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, configuredFeatureHolderGetter -> ConfiguredFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> configuredFeatureHolderGetter.register(biomeResourceKey, biomeFactory.generate(configuredFeatureHolderGetter)))))
            .add(Registries.PLACED_FEATURE, pContext -> PlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.BIOME, biomeBootstapContext -> NetherDescentBiomes.BIOME_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> biomeBootstapContext.register(biomeResourceKey, biomeFactory.factory().generate(biomeBootstapContext.lookup(Registries.PLACED_FEATURE), biomeBootstapContext.lookup(Registries.CONFIGURED_CARVER))))))
            .add(Registries.TEMPLATE_POOL, context -> NetherDescentTemplatePools.TEMPLATE_POOL_FACTORIES.forEach((templatePoolResourceKey, templatePoolFactory) -> context.register(templatePoolResourceKey, templatePoolFactory.generate(context))))
            .add(Registries.STRUCTURE, context -> NetherDescentStructures.STRUCTURE_FACTORIES.forEach((structureResourceKey, structureFactory) -> context.register(structureResourceKey, structureFactory.generate(context))))
            .add(Registries.STRUCTURE_SET, context -> NetherDescentStructureSets.STRUCTURE_SET_FACTORIES.forEach((structureSetResourceKey, structureSetFactory) -> context.register(structureSetResourceKey, structureSetFactory.generate(context.lookup(Registries.STRUCTURE)))))
            .add(Registries.PROCESSOR_LIST, pContext -> NetherDescentProcessorLists.STRUCTURE_PROCESSOR_LIST_FACTORIES.forEach((structureProcessorListResourceKey, processorListFactory) -> pContext.register(structureProcessorListResourceKey, processorListFactory.generate(pContext.lookup(Registries.PROCESSOR_LIST)))))
            .add(Registries.DAMAGE_TYPE, pContext -> NetherDescentDamageTypes.DAMAGE_TYPE_FACTORIES.forEach(((damageTypeResourceKey, damageTypeFactory) -> pContext.register(damageTypeResourceKey, damageTypeFactory.generate(pContext)))));
}