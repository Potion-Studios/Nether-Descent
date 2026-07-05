package net.potionstudios.netherdescent.neoforge.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.worldgen.lithostitched.api.registry.LithostitchedRegistries;
import dev.worldgen.lithostitched.impl.worldgen.biomeinjector.AddPoints;
import dev.worldgen.lithostitched.impl.worldgen.biomeinjector.region.Region;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.potionstudios.netherdescent.compat.lithostitched.ConfigLoadPredicate;
import net.potionstudios.netherdescent.compat.lithostitched.LoadPredicateType;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentProcessorLists;
import net.potionstudios.netherdescent.neoforge.datagen.generators.*;
import net.potionstudios.netherdescent.neoforge.datagen.generators.loot.LootGenerator;
import net.potionstudios.netherdescent.world.damagesource.NetherDescentDamageTypes;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import net.potionstudios.netherdescent.data.worldgen.features.ConfiguredFeaturesUtil;
import net.potionstudios.netherdescent.data.worldgen.placement.PlacedFeaturesUtil;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructureSets;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentTemplatePools;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NetherDescent.MOD_ID)
class DataGeneratorsRegister {

    @SubscribeEvent
    protected static void onGatherData(final GatherDataEvent event) {
	    LoadPredicateType.loadPredicateType();
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, BUILDER, Set.of(NetherDescent.MOD_ID, Identifier.DEFAULT_NAMESPACE));
        generator.addProvider(true, datapackBuiltinEntriesProvider);
        lookupProvider = datapackBuiltinEntriesProvider.getRegistryProvider();

        generator.addProvider(true, new ModelGenerators(output));
        generator.addProvider(true, new LangGenerator(output, "en_us"));
        generator.addProvider(true, new RecipeGenerator(output, lookupProvider));
        generator.addProvider(true, new LootGenerator(output, lookupProvider));
        TagsGenerator.init(generator, true, output, lookupProvider);
        generator.addProvider(true, new DataMapGenerator(output, lookupProvider));
	    generator.addProvider(true, new SoundDefinitionsGenerator(output));
        generator.addProvider(true, new ParticleDescriptionGenerator(output));
        generator.addProvider(true, new AdvancementProvider(output, lookupProvider, ImmutableList.of(new AdvancementGenerator())));
        generator.addProvider(true, new LithostitchedSurfaceRuleGenerator(output));
	}

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, configuredFeatureHolderGetter -> ConfiguredFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> configuredFeatureHolderGetter.register(biomeResourceKey, biomeFactory.generate(configuredFeatureHolderGetter)))))
            .add(Registries.PLACED_FEATURE, pContext -> PlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.BIOME, biomeBootstapContext -> NetherDescentBiomes.BIOME_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> biomeBootstapContext.register(biomeResourceKey, biomeFactory.factory().generate(biomeBootstapContext.lookup(Registries.PLACED_FEATURE), biomeBootstapContext.lookup(Registries.CONFIGURED_CARVER))))))
		    .add(Registries.TEMPLATE_POOL, context -> NetherDescentTemplatePools.TEMPLATE_POOL_FACTORIES.forEach((templatePoolResourceKey, templatePoolFactory) -> context.register(templatePoolResourceKey, templatePoolFactory.generate(context))))
		    .add(Registries.STRUCTURE, context -> NetherDescentStructures.STRUCTURE_FACTORIES.forEach((structureResourceKey, structureFactory) -> context.register(structureResourceKey, structureFactory.generate(context))))
		    .add(Registries.STRUCTURE_SET, context -> NetherDescentStructureSets.STRUCTURE_SET_FACTORIES.forEach((structureSetResourceKey, structureSetFactory) -> context.register(structureSetResourceKey, structureSetFactory.generate(context.lookup(Registries.STRUCTURE)))))
		    .add(Registries.PROCESSOR_LIST, pContext -> NetherDescentProcessorLists.STRUCTURE_PROCESSOR_LIST_FACTORIES.forEach((structureProcessorListResourceKey, processorListFactory) -> pContext.register(structureProcessorListResourceKey, processorListFactory.generate(pContext.lookup(Registries.PROCESSOR_LIST)))))
		    .add(Registries.DAMAGE_TYPE, pContext -> NetherDescentDamageTypes.DAMAGE_TYPE_FACTORIES.forEach(((damageTypeResourceKey, damageTypeFactory) -> pContext.register(damageTypeResourceKey, damageTypeFactory.generate(pContext)))))
		    .add(LithostitchedRegistries.BIOME_INJECTOR, pContext -> {
			    HolderGetter<Biome> biomeLookup = pContext.lookup(Registries.BIOME);
			    List<Pair<Climate.ParameterPoint, Holder<Biome>>> points = NetherDescentBiomes.BIOME_FACTORIES.entrySet().stream()
					    .map(entry -> Pair.of(
							    entry.getValue().parameterPoint(),
							    (Holder<Biome>) biomeLookup.getOrThrow(entry.getKey())
					    ))
					    .toList();
			    points.forEach(pair -> pContext.register(
					    NetherDescent.key(LithostitchedRegistries.BIOME_INJECTOR, pair.getSecond().unwrapKey().get().location().getPath()),
					    new AddPoints(
							    Optional.of(new ConfigLoadPredicate(pair.getSecond().getKey())),
							    LevelStem.NETHER,
							    10,
							    new Climate.ParameterList<>(List.of(pair))
					    )
			    ));
		    })
		    .add(LithostitchedRegistries.REGION, pContext -> {
			    HolderGetter<Biome> biomeLookup = pContext.lookup(Registries.BIOME);
			    List<Holder<Biome>> enabledBiomeHolders = NetherDescentBiomes.BIOME_FACTORIES.keySet().stream()
					    .map(biomeLookup::getOrThrow)
					    .map(holder -> (Holder<Biome>) holder)
					    .toList();
			    pContext.register(
					    NetherDescent.key(LithostitchedRegistries.REGION, "nether_descent"),
					    Region.create(
							    LevelStem.NETHER,
								    10,
							    HolderSet.direct(enabledBiomeHolders)
					    )
			    );
		    });
}
