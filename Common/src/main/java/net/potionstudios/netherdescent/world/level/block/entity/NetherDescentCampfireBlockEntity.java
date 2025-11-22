package net.potionstudios.netherdescent.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NetherDescentCampfireBlockEntity extends CampfireBlockEntity {
	public NetherDescentCampfireBlockEntity(BlockPos pos, BlockState blockState) {
		super(pos, blockState);
	}

	@Override
	public @NotNull BlockEntityType<?> getType() {
		return NetherDescentBlockEntityType.CAMPFIRE.get();
	}

    @Override
    public boolean isValidBlockState(@NotNull BlockState blockState) {
        return getType().isValid(blockState);
    }
}
