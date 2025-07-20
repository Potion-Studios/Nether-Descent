package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

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
			NetherDescentWoodSet.woodsets().forEach(set -> {
				simpleItem(set.door(), set.name() + "/door");
				simpleItem(set.signItem(), set.name() + "/sign");
				simpleItem(set.hangingSignItem(), set.name() + "/hanging_sign");
			});
			simpleItem(NetherDescentBlocks.EMBUR_SPROUTS.get(), "embur_sprouts");
			simpleItemBlockTexture(NetherDescentBlocks.EMBUR_LILY.get());
			simpleItemBlockTexture(NetherDescentBlocks.EMBUR_GEL_VINES.get(), "embur_gel_vines_plant");
			simpleItemBlockTexture(NetherDescentBlocks.EMBUR_ROOTS.get());
		}

		private void simpleItem(ItemLike item, String texture) {
			singleTexture(name(item), mcLoc("item/generated"), "layer0", NetherDescent.id(ModelProvider.ITEM_FOLDER + "/" + texture));
		}

		private void simpleItemBlockTexture(ItemLike item) {
			singleTexture(name(item), mcLoc("item/generated"), "layer0", NetherDescent.id(ModelProvider.BLOCK_FOLDER + "/" + name(item)));
		}

		private void simpleItemBlockTexture(ItemLike item, String texture) {
			singleTexture(name(item), mcLoc("item/generated"), "layer0", NetherDescent.id(ModelProvider.BLOCK_FOLDER + "/" + texture));
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

			NetherDescentWoodSet.woodsets().forEach(set -> {
				ResourceLocation planksTexture = woodBlockTexture(set.name(), "planks");
				simpleBlockWithItem(set.planks(), models().cubeAll(name(set.planks()), planksTexture));
				ResourceLocation logTexture = woodBlockTexture(set.name(), set.logStemEnum().getName());
				slabBlock(set.slab(), key(set.planks()), planksTexture);
				itemModels().slab(name(set.slab()), planksTexture, planksTexture, planksTexture);
				log(set);
				ResourceLocation strippedLogTexture = woodBlockTexture(set.name(), "stripped_" + set.logStemEnum().getName());
				log(set.strippedLogStem(), strippedLogTexture, woodBlockTexture(set.name(), "stripped_" + set.logStemEnum().getName() + "_top"));
				woodBlock(set.wood(), logTexture);
				woodBlock(set.strippedWood(), strippedLogTexture);
				registerStairs(set.stairs(), planksTexture);
				registerButton(set.button(), planksTexture);
				registerFenceAndGate(set.fence(), set.fenceGate(), planksTexture);
				signBlock(set.sign(), set.wallSign(), planksTexture);
				hangingSignBlock(set.hangingSign(), set.wallHangingSign(), models().sign(name(set.hangingSign()), strippedLogTexture));
				trapdoorBlockWithRenderType(set.trapdoor(), woodBlockTexture(set.name(), "trapdoor"), true, "cutout");
				itemModels().trapdoorBottom(name(set.trapdoor()), woodBlockTexture(set.name(), "trapdoor")).renderType("cutout");
				doorBlockWithRenderType(set.door(), woodBlockTexture(set.name(), "door_bottom"), woodBlockTexture(set.name(), "door_top"), "cutout");
				pressurePlateBlock(set.pressurePlate(), planksTexture);
				itemModels().pressurePlate(name(set.pressurePlate()), planksTexture);
				simpleBlockWithItem(set.bookshelf(), models().cubeColumn(name(set.bookshelf()), woodBlockTexture(set.name(), "bookshelf"), planksTexture));
				simpleBlockWithItem(set.craftingTable(), models().cube(name(set.craftingTable()), planksTexture, woodBlockTexture(set.name(), "crafting_table_top"), woodBlockTexture(set.name(), "crafting_table_front"), woodBlockTexture(set.name(), "crafting_table_side"), woodBlockTexture(set.name(), "crafting_table_side"), woodBlockTexture(set.name(), "crafting_table_front")).texture("particle", woodBlockTexture(set.name(), "crafting_table_front")));
			});

			ResourceLocation blue_netherrack = models().cubeAll(name(NetherDescentBlocks.BLUE_NETHERRACK.get()), blockTexture(NetherDescentBlocks.BLUE_NETHERRACK.get())).getLocation();
			simpleBlockItem(NetherDescentBlocks.BLUE_NETHERRACK.get(), models().getExistingFile(blue_netherrack));
			VariantBlockStateBuilder builder = getVariantBuilder(NetherDescentBlocks.BLUE_NETHERRACK.get());

			createCrossBlock(NetherDescentBlocks.EMBUR_SPROUTS.get(), "cutout");
			createCrossBlock(NetherDescentBlocks.EMBUR_GEL_VINES.get(), "translucent");
			createCrossBlock(NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get(),  "translucent");
			createCrossBlock(NetherDescentBlocks.EMBUR_ROOTS.get(), "cutout_mipped");
			createDoubleBlock(NetherDescentBlocks.TALL_EMBUR_ROOTS.get());

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

			registerPatchBlockStates(NetherDescentBlocks.EMBUR_LILY.get(), new String[]{"embur_lily", "embur_lily2"});
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

		private void registerButton(ButtonBlock button, ResourceLocation texture) {
			buttonBlock(button, texture);
			itemModels().buttonInventory(key(button).getPath(), texture);
		}

		private void registerFenceAndGate(FenceBlock fence, FenceGateBlock gate, ResourceLocation texture) {
			fenceBlock(fence, texture);
			itemModels().fenceInventory(key(fence).getPath(), texture);
			fenceGateBlock(gate, texture);
			itemModels().fenceGate(key(gate).getPath(), texture);
		}

		private void log(NetherDescentWoodSet set) {
			log(set.logstem(), woodBlockTexture(set.name(), set.logStemEnum().getName()), woodBlockTexture(set.name(), set.logStemEnum().getName() + "_top"));
		}

		private void log(RotatedPillarBlock block, ResourceLocation side, ResourceLocation top) {
			axisBlock(block, side, top);
			itemModels().cubeColumn(name(block), side, top);
		}

		private void woodBlock(RotatedPillarBlock block, ResourceLocation texture) {
			getVariantBuilder(block).forAllStates(state -> {
				if (state.getValue(RotatedPillarBlock.AXIS).equals(Direction.Axis.X))
					return ConfiguredModel.builder().rotationX(90).rotationY(90).modelFile(models().cubeColumn(name(block), texture, texture)).build();
				else if (state.getValue(RotatedPillarBlock.AXIS).equals(Direction.Axis.Y))
					return ConfiguredModel.builder().modelFile(models().cubeColumn(name(block), texture, texture)).build();
				else
					return ConfiguredModel.builder().rotationX(90).modelFile(models().cubeColumn(name(block), texture, texture)).build();
			});
			simpleBlockItem(block, models().cubeColumn(name(block), texture, texture));
		}

		private void createCrossBlock(Block block, String renderType) {
			simpleBlock(block, models().cross(name(block), blockTexture(block)).renderType(renderType));
		}

		private void createDoubleBlock(DoublePlantBlock doubleBlock) {
			getVariantBuilder(doubleBlock)
					.partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(models().cross(name(doubleBlock) + "_bottom", blockNDTexture(doubleBlock, "bottom")).texture("particle", blockNDTexture(doubleBlock, "bottom")).renderType("cutout")))
					.partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(models().cross(name(doubleBlock) + "_top", blockNDTexture(doubleBlock, "top")).texture("particle", blockNDTexture(doubleBlock, "top")).renderType("cutout")));
			simpleItemBlockTexture(doubleBlock, name(doubleBlock) + "_top");
		}

		private void simpleItemBlockTexture(Block block, String texture) {
			itemModels().singleTexture(key(block).getPath(), mcLoc("item/generated"), "layer0", NetherDescent.id(ModelProvider.BLOCK_FOLDER + "/" + texture));
		}

		private void registerPatchBlockStates(Block block, String[] models) {
			VariantBlockStateBuilder builder = getVariantBuilder(block);
			int[] rotations = {0, 90, 180, 270};

			for (String model : models) {
				for (int rotation : rotations) {
					builder.partialState()
							.addModels(new ConfiguredModel(models().getExistingFile(modLoc("block/" + model)), 0, rotation, false));
				}
			}
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

		private ResourceLocation woodBlockTexture(String type, String name) {
			return NetherDescent.id(ModelProvider.BLOCK_FOLDER + "/" + type + "/" + name);
		}
	}
}
