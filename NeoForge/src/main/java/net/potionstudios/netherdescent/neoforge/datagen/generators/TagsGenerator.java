package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentBiomeTags;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagsGenerator {

	public static void init(DataGenerator generator, boolean run, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
		BlockTagGenerator BlockTags = generator.addProvider(run, new BlockTagGenerator(output, lookupProvider, helper));
		generator.addProvider(run, new ItemTagGenerator(output, lookupProvider, BlockTags, helper));
		generator.addProvider(run, new BiomeTagGenerator(output, lookupProvider, helper));
	}

	/**
	 * Used to generate tags for blocks.
	 * @see BlockTagsProvider
	 */
	private static class BlockTagGenerator extends BlockTagsProvider {

		private BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, NetherDescent.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider provider) {
			NetherDescentBlocks.BLOCKS.forEach(block -> easyBlockTags(block.get()));
			tag(BlockTags.NETHER_CARVER_REPLACEABLES).add(NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.WAILING_NYLIUM.get());
			tag(BlockTags.CLIMBABLE).add(NetherDescentBlocks.WAILING_VINE.get());
		}

		private void easyBlockTags(Block object) {
			if (object instanceof SlabBlock) tag(BlockTags.SLABS).add(object);
			else if (object instanceof StairBlock) tag(BlockTags.STAIRS).add(object);
			else if (object instanceof WallBlock) tag(BlockTags.WALLS).add(object);
			else if (object instanceof ColoredFallingBlock) tag(BlockTags.SAND).add(object);
				//else if (object instanceof FlowerBlock) tag(BlockTags.SMALL_FLOWERS).add(object);
			else if (object instanceof TallFlowerBlock) tag(BlockTags.TALL_FLOWERS).add(object);
			else if (object instanceof LeavesBlock) tag(BlockTags.LEAVES).add(object);
			else if (object instanceof CampfireBlock) tag(BlockTags.CAMPFIRES).add(object);
			else if (object instanceof FlowerPotBlock) tag(BlockTags.FLOWER_POTS).add(object);
			SoundType type = object.defaultBlockState().getSoundType();
			if (type == SoundType.STONE || type == SoundType.DEEPSLATE || type == SoundType.NETHER_BRICKS)
				tag(BlockTags.MINEABLE_WITH_PICKAXE).add(object);
			else if (type == SoundType.WOOD || type == SoundType.SWEET_BERRY_BUSH)
				tag(BlockTags.MINEABLE_WITH_AXE).add(object);
			else if (type == SoundType.GRAVEL || type == SoundType.SAND || type == SoundType.SNOW || type == SoundType.GRASS || type == SoundType.NYLIUM)
				if (object instanceof LeavesBlock) tag(BlockTags.MINEABLE_WITH_HOE).add(object);
				else tag(BlockTags.MINEABLE_WITH_SHOVEL).add(object);
		}
	}

	private static class ItemTagGenerator extends ItemTagsProvider {

		private ItemTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, BlockTagGenerator blockTagGenerator, @Nullable ExistingFileHelper existingFileHelper) {
			super(arg, completableFuture, blockTagGenerator.contentsGetter(), NetherDescent.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider provider) {
			copy(BlockTags.SLABS, ItemTags.SLABS);
			copy(BlockTags.STAIRS, ItemTags.STAIRS);
			copy(BlockTags.WALLS, ItemTags.WALLS);
		}
	}

	private static class BiomeTagGenerator extends BiomeTagsProvider {
		private BiomeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
			super(arg, completableFuture, NetherDescent.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider provider) {
			NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList().forEach(biome -> tag(NetherDescentBiomeTags.NETHER).add(biome));
			NetherDescentBiomes.BIOMES_BY_TAG.forEach((tag, biome) -> tag(tag).add(biome));

			tag(BiomeTags.IS_NETHER).addTag(NetherDescentBiomeTags.NETHER);
		}
	}
}
