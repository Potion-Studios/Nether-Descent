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
import net.potionstudios.netherdescent.world.level.block.custom.HangingMossBlock;
import net.potionstudios.netherdescent.world.level.block.custom.SythianFarmBlock;
import net.potionstudios.netherdescent.world.level.block.plants.CrimsonBerryBushBlock;
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
                simpleBlock(set.growerItem().getBlock(), models().cross(name(set.growerItem().getBlock()), woodBlockTexture(set.name(), set.growerItemEnum().getName())).renderType("cutout"));
                simpleItemBlockTexture(set.growerItem().getBlock(), set.name() + "/" + set.growerItemEnum().getName());
                simpleBlock(set.growerItem().getPottedBlock(), models().withExistingParent(name(set.growerItem().getPottedBlock()), mcLoc("block/flower_pot_cross")).texture("plant", woodBlockTexture(set.name(), set.growerItemEnum().getName())).renderType("cutout"));
                signBlock(set.sign(), set.wallSign(), planksTexture);
				hangingSignBlock(set.hangingSign(), set.wallHangingSign(), models().sign(name(set.hangingSign()), strippedLogTexture));
				trapdoorBlockWithRenderType(set.trapdoor(), woodBlockTexture(set.name(), "trapdoor"), true, set != NetherDescentBlocks.EMBUR ? "cutout" : "translucent");
				itemModels().trapdoorBottom(name(set.trapdoor()), woodBlockTexture(set.name(), "trapdoor")).renderType(set != NetherDescentBlocks.EMBUR ? "cutout" : "translucent");
				doorBlockWithRenderType(set.door(), woodBlockTexture(set.name(), "door_bottom"), woodBlockTexture(set.name(), "door_top"), set != NetherDescentBlocks.EMBUR ? "cutout" : "translucent");
				pressurePlateBlock(set.pressurePlate(), planksTexture);
				itemModels().pressurePlate(name(set.pressurePlate()), planksTexture);
				simpleBlockWithItem(set.bookshelf(), models().cubeColumn(name(set.bookshelf()), woodBlockTexture(set.name(), "bookshelf"), planksTexture));
				simpleBlockWithItem(set.craftingTable(), models().cube(name(set.craftingTable()), planksTexture, woodBlockTexture(set.name(), "crafting_table_top"), woodBlockTexture(set.name(), "crafting_table_front"), woodBlockTexture(set.name(), "crafting_table_side"), woodBlockTexture(set.name(), "crafting_table_side"), woodBlockTexture(set.name(), "crafting_table_front")).texture("particle", woodBlockTexture(set.name(), "crafting_table_front")));
			});

			ResourceLocation blue_netherrack = models().cubeAll(name(NetherDescentBlocks.BLUE_NETHERRACK.get()), blockTexture(NetherDescentBlocks.BLUE_NETHERRACK.get())).getLocation();
			simpleBlockItem(NetherDescentBlocks.BLUE_NETHERRACK.get(), models().getExistingFile(blue_netherrack));
			VariantBlockStateBuilder builder = getVariantBuilder(NetherDescentBlocks.BLUE_NETHERRACK.get());

			simpleBlockWithItem(NetherDescentBlocks.EMBUR_GEL_BLOCK.get(), models().cubeAll(name(NetherDescentBlocks.EMBUR_GEL_BLOCK.get()), blockTexture(NetherDescentBlocks.EMBUR_GEL_BLOCK.get())).renderType("translucent"));
			createCrossBlock(NetherDescentBlocks.EMBUR_SPROUTS.get(), "cutout");
			createCrossBlock(NetherDescentBlocks.EMBUR_GEL_VINES.get(), "translucent");
			createCrossBlock(NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get(),  "translucent");
			createCrossBlock(NetherDescentBlocks.EMBUR_ROOTS.get(), "cutout_mipped");
			createDoubleBlock(NetherDescentBlocks.TALL_EMBUR_ROOTS.get());
            createDoubleBlock(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get());

            createCrossBlock(NetherDescentBlocks.SYTHIAN_SPROUTS.get(), "cutout");
            simpleItemBlockTexture(NetherDescentBlocks.SYTHIAN_SPROUTS.get());

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
            simpleBlockWithItem(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get(), models().cubeBottomTop(name(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get()), blockNDTexture(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get(), "side"), mcLoc("block/blackstone_top"), blockTexture(Blocks.CRIMSON_NYLIUM)));

			registerPatchBlockStates(NetherDescentBlocks.EMBUR_LILY.get(), new String[]{"embur_lily", "embur_lily2"});


			models().withExistingParent(name(NetherDescentBlocks.EMBUR_CAVE_MOSS.get()), "glow_lichen")
					.texture("glow_lichen", blockTexture(NetherDescentBlocks.EMBUR_CAVE_MOSS.get()))
					.texture("particle", blockTexture(NetherDescentBlocks.EMBUR_CAVE_MOSS.get())).renderType("cutout");
			simpleItemBlockTexture(NetherDescentBlocks.EMBUR_CAVE_MOSS.get());

			fenceBlock(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), ModelLocationUtils.getModelLocation(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()));
			itemModels().fenceInventory(key(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get()).getPath(), ModelLocationUtils.getModelLocation(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase()));

			models().cross(name(NetherDescentBlocks.EMBUR_HANGING_MOSS.get()), blockTexture(NetherDescentBlocks.EMBUR_HANGING_MOSS.get())).renderType("cutout");
			models().cross(name(NetherDescentBlocks.EMBUR_HANGING_MOSS.get()) + "_tip", blockNDTexture(NetherDescentBlocks.EMBUR_HANGING_MOSS.get(), "tip")).renderType("cutout");
			getVariantBuilder(NetherDescentBlocks.EMBUR_HANGING_MOSS.get()).forAllStates(state -> {
				if (state.getValue(HangingMossBlock.TIP))
					return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(NetherDescentBlocks.EMBUR_HANGING_MOSS.get()) + "_tip"))).build();
				else return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(NetherDescentBlocks.EMBUR_HANGING_MOSS.get())))).build();
			});
			simpleItemBlockTexture(NetherDescentBlocks.EMBUR_HANGING_MOSS.get());

            simpleBlockItem(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), models().carpet(name(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()), blockTexture(NetherDescentBlocks.EMBUR_MOSS_CARPET.get())).renderType("cutout"));
            models().withExistingParent(name(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()) + "_side_small", NetherDescent.id("block/mossy_carpet_side")).texture("side", blockNDTexture(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), "side_small")).renderType("cutout");
            models().withExistingParent(name(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()) + "_side_tall", NetherDescent.id("block/mossy_carpet_side")).texture("side", blockNDTexture(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), "side_tall")).renderType("cutout");

            var stableScaffolding = models().withExistingParent(name(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get()) + "_stable", mcLoc("block/scaffolding_stable"))
                    .texture("particle", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "top"))
                    .texture("top", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "top"))
                    .texture("bottom", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "bottom"))
                    .texture("side", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "side"))
                    .renderType("cutout");

            var unstableScaffolding = models().withExistingParent(name(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get()) + "_unstable", mcLoc("block/scaffolding_unstable"))
                    .texture("particle", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "top"))
                    .texture("top", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "top"))
                    .texture("bottom", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "bottom"))
                    .texture("side", blockNDTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "side"))
                    .renderType("cutout");

            getVariantBuilder(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get())
                    .forAllStatesExcept(state -> {
                        if (state.getValue(ScaffoldingBlock.BOTTOM))
                            return ConfiguredModel.builder().modelFile(unstableScaffolding).build();
                        else return ConfiguredModel.builder().modelFile(stableScaffolding).build();
                    }, ScaffoldingBlock.WATERLOGGED, ScaffoldingBlock.DISTANCE);


            itemModels().getBuilder(name(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get())).parent(stableScaffolding);

            getVariantBuilder(NetherDescentBlocks.SYTHIAN_FARMLAND.get()).forAllStates(state -> {
                if (state.getValue(SythianFarmBlock.MOSSY)) return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(name(NetherDescentBlocks.SYTHIAN_FARMLAND.get()) + "_mossy", "template_farmland")
                                .texture("dirt", blockTexture(NetherDescentBlocks.SYTHIAN_SOIL.get()))
                                .texture("top", blockNDTexture(NetherDescentBlocks.SYTHIAN_FARMLAND.get(), "mossy"))).build();
                else return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(name(NetherDescentBlocks.SYTHIAN_FARMLAND.get()), "template_farmland")
                                .texture("dirt", blockTexture(NetherDescentBlocks.SYTHIAN_SOIL.get()))
                                .texture("top", blockTexture(NetherDescentBlocks.SYTHIAN_FARMLAND.get()))).build();
            });
            simpleBlockItemExistingModel(NetherDescentBlocks.SYTHIAN_FARMLAND.get());

            getVariantBuilder(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get()).forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(models().cross(name(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get()) + "_stage" + state.getValue(CrimsonBerryBushBlock.AGE), blockNDTexture(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get(), "stage" + state.getValue(CrimsonBerryBushBlock.AGE))).renderType("cutout"))
                    .build());
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

		private void simpleItemBlockTexture(Block block) {
			simpleItemBlockTexture(block, name(block));
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

        private void simpleBlockItemExistingModel(Block block) {
            simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
        }
	}
}
