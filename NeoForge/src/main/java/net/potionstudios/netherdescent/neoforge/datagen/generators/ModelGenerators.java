package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;

public class ModelGenerators {

	public static void init(DataGenerator generator, boolean run, PackOutput output, ExistingFileHelper exFileHelper) {
		generator.addProvider(run, new BlockModelGenerators(output, exFileHelper));
		generator.addProvider(run, new ItemModelGenerators(output, exFileHelper));
	}

	private static class ItemModelGenerators extends ItemModelProvider {

		private ItemModelGenerators(PackOutput output, ExistingFileHelper existingFileHelper) {
			super(output, NetherDescent.MOD_ID, existingFileHelper);
		}

		@Override
		protected void registerModels() {
			NetherDescentItems.SIMPLE_ITEMS.forEach(item -> basicItem(item.get()));
			simpleItemBlockTexture(NetherDescentBlocks.WAILING_VINE.get());
		}

		private void simpleItemBlockTexture(ItemLike item) {
			singleTexture(name(item), mcLoc("item/generated"), "layer0", NetherDescent.id(ModelProvider.BLOCK_FOLDER + "/" + name(item)));
		}

		private String name(ItemLike item) {
			return key(item.asItem()).getPath();
		}

		private ResourceLocation key(Item item) {
			return BuiltInRegistries.ITEM.getKey(item);
		}
	}

	private static class BlockModelGenerators extends BlockStateProvider {

		private BlockModelGenerators(PackOutput output, ExistingFileHelper exFileHelper) {
			super(output, NetherDescent.MOD_ID, exFileHelper);
		}

		@Override
		protected void registerStatesAndModels() {
			NetherDescentBlocks.cubeAllBlocks.forEach(block -> simpleBlockWithItem(block.get(), cubeAll(block.get())));

			NetherDescentBlockSet.getBlockSets().forEach(set -> {
				registerSlab(set.getSlab(), set.getBase());
				registerStairs(set.getStairs(), set.getBase());
				registerWall(set.getWall(), set.getBase());
			});

			ResourceLocation blue_netherrack = models().cubeAll(name(NetherDescentBlocks.BLUE_NETHERRACK.get()), blockTexture(NetherDescentBlocks.BLUE_NETHERRACK.get())).getLocation();
			simpleBlockItem(NetherDescentBlocks.BLUE_NETHERRACK.get(), models().getExistingFile(blue_netherrack));
			VariantBlockStateBuilder builder = getVariantBuilder(NetherDescentBlocks.BLUE_NETHERRACK.get());

			for (int y : new int[]{0, 90, 180, 270})
				for (int x : new int[]{0, 90, 180, 270})
					builder.partialState()
							.addModels(ConfiguredModel.builder()
									.modelFile(models().getExistingFile(blue_netherrack))
									.rotationY(y)
									.rotationX(x)
									.build());

			simpleBlockWithItem(NetherDescentBlocks.WAILING_NYLIUM.get(), models().cubeBottomTop(name(NetherDescentBlocks.WAILING_NYLIUM.get()), blockNDTexture(NetherDescentBlocks.WAILING_NYLIUM.get(), "side"), blockTexture(Blocks.SOUL_SAND), blockTexture(NetherDescentBlocks.WAILING_NYLIUM.get())));
			simpleBlockWithItem(NetherDescentBlocks.EMBUR_NYLIUM.get(), models().cubeBottomTop(name(NetherDescentBlocks.EMBUR_NYLIUM.get()), blockNDTexture(NetherDescentBlocks.EMBUR_NYLIUM.get(), "side"), blockTexture(NetherDescentBlocks.BLUE_NETHERRACK.get()), blockTexture(NetherDescentBlocks.EMBUR_NYLIUM.get())));
			simpleBlockWithItem(NetherDescentBlocks.SYTHIAN_NYLIUM.get(), models().cubeBottomTop(name(NetherDescentBlocks.SYTHIAN_NYLIUM.get()), blockNDTexture(NetherDescentBlocks.SYTHIAN_NYLIUM.get(), "side"), blockTexture(Blocks.NETHERRACK), blockTexture(NetherDescentBlocks.SYTHIAN_NYLIUM.get())));
		}

		private void registerStairs(StairBlock stairs, Block texturedBlock) {
			registerStairs(stairs, ModelLocationUtils.getModelLocation(texturedBlock));
		}

		private void registerStairs(StairBlock stairs, ResourceLocation texture) {
			stairsBlock(stairs, texture);
			simpleBlockItem(stairs, itemModels().stairs("block/" + key(stairs).getPath(), texture, texture, texture));
		}

		private void registerSlab(SlabBlock slab, Block texturedBlock) {
			ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
			slabBlock(slab, texture, texture);
			simpleBlockItem(slab, itemModels().slab("block/" + key(slab).getPath(), texture, texture, texture));
		}

		private void registerWall(WallBlock wall, Block texturedBlock) {
			ResourceLocation texture = ModelLocationUtils.getModelLocation(texturedBlock);
			wallBlock(wall, texture);
			simpleBlockItem(wall, itemModels().wallInventory("block/" + key(wall).getPath(), texture));
		}

		private String name(Block block) {
			return key(block).getPath();
		}

		private ResourceLocation key(Block block) {
			return BuiltInRegistries.BLOCK.getKey(block);
		}

		private ResourceLocation blockNDTexture(Block block, String end) {
			return NetherDescent.id(ModelProvider.BLOCK_FOLDER + "/" + key(block).getPath() + "_" + end);
		}
	}
}
