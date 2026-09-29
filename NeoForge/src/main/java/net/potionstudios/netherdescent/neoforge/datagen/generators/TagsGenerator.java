package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.*;
import net.potionstudios.netherdescent.world.damagesource.NetherDescentDamageTypes;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityTypeIds;
import net.potionstudios.netherdescent.world.item.NetherDescentBlockItemIds;
import net.potionstudios.netherdescent.world.item.NetherDescentItemIds;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlockIds;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class TagsGenerator {

	public static void init(DataGenerator generator, boolean run, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		BlockTagGenerator BlockTags = generator.addProvider(run, new BlockTagGenerator(output, lookupProvider));
		generator.addProvider(run, new ItemTagGenerator(output, lookupProvider, BlockTags));
		generator.addProvider(run, new BiomeTagGenerator(output, lookupProvider));
		generator.addProvider(run, new StructureTagGenerator(output, lookupProvider));
        generator.addProvider(run, new DamageTypeTagGenerator(output, lookupProvider));
        generator.addProvider(run, new EntityTypeTagGenerator(output, lookupProvider));
	}

	/**
	 * Used to generate tags for blocks.
	 * @see BlockTagsProvider
	 */
	private static class BlockTagGenerator extends BlockTagsProvider {
		private BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(output, lookupProvider, NetherDescent.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			NetherDescentBlocks.BLOCKS.forEach(block -> easyBlockTags(block.get()));
			NetherDescentWoodSet.woodsets().forEach(set -> {
				tag(BlockTags.PLANKS).add(set.planks().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_SLABS).add(set.slab().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_STAIRS).add(set.stairs().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_BUTTONS).add(set.button().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_PRESSURE_PLATES).add(set.pressurePlate().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_TRAPDOORS).add(set.trapdoor().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_DOORS).add(set.door().builtInRegistryHolder().key());
				tag(BlockTags.WOODEN_FENCES).add(set.fence().builtInRegistryHolder().key());
				tag(BlockTags.FENCE_GATES).add(set.fenceGate().builtInRegistryHolder().key());
				tag(Tags.Blocks.FENCE_GATES_WOODEN).add(set.fenceGate().builtInRegistryHolder().key());
				tag(BlockTags.STANDING_SIGNS).add(set.sign().builtInRegistryHolder().key());
				tag(BlockTags.WALL_SIGNS).add(set.wallSign().builtInRegistryHolder().key());
				tag(BlockTags.CEILING_HANGING_SIGNS).add(set.hangingSign().builtInRegistryHolder().key());
				tag(BlockTags.WALL_HANGING_SIGNS).add(set.wallHangingSign().builtInRegistryHolder().key());
				tag(Tags.Blocks.BOOKSHELVES).add(set.bookshelf().builtInRegistryHolder().key());
				tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(set.bookshelf().builtInRegistryHolder().key());
				tag(set.logBlockTag()).add(set.logstem().builtInRegistryHolder().key(), set.wood().builtInRegistryHolder().key(), set.strippedLogStem().builtInRegistryHolder().key(), set.strippedWood().builtInRegistryHolder().key());
                tag(Tags.Blocks.NATURAL_WOODS).add(set.wood().builtInRegistryHolder().key());
                tag(BlockTags.LOGS).addOptionalTag(set.logBlockTag());
				tag(Tags.Blocks.STRIPPED_LOGS).add(set.strippedLogStem().builtInRegistryHolder().key());
				tag(Tags.Blocks.STRIPPED_WOODS).add(set.strippedWood().builtInRegistryHolder().key());
                tag(Tags.Blocks.NETHER_NATURAL_LOGS).add(set.logstem().builtInRegistryHolder().key());
				tag(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES).add(set.craftingTable().builtInRegistryHolder().key());
			});
			tag(BlockTags.NEEDS_IRON_TOOL).add(
					NetherDescentBlockIds.PENDORITE_BLOCK,
					NetherDescentBlockIds.PENDORITE_ORE,
					NetherDescentBlockIds.RAW_PENDORITE_BLOCK,
					NetherDescentBlockIds.CUT_PENDORITE,
					NetherDescentBlockIds.CUT_PENDORITE_SLAB,
					NetherDescentBlockIds.CUT_PENDORITE_STAIRS,
					NetherDescentBlockIds.CHISELED_PENDORITE,
					NetherDescentBlockIds.PENDORITE_GRATE,
					NetherDescentBlockIds.PENDORITE_DOOR,
					NetherDescentBlockIds.PENDORITE_TRAPDOOR,
					NetherDescentBlockIds.PENDORITE_CHAIN,
					NetherDescentBlockIds.PENDORITE_BARS,
					NetherDescentBlockIds.PENDORITE_LANTERN,
					NetherDescentBlockIds.PENDORITE_FIRE_ROD
			);
			tag(BlockTags.FENCES).add(NetherDescentBlockIds.BLUE_NETHER_BRICK_FENCE);
			tag(Tags.Blocks.FENCES_NETHER_BRICK).add(NetherDescentBlockIds.BLUE_NETHER_BRICK_FENCE);
			tag(BlockTags.BASE_STONE_NETHER).add(NetherDescentBlockIds.BLUE_NETHERRACK);
			tag(BlockTags.CLIMBABLE).add(
					NetherDescentBlockIds.WAILING_VINES,
					NetherDescentBlockIds.WAILING_VINES_PLANT,
					NetherDescentBlockIds.EMBUR_GEL_VINES,
					NetherDescentBlockIds.EMBUR_GEL_VINES_PLANT,
					NetherDescentBlockIds.SYTHIAN_SCAFFOLDING,
					NetherDescentBlockIds.EMBUR_HANGING_MOSS,
					NetherDescentBlockIds.HANGING_SYTHIAN_ROOTS,
					NetherDescentBlockIds.HANGING_SYTHIAN_ROOTS_PLANT,
					NetherDescentBlockIds.ARISIAN_TANGLE_ROOTS,
					NetherDescentBlockIds.ARISIAN_TANGLE_ROOTS_PLANT
			);
			tag(Tags.Blocks.NETHERRACKS).add(NetherDescentBlockIds.BLUE_NETHERRACK);
			tag(BlockTags.ENDERMAN_HOLDABLE).add(
					NetherDescentBlockIds.EMBUR_NYLIUM,
					NetherDescentBlockIds.SYTHIAN_NYLIUM,
					NetherDescentBlockIds.WAILING_NYLIUM
			);
			tag(BlockTags.SWORD_EFFICIENT).add(
					NetherDescentBlockIds.EMBUR_SPROUTS,
					NetherDescentBlockIds.EMBUR_CAVE_MOSS,
					NetherDescentBlockIds.SYTHIAN_SPROUTS,
					NetherDescentBlockIds.ARISIAN_SPROUTS
			);
			tag(BlockTags.REPLACEABLE_BY_TREES).add(
					NetherDescentBlockIds.EMBUR_SPROUTS,
					NetherDescentBlockIds.SYTHIAN_SPROUTS,
					NetherDescentBlockIds.ARISIAN_SPROUTS
			);
			tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(
					NetherDescentBlockIds.EMBUR_SPROUTS,
					NetherDescentBlockIds.SYTHIAN_SPROUTS,
					NetherDescentBlockIds.ARISIAN_SPROUTS
			);
			tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(NetherDescentBlockIds.EMBUR_CAVE_MOSS);
			tag(BlockTags.NYLIUM).add(
					NetherDescentBlockIds.SYTHIAN_SOIL,
					NetherDescentBlockIds.EMBUR_MOSS_BLOCK,
					NetherDescentBlockIds.ARISIAN_MOSS_BLOCK
			);
			tag(BlockTags.GOLD_ORES).add(NetherDescentBlockIds.BLUE_NETHER_GOLD_ORE);
			tag(BlockTags.BEE_GROWABLES).add(NetherDescentBlockIds.CRIMSON_BERRY_BUSH);
			tag(Tags.Blocks.ORES_QUARTZ).add(NetherDescentBlockIds.BLUE_NETHER_QUARTZ_ORE);
			tag(Tags.Blocks.ORE_RATES_SPARSE).add(NetherDescentBlockIds.BLUE_NETHER_GOLD_ORE);
			tag(Tags.Blocks.ORE_RATES_SINGULAR).add(NetherDescentBlockIds.BLUE_NETHER_QUARTZ_ORE);
			tag(Tags.Blocks.CHAINS).add(NetherDescentBlockIds.PENDORITE_CHAIN);
			tag(BlockTags.WART_BLOCKS).add(
					NetherDescentBlockIds.SYTHIAN_WART_BLOCK,
					NetherDescentBlockIds.WAILING_WART_BLOCK
			);

			tag(NetherDescentBlockTags.STORAGE_BLOCKS_PENDORITE).add(NetherDescentBlockIds.PENDORITE_BLOCK);
			tag(NetherDescentBlockTags.STORAGE_BLOCKS_RAW_PENDORITE).add(NetherDescentBlockIds.RAW_PENDORITE_BLOCK);
			tag(Tags.Blocks.STORAGE_BLOCKS).addTag(NetherDescentBlockTags.STORAGE_BLOCKS_PENDORITE).addTag(NetherDescentBlockTags.STORAGE_BLOCKS_RAW_PENDORITE);

			tag(BlockTags.DOORS).add(NetherDescentBlockIds.PENDORITE_DOOR);
			tag(BlockTags.TRAPDOORS).add(NetherDescentBlockIds.PENDORITE_TRAPDOOR);
			tag(BlockTags.DRAGON_IMMUNE).add(NetherDescentBlockIds.PENDORITE_BARS);
			tag(BlockTags.CAMPFIRES).add(NetherDescentBlockIds.PENDORITE_CAMPFIRE);

			tag(BlockTags.FLOWERS).add(
					NetherDescentBlockIds.TALL_ARISIAN_DANDELIONS,
					NetherDescentBlockIds.EMBUR_LILY,
					NetherDescentBlockIds.ARISIAN_BLOSSOM,
					NetherDescentBlockIds.ARISIAN_DANDELIONS
			);

			tag(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON).addTag(BlockTags.NYLIUM).add(
					NetherDescentBlockIds.SYTHIAN_SHOOT,
					NetherDescentBlockIds.SYTHIAN_STALK,
					NetherDescentBlockIds.SYTHIAN_FARMLAND
			);
			tag(NetherDescentBlockTags.NETHER_MOSS_REPLACEABLE).addTag(BlockTags.BASE_STONE_NETHER).addTag(BlockTags.NYLIUM);

			tag(BlockTags.WALL_POST_OVERRIDE).add(NetherDescentBlockIds.PENDORITE_TORCH);
			tag(BlockTags.SOUL_SPEED_BLOCKS).add(NetherDescentBlockIds.WAILING_NYLIUM);
			tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(NetherDescentBlockIds.WAILING_NYLIUM);

			tag(BlockTags.LANTERNS).add(NetherDescentBlockIds.PENDORITE_LANTERN);

			tag(BlockTags.REPLACEABLE)
					.addAll(provider.lookupOrThrow(Registries.BLOCK)
							.listElements()
							.filter(holder -> holder.value().defaultBlockState().canBeReplaced())
							.filter(holder -> holder.value().getDescriptionId().contains(NetherDescent.MOD_ID))
							.map(Holder.Reference::key));
		}

		private void easyBlockTags(Block object) {
			ResourceKey<Block> key = object.builtInRegistryHolder().key();
			if (object instanceof SlabBlock) tag(BlockTags.SLABS).add(key);
			else if (object instanceof StairBlock) tag(BlockTags.STAIRS).add(key);
			else if (object instanceof WallBlock) tag(BlockTags.WALLS).add(key);
			else if (object instanceof ColoredFallingBlock) tag(BlockTags.SAND).add(key);
			else if (object instanceof LeavesBlock) tag(BlockTags.LEAVES).add(key);
			else if (object instanceof CampfireBlock) tag(BlockTags.CAMPFIRES).add(key);
			else if (object instanceof FlowerPotBlock) tag(BlockTags.FLOWER_POTS).add(key);
			else if (object instanceof NyliumBlock) tag(BlockTags.NYLIUM).add(key);
			SoundType type = object.defaultBlockState().getSoundType();
			if (type == SoundType.STONE || type == SoundType.DEEPSLATE || type == SoundType.NETHER_BRICKS || type == SoundType.NYLIUM || object instanceof DropExperienceBlock || type == SoundType.COPPER || type == SoundType.COPPER_GRATE || type == SoundType.CHAIN || type == SoundType.LANTERN || type == SoundType.METAL || type == SoundType.NETHERRACK)
				tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key);
			else if (type == SoundType.WOOD || type == SoundType.SWEET_BERRY_BUSH || type == SoundType.GLOW_LICHEN || type == SoundType.FUNGUS || type == SoundType.SCAFFOLDING || type == SoundType.NETHER_WOOD)
				tag(BlockTags.MINEABLE_WITH_AXE).add(key);
			else if (object instanceof LeavesBlock || type == SoundType.WART_BLOCK)
				tag(BlockTags.MINEABLE_WITH_HOE).add(key);
			else if (type == SoundType.GRAVEL || type == SoundType.SAND || type == SoundType.SNOW)
				tag(BlockTags.MINEABLE_WITH_SHOVEL).add(key);
		}
	}

    private static class ItemTagGenerator extends BlockTagCopyingItemTagProvider {
		private ItemTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, BlockTagGenerator blockTagGenerator) {
			super(arg, completableFuture, blockTagGenerator.contentsGetter(), NetherDescent.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.@NonNull Provider provider) {
			copy(BlockTags.WALLS, ItemTags.WALLS);
			copy(BlockTags.PLANKS, ItemTags.PLANKS);
			copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
			copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
			copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
			copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
			copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
			copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
			copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
			copy(Tags.Blocks.FENCES_NETHER_BRICK, Tags.Items.FENCES_NETHER_BRICK);
			copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
			copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
			copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
			copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
			copy(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
			copy(BlockTags.LOGS, ItemTags.LOGS);
			copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
			copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
			copy(Tags.Blocks.NETHER_NATURAL_LOGS, Tags.Items.NETHER_NATURAL_LOGS);
			copy(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES, Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES);
			copy(Tags.Blocks.NETHERRACKS, Tags.Items.NETHERRACKS);
			copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
			copy(Tags.Blocks.ORES_QUARTZ, Tags.Items.ORES_QUARTZ);
			copy(Tags.Blocks.CHAINS, Tags.Items.CHAINS);
			NetherDescentWoodSet.woodsets().forEach(set -> copy(set.logBlockTag(), set.logItemTag()));
			copy(NetherDescentBlockTags.ORES_PENDORITE, NetherDescentItemTags.ORES_PENDORITE);
			copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
            copy(Tags.Blocks.CHAINS, Tags.Items.CHAINS);
			copy(Tags.Blocks.ORE_BEARING_GROUND_NETHERRACK, Tags.Items.ORE_BEARING_GROUND_NETHERRACK);
            NetherDescentWoodSet.woodsets().forEach(set -> copy(set.logBlockTag(), set.logItemTag()));
			copy(BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);

			tag(Tags.Items.BRICKS_NETHER).add(NetherDescentItemIds.BLUE_NETHER_BRICK);
			tag(Tags.Items.FOODS_BERRY).add(NetherDescentBlockItemIds.CRIMSON_BERRIES);
			tag(Tags.Items.FOODS_PIE).add(NetherDescentItemIds.CRIMSON_BERRY_PIE);
			NetherDescentWoodSet.woodsets().forEach(set -> tag(ItemTags.NON_FLAMMABLE_WOOD).addTag(set.logItemTag()).add(
					set.planks().asItem().builtInRegistryHolder().key(),
					set.slab().asItem().builtInRegistryHolder().key(),
					set.stairs().asItem().builtInRegistryHolder().key(),
					set.button().asItem().builtInRegistryHolder().key(),
					set.pressurePlate().asItem().builtInRegistryHolder().key(),
					set.trapdoor().asItem().builtInRegistryHolder().key(),
					set.door().asItem().builtInRegistryHolder().key(),
					set.fence().asItem().builtInRegistryHolder().key(),
					set.fenceGate().asItem().builtInRegistryHolder().key(),
					set.signItem().builtInRegistryHolder().key(),
					set.hangingSignItem().builtInRegistryHolder().key(),
					set.bookshelf().asItem().builtInRegistryHolder().key(),
					set.craftingTable().asItem().builtInRegistryHolder().key()
			));

			tag(Tags.Items.BRICKS_NETHER).add(NetherDescentItems.BLUE_NETHER_BRICK.get());
            tag(Tags.Items.FOODS_BERRY).add(NetherDescentItems.CRIMSON_BERRIES.get());
            tag(Tags.Items.FOODS_PIE).add(NetherDescentItems.CRIMSON_BERRY_PIE.get());

			tag(Tags.Items.ARMORS_HORSE).add(NetherDescentItemIds.PENDORITE_HORSE_ARMOR);
			tag(Tags.Items.ARMORS_WOLF).add(NetherDescentItemIds.PENDORITE_WOLF_ARMOR);

			tag(NetherDescentItemTags.INGOTS_PENDORITE).add(NetherDescentItemIds.PENDORITE_INGOT);
			tag(Tags.Items.INGOTS).addTag(NetherDescentItemTags.INGOTS_PENDORITE);
			tag(NetherDescentItemTags.NUGGETS_PENDORITE).add(NetherDescentItemIds.PENDORITE_NUGGET);
			tag(Tags.Items.NUGGETS).addTag(NetherDescentItemTags.NUGGETS_PENDORITE);
            tag(NetherDescentItemTags.RAW_MATERIALS_PENDORITE).add(NetherDescentItemIds.RAW_PENDORITE);
            tag(Tags.Items.RAW_MATERIALS).addTag(NetherDescentItemTags.RAW_MATERIALS_PENDORITE);

			copy(NetherDescentBlockTags.STORAGE_BLOCKS_PENDORITE, NetherDescentItemTags.STORAGE_BLOCKS_PENDORITE);
			copy(NetherDescentBlockTags.STORAGE_BLOCKS_RAW_PENDORITE, NetherDescentItemTags.STORAGE_BLOCKS_RAW_PENDORITE);
			copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

			tag(ItemTags.CREEPER_IGNITERS).add(NetherDescentItemIds.SOUL_FIRE_CHARGE, NetherDescentItemIds.PENDORITE_FIRE_CHARGE);
			tag(Tags.Items.RODS_BLAZE).add(NetherDescentItemIds.SOUL_BLAZE_ROD);
            tag(ItemTags.CREEPER_IGNITERS).add(NetherDescentItemIds.SOUL_FIRE_CHARGE, NetherDescentItemIds.PENDORITE_FIRE_CHARGE);
            tag(Tags.Items.RODS_BLAZE).add(NetherDescentItemIds.SOUL_BLAZE_ROD);

			copy(BlockTags.LANTERNS, ItemTags.LANTERNS);
			copy(Tags.Blocks.NATURAL_WOODS, Tags.Items.NATURAL_WOODS);
			copy(Tags.Blocks.ORES, Tags.Items.ORES);
			copy(Tags.Blocks.ORE_RATES_SPARSE, Tags.Items.ORE_RATES_SPARSE);
			copy(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK);
			copy(BlockTags.LEAVES, ItemTags.LEAVES);
			copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
			copy(BlockTags.BARS, ItemTags.BARS);
		}
	}

	private static class BiomeTagGenerator extends BiomeTagsProvider {
		private BiomeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(arg, completableFuture, NetherDescent.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.@NonNull Provider provider) {
			NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList().forEach(biome -> tag(NetherDescentBiomeTags.NETHER).add(biome));
			NetherDescentBiomes.BIOMES_BY_TAG.forEach((tag, biome) -> tag(tag).add(biome));

			tag(BiomeTags.IS_NETHER).addTag(NetherDescentBiomeTags.NETHER);
			tag(Tags.Biomes.IS_HOT_NETHER).addTag(NetherDescentBiomeTags.HOT);
			tag(Tags.Biomes.IS_DRY_NETHER).addTag(NetherDescentBiomeTags.DRY);
			tag(Tags.Biomes.IS_NETHER_FOREST).addTag(NetherDescentBiomeTags.FOREST);
		}
	}

	private static class StructureTagGenerator extends StructureTagsProvider {
		private StructureTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(arg, completableFuture, NetherDescent.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.@NonNull Provider provider) {
			tag(NetherDescentStructureTags.FORTRESSES).add(NetherDescentStructures.BLUE_FORTRESS).add(BuiltinStructures.FORTRESS);

			tag(NetherDescentStructureTags.CHAINS).add(NetherDescentStructures.SMALL_CHAINS).add(NetherDescentStructures.MEDIUM_CHAINS).add(NetherDescentStructures.LARGE_CHAINS);

			tag(Tags.Structures.HIDDEN_FROM_DISPLAYERS).addTag(NetherDescentStructureTags.CHAINS);
			tag(Tags.Structures.HIDDEN_FROM_LOCATOR_SELECTION).addTag(NetherDescentStructureTags.CHAINS);
		}
	}

	private static class DamageTypeTagGenerator extends DamageTypeTagsProvider {
		private DamageTypeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(arg, completableFuture, NetherDescent.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.@NonNull Provider provider) {
			tag(DamageTypeTags.NO_KNOCKBACK).add(NetherDescentDamageTypes.CRIMSON_BERRY_BUSH);
			tag(Tags.DamageTypes.IS_ENVIRONMENT).add(NetherDescentDamageTypes.CRIMSON_BERRY_BUSH);
			tag(Tags.DamageTypes.IS_PHYSICAL).add(NetherDescentDamageTypes.CRIMSON_BERRY_BUSH);
		}
	}

	private static class EntityTypeTagGenerator extends EntityTypeTagsProvider {
		private EntityTypeTagGenerator(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(arg, completableFuture, NetherDescent.MOD_ID);
		}

		@Override
		protected void addTags(HolderLookup.@NonNull Provider provider) {
			tag(EntityTypeTags.IMPACT_PROJECTILES).add(NetherDescentEntityTypeIds.SMALL_SOUL_FIREBALL, NetherDescentEntityTypeIds.SOUL_FIREBALL);
			tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(NetherDescentEntityTypeIds.PENDORITE_BLAZE, NetherDescentEntityTypeIds.SOUL_BLAZE, NetherDescentEntityTypeIds.HORNET, NetherDescentEntityTypeIds.SOUL_GHAST);
			tag(EntityTypeTags.ARTHROPOD).add(NetherDescentEntityTypeIds.HORNET);
			tag(EntityTypeTags.REDIRECTABLE_PROJECTILE).add(NetherDescentEntityTypeIds.SOUL_FIREBALL);
			tag(NetherDescentEntityTypeTags.SOUL_FIRE_FLAME).add(NetherDescentEntityTypeIds.SMALL_SOUL_FIREBALL, NetherDescentEntityTypeIds.SOUL_FIREBALL, NetherDescentEntityTypeIds.SOUL_BLAZE);
		}
	}
}
