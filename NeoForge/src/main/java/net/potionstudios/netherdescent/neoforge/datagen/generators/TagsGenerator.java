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
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentBiomeTags;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
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
			NetherDescentWoodSet.woodsets().forEach(set -> {
				tag(BlockTags.PLANKS).add(set.planks());
				tag(BlockTags.WOODEN_SLABS).add(set.slab());
				tag(BlockTags.WOODEN_STAIRS).add(set.stairs());
				tag(BlockTags.WOODEN_BUTTONS).add(set.button());
				tag(BlockTags.WOODEN_PRESSURE_PLATES).add(set.pressurePlate());
				tag(BlockTags.WOODEN_TRAPDOORS).add(set.trapdoor());
				tag(BlockTags.WOODEN_DOORS).add(set.door());
				tag(BlockTags.WOODEN_FENCES).add(set.fence());
				tag(BlockTags.FENCE_GATES).add(set.fenceGate());
				tag(Tags.Blocks.FENCE_GATES_WOODEN).add(set.fenceGate());
				tag(BlockTags.STANDING_SIGNS).add(set.sign());
				tag(BlockTags.WALL_SIGNS).add(set.wallSign());
				tag(BlockTags.CEILING_HANGING_SIGNS).add(set.hangingSign());
				tag(BlockTags.WALL_HANGING_SIGNS).add(set.wallHangingSign());
				tag(Tags.Blocks.BOOKSHELVES).add(set.bookshelf());
				tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(set.bookshelf());
				tag(set.logBlockTag()).add(set.logstem(), set.wood(), set.strippedLogStem(), set.strippedWood());
				tag(BlockTags.LOGS).addOptionalTag(set.logBlockTag());
				tag(Tags.Blocks.STRIPPED_LOGS).add(set.strippedLogStem());
				tag(Tags.Blocks.STRIPPED_WOODS).add(set.strippedWood());
				tag(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES).add(set.craftingTable());
			});
			tag(BlockTags.NETHER_CARVER_REPLACEABLES).add(NetherDescentBlocks.BLUE_NETHERRACK.get(), NetherDescentBlocks.WAILING_NYLIUM.get(), NetherDescentBlocks.EMBUR_NYLIUM.get(), NetherDescentBlocks.SYTHIAN_NYLIUM.get());
			tag(BlockTags.CLIMBABLE).add(NetherDescentBlocks.WAILING_VINE.get());
			tag(Tags.Blocks.NETHERRACKS).add(NetherDescentBlocks.BLUE_NETHERRACK.get());
			tag(BlockTags.ENDERMAN_HOLDABLE).add(NetherDescentBlocks.EMBUR_NYLIUM.get(), NetherDescentBlocks.SYTHIAN_NYLIUM.get(), NetherDescentBlocks.WAILING_NYLIUM.get());
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
			else if (object instanceof NyliumBlock) tag(BlockTags.NYLIUM).add(object);
			SoundType type = object.defaultBlockState().getSoundType();
			if (type == SoundType.STONE || type == SoundType.DEEPSLATE || type == SoundType.NETHER_BRICKS || type == SoundType.NYLIUM)
				tag(BlockTags.MINEABLE_WITH_PICKAXE).add(object);
			else if (type == SoundType.WOOD || type == SoundType.SWEET_BERRY_BUSH)
				tag(BlockTags.MINEABLE_WITH_AXE).add(object);
			else if (type == SoundType.GRAVEL || type == SoundType.SAND || type == SoundType.SNOW || type == SoundType.GRASS)
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
			copy(BlockTags.PLANKS, ItemTags.PLANKS);
			copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
			copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
			copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
			copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
			copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
			copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
			copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
			copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
			copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
			copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
			copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
			copy(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
			copy(BlockTags.LOGS, ItemTags.LOGS);
			copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
			copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
			copy(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES, Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES);
			copy(Tags.Blocks.NETHERRACKS, Tags.Items.NETHERRACKS);
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
