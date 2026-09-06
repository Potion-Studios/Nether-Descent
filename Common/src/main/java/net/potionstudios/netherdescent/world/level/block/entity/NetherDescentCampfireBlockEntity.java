package net.potionstudios.netherdescent.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class NetherDescentCampfireBlockEntity extends CampfireBlockEntity {
	public NetherDescentCampfireBlockEntity(BlockPos pos, BlockState blockState) {
		super(pos, blockState);
	}

	@Override
	public @NonNull BlockEntityType<?> getType() {
		return NetherDescentBlockEntityType.CAMPFIRE.get();
	}

    @Override
    public boolean isValidBlockState(@NonNull BlockState blockState) {
        return getType().isValid(blockState);
    }
}
