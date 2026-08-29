package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.SythianFarmBlock;
import net.potionstudios.netherdescent.world.level.block.custom.SythianScaffoldingBlock;
import net.potionstudios.netherdescent.world.level.block.custom.ThornSproutBlock;
import net.potionstudios.netherdescent.world.level.block.custom.WailingBulbBlossomBlock;
import net.potionstudios.netherdescent.world.level.block.plants.ArisianBlossomBlock;
import net.potionstudios.netherdescent.world.level.block.plants.HangingDoublePlantBlock;
import net.potionstudios.netherdescent.world.level.block.plants.HangingNDBushBlock;
import net.potionstudios.netherdescent.world.level.block.plants.SythianShootBlock;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.wood.ArisianLeavesBlock;
import net.potionstudios.netherdescent.world.level.block.wood.HangingFungusBlock;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModelGenerator extends ModelProvider {
	public ModelGenerator(PackOutput arg) {
		super(arg, NetherDescent.MOD_ID);
	}

	@Override
	protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		NetherDescentBlocks.cubeAllBlocks.forEach(block -> {
			blockModels.createTrivialCube(block.get());
			blockItemModel(blockModels, block.get());
		});

		NetherDescentBlockSet.getBlockSets().forEach(set -> {
			TextureMapping baseMapping = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(set.getBase()));
			createSlabAndStairs(blockModels, itemModels, set.getSlab(), set.getStairs(), set.getBase(), baseMapping);
			createWall(blockModels, itemModels, set.getWall(), set.getBase());
		});

		createNetherrack(blockModels, NetherDescentBlocks.BLUE_NETHERRACK.get());

		blockModels.createTrivialBlock(NetherDescentBlocks.BARTERING_TABLE.get(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(template -> template
				.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_bottom"))
				.put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.BARTERING_TABLE.get(), "_side"))
				.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.BARTERING_TABLE.get(), "_side"))
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.BARTERING_TABLE.get(), "_top"))));
		blockItemModel(blockModels, NetherDescentBlocks.BARTERING_TABLE.get());

		NetherDescentWoodSet.woodsets().forEach(woodSet -> {
			String folder = "block/" + woodSet.name() + "/";
			Identifier Planks = NetherDescent.id(folder + "planks");
			blockModels.createTrivialBlock(woodSet.planks(), TexturedModel.CUBE.updateTexture(textureMapping -> textureMapping.put(TextureSlot.ALL, Planks)));
			blockItemModel(blockModels, woodSet.planks());

			TextureMapping planks = new TextureMapping().put(TextureSlot.ALL, Planks);

			createSlabAndStairs(blockModels, itemModels, woodSet.slab(), woodSet.stairs(), woodSet.planks(), planks);

			blockModels.blockStateOutput.accept(BlockModelGenerators.createButton(woodSet.button(), BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(woodSet.button(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(ModelLocationUtils.getModelLocation(woodSet.button(), "_pressed"), planks, blockModels.modelOutput))));
			itemModels.itemModelOutput.accept(woodSet.button().asItem(), ItemModelUtils.plainModel(ModelTemplates.BUTTON_INVENTORY.create(woodSet.button().asItem(), planks, itemModels.modelOutput)));

			blockModels.blockStateOutput.accept(BlockModelGenerators.createFence(woodSet.fence(), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(woodSet.fence(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(woodSet.fence(), planks, blockModels.modelOutput))));
			itemModels.itemModelOutput.accept(woodSet.fence().asItem(), ItemModelUtils.plainModel(ModelTemplates.FENCE_INVENTORY.create(woodSet.fence().asItem(), planks, itemModels.modelOutput)));

			blockModels.blockStateOutput.accept(BlockModelGenerators.createFenceGate(woodSet.fenceGate(), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(woodSet.fenceGate(), planks, blockModels.modelOutput)), false));
			blockItemModel(blockModels, woodSet.fenceGate());

			TextureMapping door = new TextureMapping().put(TextureSlot.TOP, NetherDescent.id(folder + "door_top")).put(TextureSlot.BOTTOM, NetherDescent.id(folder + "door_bottom"));
			blockModels.blockStateOutput.accept(BlockModelGenerators.createDoor(woodSet.door(),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT.create(woodSet.door(), door, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(woodSet.door(), door, blockModels.modelOutput))));


			TextureMapping trapdoor = new TextureMapping().put(TextureSlot.TEXTURE, NetherDescent.id(folder + "trapdoor"));
			Identifier trapdoorBottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput);
			blockModels.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(woodSet.trapdoor(),
					BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(trapdoorBottom),
					BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(woodSet.trapdoor(), trapdoor, blockModels.modelOutput))));

			blockModels.itemModelOutput.accept(woodSet.trapdoor().asItem(), ItemModelUtils.plainModel(trapdoorBottom));

			blockModels.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(woodSet.pressurePlate(),
					BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(woodSet.pressurePlate(), planks, blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(woodSet.pressurePlate(), planks, blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.pressurePlate());

			Identifier Log = NetherDescent.id(folder + woodSet.logStemEnum().getName());
			Identifier LogTop = NetherDescent.id(folder + woodSet.logStemEnum().getName() + "_top");

			blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.logstem(),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.logstem(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.logstem(), new TextureMapping().put(TextureSlot.END, LogTop).put(TextureSlot.SIDE, Log), blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.logstem());

			blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.wood(),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.wood(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.wood(), new TextureMapping().put(TextureSlot.END, Log).put(TextureSlot.SIDE, Log), blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.wood());

			Identifier StrippedLog = NetherDescent.id(folder + "stripped_" + woodSet.logStemEnum().getName());
			Identifier StrippedLogTop = NetherDescent.id(folder + "stripped_" + woodSet.logStemEnum().getName() + "_top");
			blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.strippedLogStem(),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.strippedLogStem(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.strippedLogStem(), new TextureMapping().put(TextureSlot.END, StrippedLogTop).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.strippedLogStem());

			blockModels.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(woodSet.strippedWood(),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.strippedWood(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput)),
					BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(woodSet.strippedWood(), new TextureMapping().put(TextureSlot.END, StrippedLog).put(TextureSlot.SIDE, StrippedLog), blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.strippedWood());

			blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.sign(), BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(woodSet.sign(), planks, blockModels.modelOutput))));
			blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.wallSign(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(woodSet.sign()))));
			blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.hangingSign(), BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(woodSet.hangingSign(), TextureMapping.particle(StrippedLog), blockModels.modelOutput))));
			blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.wallHangingSign(), BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(woodSet.hangingSign()))));

			blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.bookshelf(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(woodSet.bookshelf(), new TextureMapping().put(TextureSlot.END, Planks).put(TextureSlot.SIDE, NetherDescent.id("block/" + woodSet.name() + "/bookshelf")), blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.bookshelf());

			blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woodSet.craftingTable(), BlockModelGenerators.plainVariant(ModelTemplates.CUBE.create(woodSet.craftingTable(), new TextureMapping()
					.put(TextureSlot.DOWN, Planks)
					.put(TextureSlot.UP, NetherDescent.id(folder + "crafting_table_top"))
					.put(TextureSlot.EAST, NetherDescent.id(folder + "crafting_table_side"))
					.put(TextureSlot.WEST, NetherDescent.id(folder + "crafting_table_front"))
					.put(TextureSlot.NORTH, NetherDescent.id(folder + "crafting_table_front"))
					.put(TextureSlot.SOUTH, NetherDescent.id(folder + "crafting_table_side"))
					.put(TextureSlot.PARTICLE, NetherDescent.id(folder + "crafting_table_front")), blockModels.modelOutput))));
			blockItemModel(blockModels, woodSet.craftingTable());

			if (woodSet.growerItem() != null) {
				if (woodSet.growerItem().get() instanceof HangingFungusBlock){
					Identifier model = ModelTemplates.CROSS.extend().renderType(mcLocation("cutout")).build().create(woodSet.growerItem().get(), TextureMapping.cross(NetherDescent.id(folder + woodSet.growerItemEnum().getName())), blockModels.modelOutput);
					blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(woodSet.growerItem().get())
							.with(PropertyDispatch.initial(HangingNDBushBlock.HANGING)
									.select(false, BlockModelGenerators.plainVariant(model))
									.select(true, BlockModelGenerators.plainVariant(model).with(BlockModelGenerators.X_ROT_180))));
				}
				else blockModels.createTrivialBlock(woodSet.growerItem().get(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, NetherDescent.id(folder + woodSet.growerItemEnum().getName()))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
				itemModels.itemModelOutput.accept(woodSet.growerItem().getItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.growerItem().getItem(), TextureMapping.layer0(NetherDescent.id(folder + woodSet.growerItemEnum().getName())), itemModels.modelOutput)));
				blockModels.createTrivialBlock(woodSet.growerItem().getPottedBlock(), TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS.extend().renderType(mcLocation("cutout")).build()).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, NetherDescent.id(folder + woodSet.growerItemEnum().getName()))));
			}

			itemModels.itemModelOutput.accept(woodSet.signItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.signItem(), TextureMapping.layer0(NetherDescent.id("item/" + woodSet.name() + "/sign")), itemModels.modelOutput)));
			itemModels.itemModelOutput.accept(woodSet.hangingSignItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.hangingSignItem(), TextureMapping.layer0(NetherDescent.id("item/" + woodSet.name() + "/hanging_sign")), itemModels.modelOutput)));
			itemModels.itemModelOutput.accept(woodSet.door().asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(woodSet.door(), TextureMapping.layer0(NetherDescent.id("item/" + woodSet.name() + "/door")), itemModels.modelOutput)));
		});

		NetherDescentBlocks.BLOCKS.forEach(block -> {
			if (block.get() instanceof FlowerPotBlock flowerPotBlock && !flowerPotBlock.getPotted().defaultBlockState().is(NetherDescentBlocks.SYTHIAN_STALK.get()) && NetherDescentWoodSet.woodsets().stream().noneMatch(netherDescentWoodSet -> netherDescentWoodSet.growerItem().getBlockState().is(flowerPotBlock.getPotted())))
				blockModels.createTrivialBlock(flowerPotBlock, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.FLOWER_POT_CROSS.extend().renderType(mcLocation("cutout")).build()).updateTexture(textureMapping -> textureMapping.put(TextureSlot.PLANT, ModelLocationUtils.getModelLocation(flowerPotBlock.getPotted()))));
		});

		blockModels.createTrivialBlock(NetherDescentBlocks.WAILING_NYLIUM.get(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping -> textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.WAILING_NYLIUM.get())).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.SOUL_SAND))));
		blockItemModel(blockModels, NetherDescentBlocks.WAILING_NYLIUM.get());
		blockModels.createTrivialBlock(NetherDescentBlocks.EMBUR_NYLIUM.get(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping -> textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.EMBUR_NYLIUM.get())).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(NetherDescentBlocks.BLUE_NETHERRACK.get()))));
		blockItemModel(blockModels, NetherDescentBlocks.EMBUR_NYLIUM.get());
		blockModels.createTrivialBlock(NetherDescentBlocks.SYTHIAN_NYLIUM.get(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping -> textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_NYLIUM.get())).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.NETHERRACK))));
		blockItemModel(blockModels, NetherDescentBlocks.SYTHIAN_NYLIUM.get());
		blockModels.createTrivialBlock(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping -> textureMapping.put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.CRIMSON_NYLIUM)).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.BLACKSTONE, "_top"))));
		blockItemModel(blockModels, NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get());

		blockModels.blockStateOutput.accept(BlockModelGenerators.createFence(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase())), blockModels.modelOutput)), ModelTemplates.FENCE_SIDE.create(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase())), blockModels.modelOutput)));
		itemModels.itemModelOutput.accept(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().asItem(), ItemModelUtils.plainModel(ModelTemplates.FENCE_INVENTORY.create(NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get(), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase())), itemModels.modelOutput)));

		//Arisian
		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(NetherDescentBlocks.ARISIAN_LEAVES.get())
				.with(PropertyDispatch.initial(ArisianLeavesBlock.LIT)
						.select(true, BlockModelGenerators.plainVariant(ModelTemplates.LEAVES.extend().renderType(mcLocation("cutout_mipped")).build().createWithSuffix(NetherDescentBlocks.ARISIAN_LEAVES.get(), "_lit", new TextureMapping().put(TextureSlot.ALL, NetherDescent.id("block/arisian/leaves_lit")), blockModels.modelOutput)))
						.select(false, BlockModelGenerators.plainVariant(ModelTemplates.LEAVES.extend().renderType(mcLocation("cutout_mipped")).build().create(NetherDescentBlocks.ARISIAN_LEAVES.get(), new TextureMapping().put(TextureSlot.ALL, NetherDescent.id("block/arisian/leaves")), blockModels.modelOutput)))));
		blockItemModel(blockModels, NetherDescentBlocks.ARISIAN_LEAVES.get());

		blockModels.blockStateOutput
				.accept(
						MultiVariantGenerator.multiVariant(NetherDescentBlocks.ARISIAN_BRANCH.get(), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CORAL_WALL_FAN.extend().renderType(mcLocation("cutout_mipped")).build().create(NetherDescentBlocks.ARISIAN_BRANCH.get(), TextureMapping.fan(NetherDescentBlocks.ARISIAN_BRANCH.get()), blockModels.modelOutput)))
								.with(
										PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
												.select(Direction.NORTH, Variant.variant())
												.select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
												.select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
												.select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
								)
				);
		layer0(itemModels, NetherDescentBlocks.ARISIAN_BRANCH.get());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get(),
				BlockModelGenerators.variants(BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get())), BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get(), "2")))));
		blockModels.registerSimpleItemModel(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get(), ModelTemplates.CUBE_ALL.create(TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get(), "_item"), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get())), blockModels.modelOutput));
		createHeadBlock(blockModels, itemModels, NetherDescentBlocks.ARISIAN_TANGLE_ROOTS.get(), NetherDescentBlocks.ARISIAN_TANGLE_ROOTS_PLANT.get());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.ARISIAN_DANDELIONS.get())
				.with(PropertyDispatch.property(HangingNDBushBlock.HANGING)
						.select(false, Stream.of(
										BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_DANDELIONS.get())),
										BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_DANDELIONS.get(), "2"))
								)
								.flatMap(Arrays::stream)
								.toList())
						.select(true, Stream.of(
										createHangingRotatedVariants(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_DANDELIONS.get())),
										createHangingRotatedVariants(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_DANDELIONS.get(), "2"))
								)
								.flatMap(Arrays::stream)
								.toList())));
		createHangingCrossBlock(blockModels, itemModels, NetherDescentBlocks.ARISIAN_SPROUTS.get());

		basicItem(itemModels, NetherDescentBlocks.ARISIAN_DANDELIONS.get().asItem());

		Identifier arisianBlossom = ModelTemplates.CROSS.extend().renderType(mcLocation("cutout")).build().create(NetherDescentBlocks.ARISIAN_BLOSSOM.get(), TextureMapping.cross(NetherDescentBlocks.ARISIAN_BLOSSOM.get()), blockModels.modelOutput);
		Identifier arisianBlossomLit = ModelTemplates.CROSS.extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_BLOSSOM.get(), "_lit"), TextureMapping.cross(TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_BLOSSOM.get(), "_lit")), blockModels.modelOutput);
		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(NetherDescentBlocks.ARISIAN_BLOSSOM.get())
				.with(PropertyDispatch.initial(ArisianBlossomBlock.LIT, ArisianBlossomBlock.HANGING)
						.select(false, false, BlockModelGenerators.plainVariant(arisianBlossom))
						.select(true, false, BlockModelGenerators.plainVariant(arisianBlossomLit))
						.select(false, true, BlockModelGenerators.plainVariant(arisianBlossom).with(BlockModelGenerators.X_ROT_180))
						.select(true, true, BlockModelGenerators.plainVariant(arisianBlossomLit).with(BlockModelGenerators.X_ROT_180))));
		layer0(itemModels, NetherDescentBlocks.ARISIAN_BLOSSOM.get());

		ModelTemplates.CARPET.extend().renderType(mcLocation("cutout")).build().create(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), TextureMapping.wool(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get()), blockModels.modelOutput);
		ModelTemplates.MOSSY_CARPET_SIDE.extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small"), new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small")), blockModels.modelOutput);
		ModelTemplates.MOSSY_CARPET_SIDE.extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_tall"), new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_tall")), blockModels.modelOutput);
		ModelTemplates.create("netherdescent:mossy_carpet_side_hanging", TextureSlot.SIDE).extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small_hanging"), new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small")), blockModels.modelOutput);
		ModelTemplates.create("netherdescent:mossy_carpet_side_hanging", TextureSlot.SIDE).extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_tall_hanging"), new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_tall")), blockModels.modelOutput);
		blockItemModel(blockModels, NetherDescentBlocks.ARISIAN_MOSS_CARPET.get());

		Identifier hangingDansTop = ModelTemplates.create("netherdescent:empty").create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_ARISIAN_DANDELIONS.get(), "_top"), new TextureMapping().putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_DANDELIONS.get())), blockModels.modelOutput);
		Identifier hangingDansBottom = ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_ARISIAN_DANDELIONS.get());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(NetherDescentBlocks.TALL_ARISIAN_DANDELIONS.get())
				.with(PropertyDispatch.initial(HangingDoublePlantBlock.HANGING, HangingDoublePlantBlock.HALF)
						.select(false, DoubleBlockHalf.LOWER, BlockModelGenerators.plainVariant(hangingDansBottom))
						.select(false, DoubleBlockHalf.UPPER, BlockModelGenerators.plainVariant(hangingDansTop))
						.select(true, DoubleBlockHalf.LOWER, BlockModelGenerators.plainVariant(hangingDansBottom).with(BlockModelGenerators.X_ROT_180))
						.select(true, DoubleBlockHalf.UPPER, BlockModelGenerators.plainVariant(hangingDansTop).with(BlockModelGenerators.X_ROT_180))));
		layer0(itemModels, NetherDescentBlocks.TALL_ARISIAN_DANDELIONS.get(), ModelLocationUtils.getModelLocation(NetherDescentBlocks.ARISIAN_DANDELIONS.get().asItem()));

		Identifier hangingSproutsTop = ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_ARISIAN_SPROUTS.get(), "_top"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small")), blockModels.modelOutput);
		Identifier hangingSproutsBottom = ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_ARISIAN_SPROUTS.get(), "_bottom"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_tall")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_tall")), blockModels.modelOutput);

		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(NetherDescentBlocks.TALL_ARISIAN_SPROUTS.get())
				.with(PropertyDispatch.initial(HangingDoublePlantBlock.HANGING, HangingDoublePlantBlock.HALF)
						.select(false, DoubleBlockHalf.LOWER, BlockModelGenerators.plainVariant(hangingSproutsBottom))
						.select(false, DoubleBlockHalf.UPPER, BlockModelGenerators.plainVariant(hangingSproutsTop))
						.select(true, DoubleBlockHalf.LOWER, BlockModelGenerators.plainVariant(hangingSproutsBottom).with(BlockModelGenerators.X_ROT_180))
						.select(true, DoubleBlockHalf.UPPER, BlockModelGenerators.plainVariant(hangingSproutsTop).with(BlockModelGenerators.X_ROT_180))));

		layer0(itemModels, NetherDescentBlocks.TALL_ARISIAN_SPROUTS.get(), TextureMapping.getBlockTexture(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), "_side_small"));

		//Embur
		createHeadBlock(blockModels, itemModels, NetherDescentBlocks.EMBUR_GEL_VINES.get(), NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get());
		createCrossBlock(blockModels, itemModels, NetherDescentBlocks.EMBUR_SPROUTS.get(), "cutout");
		createCrossBlock(blockModels, itemModels, NetherDescentBlocks.EMBUR_ROOTS.get(), "cutout_mipped");

		ModelTemplates.create("glow_lichen").extend().renderType(mcLocation("cutout")).build()
				.create(NetherDescentBlocks.EMBUR_CAVE_MOSS.get(), new TextureMapping().putForced(TextureSlot.create("glow_lichen"), TextureMapping.getBlockTexture(NetherDescentBlocks.EMBUR_CAVE_MOSS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.EMBUR_CAVE_MOSS.get())), blockModels.modelOutput);
		layer0(itemModels, NetherDescentBlocks.EMBUR_CAVE_MOSS.get());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(NetherDescentBlocks.EMBUR_HANGING_MOSS.get())
				.with(PropertyDispatch.initial(HangingMossBlock.TIP)
						.select(false, BlockModelGenerators.plainVariant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(NetherDescentBlocks.EMBUR_HANGING_MOSS.get(), TextureMapping.cross(NetherDescentBlocks.EMBUR_HANGING_MOSS.get()), blockModels.modelOutput)))
						.select(true, BlockModelGenerators.plainVariant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.EMBUR_HANGING_MOSS.get(), "_tip"), TextureMapping.cross(TextureMapping.getBlockTexture(NetherDescentBlocks.EMBUR_HANGING_MOSS.get(), "_tip")), blockModels.modelOutput)))));
		layer0(itemModels, NetherDescentBlocks.EMBUR_HANGING_MOSS.get());

		ModelTemplates.CARPET.extend().renderType(mcLocation("cutout")).build().create(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), TextureMapping.wool(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()), blockModels.modelOutput);
		ModelTemplates.MOSSY_CARPET_SIDE.extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), "_side_small"), new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), "_side_small")), blockModels.modelOutput);
		ModelTemplates.MOSSY_CARPET_SIDE.extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), "_side_tall"), new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), "_side_tall")), blockModels.modelOutput);
		blockItemModel(blockModels, NetherDescentBlocks.EMBUR_MOSS_CARPET.get());

		layer0(itemModels, NetherDescentBlocks.EMBUR_LILY.get());
		blockModels.createTrivialBlock(NetherDescentBlocks.EMBUR_GEL_BLOCK.get(), TexturedModel.CUBE.updateTemplate(template -> template.extend().renderType(mcLocation("translucent")).build()));
		blockItemModel(blockModels, NetherDescentBlocks.EMBUR_GEL_BLOCK.get());

		blockModels.createTrivialBlock(NetherDescentBlocks.HORNET_NEST.get(), TexturedModel.CUBE_TOP_BOTTOM.updateTexture(textureMapping -> textureMapping.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(NetherDescentBlocks.HORNET_NEST.get(), "_bottom")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.HORNET_NEST.get(), "_side")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.HORNET_NEST.get(), "_top"))));
		blockItemModel(blockModels, NetherDescentBlocks.HORNET_NEST.get());

		blockModels.createDoubleBlock(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(),
				BlockModelGenerators.plainVariant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_top"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_top")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_top")), blockModels.modelOutput)),
				BlockModelGenerators.plainVariant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_bottom"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_bottom")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_bottom")), blockModels.modelOutput)));
		layer0(itemModels, NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_EMBUR_ROOTS.get(), "_top"));

		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(
				NetherDescentBlocks.EMBUR_LILY.get(),
				ArrayUtils.addAll(
						BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(NetherDescentBlocks.EMBUR_LILY.get())),
						BlockModelGenerators.createRotatedVariants(ModelLocationUtils.getModelLocation(NetherDescentBlocks.EMBUR_LILY.get(), "2"))
				)
		));

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get())
				.with(PropertyDispatch.property(WailingBulbBlossomBlock.ACTIVE)
						.select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("netherdescent:wailing_bulb_blossom").extend().renderType(mcLocation("cutout")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(), "_active"),
								new TextureMapping()
										.putForced(TextureSlot.create("0"), TextureMapping.getBlockTexture(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(), "_petal_active"))
										.putForced(TextureSlot.create("1"), TextureMapping.getBlockTexture(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(), "_side_active"))
										.putForced(TextureSlot.create("2"), TextureMapping.getBlockTexture(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(), "_top_active"))
										.putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(), "_side_active")),
								blockModels.modelOutput)))
						.select(false, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get())))));

		itemModels.itemModelOutput.accept(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get().asItem(), ItemModelUtils.plainModel(modLocation("item/wailing_bulb_blossom")));

		//Sythian
		createHeadBlock(blockModels, itemModels, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS.get(), NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.SYTHIAN_FARMLAND.get())
				.with(PropertyDispatch.property(SythianFarmBlock.MOSSY)
						.select(false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.FARMLAND.create(NetherDescentBlocks.SYTHIAN_FARMLAND.get(), new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SOIL.get())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_FARMLAND.get())), blockModels.modelOutput)))
						.select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.FARMLAND.create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_FARMLAND.get(), "_mossy"), new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SOIL.get())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_FARMLAND.get(), "_mossy")), blockModels.modelOutput)))));
		blockItemModel(blockModels, NetherDescentBlocks.SYTHIAN_FARMLAND.get());

		createCrossBlock(blockModels, itemModels, NetherDescentBlocks.SYTHIAN_SPROUTS.get(), "cutout");

		Identifier sythianRoots = ModelTemplates.CROSS.extend().renderType(mcLocation("cutout_mipped")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_ROOTS.get()), TextureMapping.cross(NetherDescentBlocks.SYTHIAN_ROOTS.get()), blockModels.modelOutput);
		Identifier sythainRootsWall = ModelTemplates.CORAL_WALL_FAN.extend().renderType(mcLocation("cutout_mipped")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_ROOTS.get(), "_wall"), TextureMapping.fan(NetherDescentBlocks.SYTHIAN_ROOTS.get()), blockModels.modelOutput);

		blockModels.blockStateOutput
				.accept(
						MultiVariantGenerator.multiVariant(NetherDescentBlocks.SYTHIAN_ROOTS.get())
								.with(
										PropertyDispatch.property(BlockStateProperties.FACING)
												.select(Direction.UP, Variant.variant().with(VariantProperties.MODEL, sythianRoots))
												.select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, sythianRoots))
												.select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, sythainRootsWall))
												.select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, sythainRootsWall).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
												.select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, sythainRootsWall).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
												.select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, sythainRootsWall).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
								)
				);
		layer0(itemModels, NetherDescentBlocks.SYTHIAN_ROOTS.get());

		blockModels.blockStateOutput
				.accept(
						MultiPartGenerator.multiPart(NetherDescentBlocks.SYTHIAN_STALK.get())
								.with(Condition.condition().term(BlockStateProperties.AGE_1, 0), this.createSythianStalkModels(blockModels, 0))
								.with(Condition.condition().term(BlockStateProperties.AGE_1, 1), this.createSythianStalkModels(blockModels, 1))
								.with(
										Condition.condition().term(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.SMALL),
										Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("bamboo_small_leaves").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_STALK.get(), "_small_leaves"), new TextureMapping().putForced(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get(), "_small_leaves")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get(), "_small_leaves")), blockModels.modelOutput))
								)
								.with(
										Condition.condition().term(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.LARGE),
										Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("bamboo_large_leaves").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_STALK.get(), "_large_leaves"), new TextureMapping().putForced(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get(), "_large_leaves")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get(), "_large_leaves")), blockModels.modelOutput))
								)
				);

		ModelTemplates.create("potted_bamboo").extend().renderType("cutout").build().create(NetherDescentBlocks.SYTHIAN_STALK.getPottedBlock(),
				new TextureMapping()
						.putForced(TextureSlot.create("bamboo"), TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get()))
						.putForced(TextureSlot.DIRT, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_NYLIUM.get()))
						.putForced(TextureSlot.create("flowerpot"), TextureMapping.getBlockTexture(Blocks.FLOWER_POT))
						.putForced(TextureSlot.create("leaf"), TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get(), "_small_leaves"))
						.putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.FLOWER_POT))
				, blockModels.modelOutput);

		blockModels.createNonTemplateModelBlock(NetherDescentBlocks.SYTHIAN_STALK.getPottedBlock());

		Identifier model = ModelTemplates.TINTED_CROSS.extend().renderType(mcLocation("cutout")).build().create(NetherDescentBlocks.SYTHIAN_SHOOT.get(), TextureMapping.cross(TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get(), "_stage0")), blockModels.modelOutput);
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.SYTHIAN_SHOOT.get())
				.with(PropertyDispatch.property(SythianShootBlock.HANGING)
						.select(false, Variant.variant().with(VariantProperties.MODEL, model))
						.select(true, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.MODEL, model))));
		basicItem(itemModels, NetherDescentBlocks.SYTHIAN_STALK.getItem());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get())
				.with(PropertyDispatch.property(SythianScaffoldingBlock.BOTTOM)
						.select(false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("scaffolding_stable").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_stable"), new TextureMapping()
								.putForced(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_bottom"))
								.putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_top"))
								.putForced(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_side"))
								.putForced(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_top")), blockModels.modelOutput)))
						.select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("scaffolding_unstable").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_unstable"), new TextureMapping()
								.putForced(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_bottom"))
								.putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_top"))
								.putForced(TextureSlot.SIDE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_side"))
								.putForced(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_top")), blockModels.modelOutput)))));
		blockModels.registerSimpleItemModel(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_SCAFFOLDING.get(), "_stable"));

		//Wailing
		createHeadBlock(blockModels, itemModels, NetherDescentBlocks.WAILING_VINES.get(), NetherDescentBlocks.WAILING_VINES_PLANT.get());
		blockModels.blockStateOutput.accept(
				MultiVariantGenerator.multiVariant(NetherDescentBlocks.WAILING_GRASS.get(),
						Variant.variant().with(
								VariantProperties.MODEL, ModelTemplates.CROSS.extend().renderType(mcLocation("cutout_mipped")).build().create(NetherDescentBlocks.WAILING_GRASS.get(), TextureMapping.cross(NetherDescentBlocks.WAILING_GRASS.get()), blockModels.modelOutput)),
						Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CORAL_FAN.extend().renderType(mcLocation("cutout_mipped")).build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.WAILING_GRASS.get(), "1"), TextureMapping.fan(NetherDescentBlocks.WAILING_GRASS.get()), blockModels.modelOutput))));
		layer0(itemModels, NetherDescentBlocks.WAILING_GRASS.get());

		//Pendorite
		createSlabAndStairs(blockModels, itemModels, NetherDescentBlocks.CUT_PENDORITE_SLAB.get(), NetherDescentBlocks.CUT_PENDORITE_STAIRS.get(), NetherDescentBlocks.CUT_PENDORITE.get(), new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.CUT_PENDORITE.get())));
		TextureMapping door = new TextureMapping().put(TextureSlot.TOP, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_DOOR.get(), "_top")).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_DOOR.get(), "_bottom"));
		blockModels.createTrivialBlock(NetherDescentBlocks.PENDORITE_GRATE.get(), TexturedModel.CUBE.updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
		blockItemModel(blockModels, NetherDescentBlocks.PENDORITE_GRATE.get());
		blockModels.blockStateOutput.accept(BlockModelGenerators.createDoor(NetherDescentBlocks.PENDORITE_DOOR.get(),
				ModelTemplates.DOOR_BOTTOM_LEFT.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_BOTTOM_RIGHT.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_TOP_LEFT.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_TOP_RIGHT.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput),
				ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_DOOR.get(), door, blockModels.modelOutput)));
		basicItem(itemModels, NetherDescentBlocks.PENDORITE_DOOR.get().asItem());

		TextureMapping trapdoor = TextureMapping.defaultTexture(TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_TRAPDOOR.get()));
		Identifier trapdoorBottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_TRAPDOOR.get(), trapdoor, blockModels.modelOutput);
		blockModels.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(NetherDescentBlocks.PENDORITE_TRAPDOOR.get(),
				ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_TRAPDOOR.get(), trapdoor, blockModels.modelOutput),
				trapdoorBottom,
				ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType(mcLocation("translucent")).build().create(NetherDescentBlocks.PENDORITE_TRAPDOOR.get(), trapdoor, blockModels.modelOutput)));

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.PENDORITE_LANTERN.get())
				.with(PropertyDispatch.property(LanternBlock.HANGING)
						.select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.LANTERN.extend().renderType(mcLocation("cutout")).build().create(NetherDescentBlocks.PENDORITE_LANTERN.get(), new TextureMapping().put(TextureSlot.LANTERN, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_LANTERN.get())), blockModels.modelOutput)))
						.select(false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.HANGING_LANTERN.extend().renderType(mcLocation("cutout")).build().create(NetherDescentBlocks.PENDORITE_LANTERN.get(), new TextureMapping().put(TextureSlot.LANTERN, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_LANTERN.get())), blockModels.modelOutput)))));
		basicItem(itemModels, NetherDescentBlocks.PENDORITE_LANTERN.get().asItem());

		Identifier chain = ModelTemplates.create("chain").extend().renderType(mcLocation("cutout_mipped")).build().create(NetherDescentBlocks.PENDORITE_CHAIN.get(), new TextureMapping().putForced(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_CHAIN.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_CHAIN.get())), blockModels.modelOutput);
		blockModels.createAxisAlignedPillarBlockCustomModel(NetherDescentBlocks.PENDORITE_CHAIN.get(), chain);
		basicItem(itemModels, NetherDescentBlocks.PENDORITE_CHAIN.get().asItem());

		blockModels.blockStateOutput
				.accept(
						MultiPartGenerator.multiPart(NetherDescentBlocks.PENDORITE_BARS.get())
								.with(Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("iron_bars_post_ends").extend().renderType("cutout_mipped").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_post_ends"), new TextureMapping().putForced(TextureSlot.EDGE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())), blockModels.modelOutput)))
								.with(
										Condition.condition()
												.term(BlockStateProperties.NORTH, false)
												.term(BlockStateProperties.EAST, false)
												.term(BlockStateProperties.SOUTH, false)
												.term(BlockStateProperties.WEST, false),
										Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("iron_bars_post").extend().renderType("cutout_mipped").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_post"), new TextureMapping().putForced(TextureSlot.EDGE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())), blockModels.modelOutput))
								)
								.with(
										Condition.condition()
												.term(BlockStateProperties.NORTH, true)
												.term(BlockStateProperties.EAST, false)
												.term(BlockStateProperties.SOUTH, false)
												.term(BlockStateProperties.WEST, false),
										Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("iron_bars_cap").extend().renderType("cutout_mipped").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_cap"), new TextureMapping().putForced(TextureSlot.EDGE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.create("bars"), TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())), blockModels.modelOutput))
								)
								.with(
										Condition.condition()
												.term(BlockStateProperties.NORTH, false)
												.term(BlockStateProperties.EAST, true)
												.term(BlockStateProperties.SOUTH, false)
												.term(BlockStateProperties.WEST, false),
										Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_cap")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
								)
								.with(
										Condition.condition()
												.term(BlockStateProperties.NORTH, false)
												.term(BlockStateProperties.EAST, false)
												.term(BlockStateProperties.SOUTH, true)
												.term(BlockStateProperties.WEST, false),
										Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("iron_bars_cap_alt").extend().renderType("cutout_mipped").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_cap_alt"), new TextureMapping().putForced(TextureSlot.EDGE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.create("bars"), TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())), blockModels.modelOutput))
								)
								.with(
										Condition.condition()
												.term(BlockStateProperties.NORTH, false)
												.term(BlockStateProperties.EAST, false)
												.term(BlockStateProperties.SOUTH, false)
												.term(BlockStateProperties.WEST, true),
										Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_cap_alt")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
								)
								.with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("iron_bars_side").extend().renderType("cutout_mipped").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_side"), new TextureMapping().putForced(TextureSlot.EDGE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.create("bars"), TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())), blockModels.modelOutput)))
								.with(
										Condition.condition().term(BlockStateProperties.EAST, true),
										Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_side")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
								)
								.with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("iron_bars_side_alt").extend().renderType("cutout_mipped").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_side_alt"), new TextureMapping().putForced(TextureSlot.EDGE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())).putForced(TextureSlot.create("bars"), TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_BARS.get())), blockModels.modelOutput)))
								.with(
										Condition.condition().term(BlockStateProperties.WEST, true),
										Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_BARS.get(), "_side_alt")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
								)
				);
		layer0(itemModels, NetherDescentBlocks.PENDORITE_BARS.get());

		blockModels.itemModelOutput.accept(NetherDescentBlocks.PENDORITE_TRAPDOOR.get().asItem(), ItemModelUtils.plainModel(trapdoorBottom));

		blockModels.createNormalTorch(NetherDescentBlocks.PENDORITE_TORCH.get(), NetherDescentBlocks.PENDORITE_WALL_TORCH.get());

		itemModels.generateWolfArmor(NetherDescentItems.PENDORITE_WOLF_ARMOR.get());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.PENDORITE_CAMPFIRE.get())
						.with(BlockModelGenerators.createHorizontalFacingDispatch())
				.with(PropertyDispatch.property(CampfireBlock.LIT)
						.select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAMPFIRE.extend().renderType(mcLocation("cutout")).build()
								.create(NetherDescentBlocks.PENDORITE_CAMPFIRE.get(), new TextureMapping().put(TextureSlot.FIRE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_CAMPFIRE.get(), "_fire")).put(TextureSlot.LIT_LOG, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_CAMPFIRE.get(), "_log_lit")), blockModels.modelOutput)))
						.select(false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("campfire_off").extend().renderType(mcLocation("cutout")).build()
								.create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.PENDORITE_CAMPFIRE.get(), "_off"), new TextureMapping(), blockModels.modelOutput)))));
		basicItem(itemModels, NetherDescentBlocks.PENDORITE_CAMPFIRE.get().asItem());


		//Crimson
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.CRIMSON_CARPET.get(),
				Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.CRIMSON_CARPET.get())),
				Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.CRIMSON_CARPET.get(), "2"))));
		blockItemModel(blockModels, NetherDescentBlocks.CRIMSON_CARPET.get());
		blockModels.createDoubleBlock(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(),
				ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_top"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_top")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_top")), blockModels.modelOutput),
				ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_bottom"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_bottom")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_bottom")), blockModels.modelOutput));

		layer0(itemModels, NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_ROOTS.get(), "_top"));

		blockModels.createDoubleBlock(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(),
				ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_top"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_top")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_top")), blockModels.modelOutput),
				ModelTemplates.CROSS.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_bottom"), new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_bottom")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_bottom")), blockModels.modelOutput));

		layer0(itemModels, NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), TextureMapping.getBlockTexture(NetherDescentBlocks.TALL_CRIMSON_FUNGI.get(), "_top"));

		blockModels.blockStateOutput
				.accept(
						MultiVariantGenerator.multiVariant(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get())
								.with(
										PropertyDispatch.property(BlockStateProperties.AGE_3)
												.generate(
														integer -> Variant.variant()
																.with(VariantProperties.MODEL, blockModels.createSuffixedVariant(NetherDescentBlocks.CRIMSON_BERRY_BUSH.get(), "_stage" + integer, ModelTemplates.CROSS.extend().renderType("cutout").build(), TextureMapping::cross))
												)
								)
				);

		ModelTemplates.create("end_rod").extend().renderType("cutout").build().create(NetherDescentBlocks.BLAZE_FIRE_ROD.get(), new TextureMapping().putForced(TextureSlot.create("end_rod"), TextureMapping.getBlockTexture(NetherDescentBlocks.BLAZE_FIRE_ROD.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.BLAZE_FIRE_ROD.get())), blockModels.modelOutput);
		blockModels.createRotatableColumn(NetherDescentBlocks.BLAZE_FIRE_ROD.get());
		blockItemModel(blockModels, NetherDescentBlocks.BLAZE_FIRE_ROD.get());
		ModelTemplates.create("end_rod").extend().renderType("cutout").build().create(NetherDescentBlocks.PENDORITE_FIRE_ROD.get(), new TextureMapping().putForced(TextureSlot.create("end_rod"), TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_FIRE_ROD.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.PENDORITE_FIRE_ROD.get())), blockModels.modelOutput);
		blockModels.createRotatableColumn(NetherDescentBlocks.PENDORITE_FIRE_ROD.get());
		blockItemModel(blockModels, NetherDescentBlocks.PENDORITE_FIRE_ROD.get());
		ModelTemplates.create("end_rod").extend().renderType("cutout").build().create(NetherDescentBlocks.SOUL_FIRE_ROD.get(), new TextureMapping().putForced(TextureSlot.create("end_rod"), TextureMapping.getBlockTexture(NetherDescentBlocks.SOUL_FIRE_ROD.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.SOUL_FIRE_ROD.get())), blockModels.modelOutput);
		blockModels.createRotatableColumn(NetherDescentBlocks.SOUL_FIRE_ROD.get());
		blockItemModel(blockModels, NetherDescentBlocks.SOUL_FIRE_ROD.get());

		NetherDescentItems.SIMPLE_ITEMS.forEach(item -> basicItem(itemModels, item.get()));
		basicItem(itemModels, NetherDescentBlocks.FUNGAL_BULBS.get().asItem());

		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(NetherDescentBlocks.THORN_SPROUT.get())
				.with(BlockModelGenerators.createHorizontalFacingDispatch())
				.with(PropertyDispatch.properties(ThornSproutBlock.SEGMENT, ThornSproutBlock.FLOWERING)
						.select(ThornSproutBlock.SegmentType.BASE, false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("netherdescent:thorn_sprout").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.THORN_SPROUT.get(), "_base"), new TextureMapping().putForced(TextureSlot.create("1"), TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_base")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_base")), blockModels.modelOutput)))
						.select(ThornSproutBlock.SegmentType.BASE, true, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.THORN_SPROUT.get(), "_base")))
						.select(ThornSproutBlock.SegmentType.END, false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("netherdescent:thorn_sprout").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.THORN_SPROUT.get(), "_end"), new TextureMapping().putForced(TextureSlot.create("1"), TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_end")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_end")), blockModels.modelOutput)))
						.select(ThornSproutBlock.SegmentType.END, true, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(NetherDescentBlocks.THORN_SPROUT.get(), "_end")))
						.select(ThornSproutBlock.SegmentType.MIDDLE, false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("netherdescent:thorn_sprout").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.THORN_SPROUT.get(), "_middle"), new TextureMapping().putForced(TextureSlot.create("1"), TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_middle")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_middle")), blockModels.modelOutput)))
						.select(ThornSproutBlock.SegmentType.MIDDLE, true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("netherdescent:thorn_sprout").extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.THORN_SPROUT.get(), "_middle_flowering"), new TextureMapping().putForced(TextureSlot.create("1"), TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_middle_flowering")).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_middle_flowering")), blockModels.modelOutput)))));
		layer0(itemModels, NetherDescentBlocks.THORN_SPROUT.get(), TextureMapping.getBlockTexture(NetherDescentBlocks.THORN_SPROUT.get(), "_end"));

		itemModels.generateSpawnEgg(NetherDescentItems.HORNET_SPAWN_EGG.get(), ARGB.color(150, 87, 146), ARGB.color(243, 158, 96));
		itemModels.generateSpawnEgg(NetherDescentItems.PENDORITE_BLAZE_SPAWN_EGG.get(), ARGB.color(56, 36, 107), ARGB.color(151, 171, 230));
		itemModels.generateSpawnEgg(NetherDescentItems.SOUL_BLAZE_SPAWN_EGG.get(), ARGB.color(37, 167, 172), ARGB.color(212, 254, 255));
		itemModels.generateSpawnEgg(NetherDescentItems.SOUL_GHAST_SPAWN_EGG.get(), ARGB.color(9, 86, 86), ARGB.color(50, 161, 161));
	}

	private void createHeadBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block, Block body) {
		blockModels.createTrivialBlock(block, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, TextureMapping.getBlockTexture(block))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
		blockModels.createTrivialBlock(body, TexturedModel.createDefault(TextureMapping::cross, ModelTemplates.CROSS).updateTexture(textureMapping -> textureMapping.put(TextureSlot.CROSS, TextureMapping.getBlockTexture(body))).updateTemplate(template -> template.extend().renderType(mcLocation("cutout")).build()));
		itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(block.asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(body)), itemModels.modelOutput)));
	}

	private void createHangingCrossBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
		Identifier model = ModelTemplates.CROSS.extend().renderType(mcLocation("cutout")).build().create(block, TextureMapping.cross(block), blockModels.modelOutput);
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
				.with(PropertyDispatch.property(HangingNDBushBlock.HANGING)
						.select(false, Variant.variant().with(VariantProperties.MODEL, model))
						.select(true, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.MODEL, model))));
		layer0(itemModels, block);
	}

	private void createCrossBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block, String renderType) {
		blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelTemplates.CROSS.extend().renderType(mcLocation(renderType)).build().create(block, TextureMapping.cross(block), blockModels.modelOutput)));
		layer0(itemModels, block);
	}

	private void createNetherrack(BlockModelGenerators blockModels, Block block) {
		Identifier Identifier = TexturedModel.CUBE.create(block, blockModels.modelOutput);
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, Identifier), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, Identifier).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270)));
		blockItemModel(blockModels, block);
	}

	private static void createSlab(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block baseBlock, TextureMapping baseTexture) {
		Identifier slabBottom = ModelTemplates.SLAB_BOTTOM.create(slab, baseTexture, blockModels.modelOutput);
		Identifier slabTop = ModelTemplates.SLAB_TOP.create(slab, baseTexture, blockModels.modelOutput);
		blockModels.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, slabBottom, slabTop, ModelLocationUtils.getModelLocation(baseBlock)));
		itemModels.itemModelOutput.accept(slab.asItem(), ItemModelUtils.plainModel(slabBottom));
	}

	private static void createStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block stairs, TextureMapping baseTexture) {
		Identifier stairsStraight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, baseTexture, blockModels.modelOutput);
		Identifier stairsInner = ModelTemplates.STAIRS_INNER.create(stairs, baseTexture, blockModels.modelOutput);
		Identifier stairsOuter = ModelTemplates.STAIRS_OUTER.create(stairs, baseTexture, blockModels.modelOutput);
		blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, stairsInner, stairsStraight, stairsOuter));
		itemModels.itemModelOutput.accept(stairs.asItem(), ItemModelUtils.plainModel(stairsStraight));
	}

	private static void createSlabAndStairs(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block slab, Block stairs, Block baseBlock, TextureMapping baseTexture) {
		createSlab(blockModels, itemModels, slab, baseBlock, baseTexture);
		createStairs(blockModels, itemModels, stairs, baseTexture);
	}

	private static void createWall(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block wall, Block baseBlock) {
		TextureMapping base = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(baseBlock));
		Identifier wallPost = ModelTemplates.WALL_POST.create(wall, base, blockModels.modelOutput);
		Identifier wallSide = ModelTemplates.WALL_LOW_SIDE.create(wall, base, blockModels.modelOutput);
		Identifier wallSideTall = ModelTemplates.WALL_TALL_SIDE.create(wall, base, blockModels.modelOutput);
		Identifier wallInventory = ModelTemplates.WALL_INVENTORY.create(wall, base, blockModels.modelOutput);
		blockModels.blockStateOutput.accept(BlockModelGenerators.createWall(wall, wallPost, wallSide, wallSideTall));
		itemModels.itemModelOutput.accept(wall.asItem(), ItemModelUtils.plainModel(wallInventory));
	}
	protected List<Variant> createSythianStalkModels(BlockModelGenerators blockModels, int age) {
		String string = "_age" + age;
		return IntStream.range(1, 5)// ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_STALK.get(), i + string)
				.mapToObj(i -> Variant.variant().with(VariantProperties.MODEL, ModelTemplates.create("bamboo" + i + string).extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(NetherDescentBlocks.SYTHIAN_STALK.get(), i + string), new TextureMapping().putForced(TextureSlot.ALL, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get())).putForced(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(NetherDescentBlocks.SYTHIAN_STALK.get())), blockModels.modelOutput)))
				.collect(Collectors.toList());
	}

	public static Variant[] createHangingRotatedVariants(Identifier modelLocation) {
		return new Variant[]{Variant.variant().with(VariantProperties.MODEL, modelLocation), Variant.variant().with(VariantProperties.MODEL, modelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, modelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, modelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)};
	}

	private void blockItemModel(BlockModelGenerators blockModels, Block block) {
		blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	private void basicItem(ItemModelGenerators itemModels, Item item) {
		itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
	}

	private void layer0(ItemModelGenerators itemModels, Block block) {
		itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(block.asItem(), TextureMapping.layer0(TextureMapping.getBlockTexture(block)), itemModels.modelOutput)));
	}

	private void layer0(ItemModelGenerators itemModels, Block block, Identifier location) {
		itemModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(block.asItem(), TextureMapping.layer0(location), itemModels.modelOutput)));
	}

	@Override
	protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
		return Stream.empty();
	}

	@Override
	protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
		return Stream.empty();
	}
}
