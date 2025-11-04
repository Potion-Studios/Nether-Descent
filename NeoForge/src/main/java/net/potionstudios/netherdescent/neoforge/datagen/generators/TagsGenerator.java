package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.*;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentBiomeTags;
import net.potionstudios.netherdescent.tags.NetherDescentBlockTags;
import net.potionstudios.netherdescent.tags.NetherDescentItemTags;
import net.potionstudios.netherdescent.tags.NetherDescentStructureTags;
import net.potionstudios.netherdescent.world.damagesource.NetherDescentDamageTypes;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagsGenerator {

	public static void init(DataGenerator generator, boolean run, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
		BlockTagGenerator BlockTags = generator.addProvider(run, new BlockTagGenerator(output, lookupProvider, helper));
		generator.addProvider(run, new ItemTagGenerator(output, lookupProvider, BlockTags, helper));
		generator.addProvider(run, new BiomeTagGenerator(output, lookupProvider, helper));
		generator.addProvider(run, new StructureTagGenerator(output, lookupProvider, helper));
        generator.addProvider(run, new DamageTypeTagGenerator(output, lookupProvider, helper));
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
            tag(BlockTags.FENCES).add(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get());
            tag(Tags.Blocks.FENCES_NETHER_BRICK).add(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get());
			tag(BlockTags.BASE_STONE_NETHER).add(NetherDescentBlocks.BLUE_NETHERRACK.get());
			tag(BlockTags.CLIMBABLE).add(NetherDescentBlocks.WAILING_VINE.get(), NetherDescentBlocks.EMBUR_GEL_VINES.get(), NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get(), NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), NetherDescentBlocks.EMBUR_HANGING_MOSS.get(),
                    NetherDescentBlocks.HANGING_SYTHIAN_ROOTS.get(), NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get());
			tag(Tags.Blocks.NETHERRACKS).add(NetherDescentBlocks.BLUE_NETHERRACK.get());
			tag(BlockTags.ENDERMAN_HOLDABLE).add(NetherDescentBlocks.EMBUR_NYLIUM.get(), NetherDescentBlocks.SYTHIAN_NYLIUM.get(), NetherDescentBlocks.WAILING_NYLIUM.get());
			tag(BlockTags.SWORD_EFFICIENT).add(NetherDescentBlocks.EMBUR_SPROUTS.get(), NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), NetherDescentBlocks.SYTHIAN_SPROUTS.get());
			tag(BlockTags.REPLACEABLE_BY_TREES).add(NetherDescentBlocks.EMBUR_SPROUTS.get(), NetherDescentBlocks.SYTHIAN_SPROUTS.get());
			tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(NetherDescentBlocks.EMBUR_SPROUTS.get(), NetherDescentBlocks.SYTHIAN_SPROUTS.get());
			tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(NetherDescentBlocks.EMBUR_CAVE_MOSS.get());
            tag(BlockTags.NYLIUM).add(NetherDescentBlocks.SYTHIAN_SOIL.get(), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get());
            tag(BlockTags.GOLD_ORES).add(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get());
            tag(Tags.Blocks.ORES_QUARTZ).add(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get());
            tag(Tags.Blocks.ORE_RATES_SPARSE).add(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get());
            tag(Tags.Blocks.ORE_RATES_SINGULAR).add(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get());

			tag(NetherDescentBlockTags.STORAGE_BLOCKS_PENDORITE).add(NetherDescentBlocks.PENDORITE_BLOCK.get());
			tag(NetherDescentBlockTags.STORAGE_BLOCKS_RAW_PENDORITE).add(NetherDescentBlocks.RAW_PENDORITE_BLOCK.get());
			tag(Tags.Blocks.STORAGE_BLOCKS).addTag(NetherDescentBlockTags.STORAGE_BLOCKS_PENDORITE).addTag(NetherDescentBlockTags.STORAGE_BLOCKS_RAW_PENDORITE);

            tag(BlockTags.NEEDS_DIAMOND_TOOL).add(NetherDescentBlocks.PENDORITE_ORE.get());
            tag(BlockTags.NEEDS_STONE_TOOL).add(NetherDescentBlocks.PENDORITE_GRATE.get());
			tag(BlockTags.DOORS).add(NetherDescentBlocks.PENDORITE_DOOR.get());
			tag(BlockTags.TRAPDOORS).add(NetherDescentBlocks.PENDORITE_TRAPDOOR.get());

            tag(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON).addTag(BlockTags.NYLIUM).add(NetherDescentBlocks.SYTHIAN_SHOOT.get(), NetherDescentBlocks.SYTHIAN_STALK.get(), NetherDescentBlocks.SYTHIAN_FARMLAND.get());

			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> intrinsicTagAppender = this.tag(BlockTags.REPLACEABLE);
			provider.lookupOrThrow(Registries.BLOCK)
					.filterElements(block -> block.defaultBlockState().canBeReplaced())
					.filterElements(block -> block.getDescriptionId().contains(NetherDescent.MOD_ID))
					.listElementIds()
					.forEach(intrinsicTagAppender::add);
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
			if (type == SoundType.STONE || type == SoundType.DEEPSLATE || type == SoundType.NETHER_BRICKS || type == SoundType.NYLIUM || object instanceof DropExperienceBlock || type == SoundType.COPPER_GRATE)
				tag(BlockTags.MINEABLE_WITH_PICKAXE).add(object);
			else if (type == SoundType.WOOD || type == SoundType.SWEET_BERRY_BUSH || type == SoundType.GLOW_LICHEN || type == SoundType.FUNGUS || type == SoundType.SCAFFOLDING)
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
            copy(BlockTags.FENCES, ItemTags.FENCES);
            copy(Tags.Blocks.FENCES_NETHER_BRICK, Tags.Items.FENCES_NETHER_BRICK);
			copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
			copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
			copy(BlockTags.DOORS, ItemTags.DOORS);
			copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
			copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
			copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
			copy(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
			copy(BlockTags.LOGS, ItemTags.LOGS);
			copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
			copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
			copy(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES, Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES);
			copy(Tags.Blocks.NETHERRACKS, Tags.Items.NETHERRACKS);
            copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
            copy(Tags.Blocks.ORES_QUARTZ, Tags.Items.ORES_QUARTZ);
            NetherDescentWoodSet.woodsets().forEach(set -> copy(set.logBlockTag(), set.logItemTag()));

			tag(Tags.Items.BRICKS_NETHER).add(NetherDescentItems.BLUE_NETHER_BRICK.get());
            tag(Tags.Items.FOODS_BERRY).add(NetherDescentItems.CRIMSON_BERRIES.get());
            tag(Tags.Items.FOODS_PIE).add(NetherDescentItems.CRIMSON_BERRY_PIE.get());

			tag(NetherDescentItemTags.INGOTS_PENDORITE).add(NetherDescentItems.PENDORITE_INGOT.get());
            tag(Tags.Items.INGOTS).addTag(NetherDescentItemTags.INGOTS_PENDORITE);

			copy(NetherDescentBlockTags.STORAGE_BLOCKS_PENDORITE, NetherDescentItemTags.STORAGE_BLOCKS_PENDORITE);
			copy(NetherDescentBlockTags.STORAGE_BLOCKS_RAW_PENDORITE, NetherDescentItemTags.STORAGE_BLOCKS_RAW_PENDORITE);
			copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
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

	private static class StructureTagGenerator extends StructureTagsProvider {

		private StructureTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
			super(arg, completableFuture, NetherDescent.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider provider) {
			tag(NetherDescentStructureTags.FORTRESSES)
					.add(NetherDescentStructures.BLUE_FORTRESS, BuiltinStructures.FORTRESS);
		}
	}

    private static class DamageTypeTagGenerator extends DamageTypeTagsProvider {

        private DamageTypeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(arg, completableFuture, NetherDescent.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(DamageTypeTags.NO_KNOCKBACK).add(NetherDescentDamageTypes.CRIMSON_BERRY_BUSH);
            tag(Tags.DamageTypes.IS_ENVIRONMENT).add(NetherDescentDamageTypes.CRIMSON_BERRY_BUSH);
            tag(Tags.DamageTypes.IS_PHYSICAL).add(NetherDescentDamageTypes.CRIMSON_BERRY_BUSH);
        }
    }
}
