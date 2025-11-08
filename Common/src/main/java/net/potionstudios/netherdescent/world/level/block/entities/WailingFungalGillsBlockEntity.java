package net.potionstudios.netherdescent.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WailingFungalGillsBlockEntity extends BlockEntity {
	public WailingFungalGillsBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.WAILING_FUNGAL_GILLS.get(), pos, blockState);
	}
}
