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
import net.potionstudios.netherdescent.neoforge.datagen.generators.LangGenerator;
import net.potionstudios.netherdescent.neoforge.datagen.generators.ModelGenerators;
import net.potionstudios.netherdescent.neoforge.datagen.generators.TagsGenerator;
import net.potionstudios.netherdescent.neoforge.datagen.generators.loot.LootGenerator;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = NetherDescent.MOD_ID)
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
        generator.addProvider(event.includeServer(), new LootGenerator(output, lookupProvider));
        TagsGenerator.init(generator, event.includeServer(), output, lookupProvider, existingFileHelper);
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, biomeBootstapContext -> NetherDescentBiomes.BIOME_FACTORIES.forEach(((biomeResourceKey, biomeFactory) -> biomeBootstapContext.register(biomeResourceKey, biomeFactory.generate(biomeBootstapContext.lookup(Registries.PLACED_FEATURE), biomeBootstapContext.lookup(Registries.CONFIGURED_CARVER))))));

}
