package net.potionstudios.netherdescent.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HornetNestBlockEntity extends BlockEntity {
	public HornetNestBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.HORNET_NEST.get(), pos, blockState);
	}
}
