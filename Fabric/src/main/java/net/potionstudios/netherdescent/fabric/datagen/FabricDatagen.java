package net.potionstudios.netherdescent.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.netherdescent.tags.NetherDescentBiomeTags;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.concurrent.CompletableFuture;

public class FabricDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		FabricTagProvider.BlockTagProvider blockProvider = pack.addProvider(FabricBlockTagGenerator::new);
		pack.addProvider((output, completableFuture) -> new FabricItemTagGenerator(output, completableFuture, blockProvider));
		pack.addProvider(FabricBiomeTagGenerator::new);
	}

	private static class FabricBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
		private FabricBlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			NetherDescentWoodSet.woodsets().forEach(set -> getOrCreateTagBuilder(ConventionalBlockTags.BOOKSHELVES).add(set.bookshelf()));
			getOrCreateTagBuilder(ConventionalBlockTags.ORES).add(NetherDescentBlocks.PENDORITE_ORE.get());
		}
	}

	private static class FabricItemTagGenerator extends FabricTagProvider.ItemTagProvider {
		private FabricItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, FabricTagProvider.BlockTagProvider blockTags) {
			super(output, registriesFuture, blockTags);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			getOrCreateTagBuilder(ConventionalItemTags.INGOTS).add(NetherDescentItems.PENDORITE_INGOT.get());
			getOrCreateTagBuilder(ConventionalItemTags.NUGGETS).add(NetherDescentItems.PENDORITE_NUGGET.get());
			copy(ConventionalBlockTags.ORES, ConventionalItemTags.ORES);
			copy(ConventionalBlockTags.BOOKSHELVES, ConventionalItemTags.BOOKSHELVES);
			NetherDescentItems.ITEMS.stream()
					.filter(item -> item.get().isEdible())
					.forEach(item -> getOrCreateTagBuilder(ConventionalItemTags.FOODS).add(item.get()));
		}
	}

	private static class FabricBiomeTagGenerator extends FabricTagProvider<Biome> {
		private FabricBiomeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, Registries.BIOME, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			getOrCreateTagBuilder(ConventionalBiomeTags.IN_NETHER).forceAddTag(NetherDescentBiomeTags.NETHER);
			getOrCreateTagBuilder(ConventionalBiomeTags.NETHER_FORESTS).forceAddTag(NetherDescentBiomeTags.FOREST);
		}
	}
}
