package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jspecify.annotations.NonNull;

public class ModelGenerators extends ModelProvider {
	public ModelGenerators(PackOutput output) {
		super(output, NetherDescent.MOD_ID);
	}

	@Override
	protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
		NetherDescentBlocks.cubeAllBlocks.forEach(block -> {
			blockModels.createTrivialCube(block.get());
			blockItemModel(blockModels, block.get());
		});
		NetherDescentItems.SIMPLE_ITEMS.forEach(item -> basicItem(itemModels, item.get()));
	}

	private void blockItemModel(BlockModelGenerators blockModels, Block block) {
		blockModels.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	private void basicItem(ItemModelGenerators itemModels, Item item) {
		itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
	}
}
