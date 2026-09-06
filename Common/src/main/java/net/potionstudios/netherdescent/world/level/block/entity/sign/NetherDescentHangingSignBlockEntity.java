package net.potionstudios.netherdescent.world.level.block.entity.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import org.jspecify.annotations.NonNull;

public class NetherDescentHangingSignBlockEntity extends HangingSignBlockEntity {
    public NetherDescentHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public @NonNull BlockEntityType<?> getType() {
        return NetherDescentBlockEntityType.HANGING_SIGNS.get();
    }

    @Override
    public boolean isValidBlockState(@NonNull BlockState blockState) {
        return getType().isValid(blockState);
    }
}
