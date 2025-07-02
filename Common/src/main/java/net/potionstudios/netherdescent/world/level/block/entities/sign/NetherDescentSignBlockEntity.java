package net.potionstudios.netherdescent.world.level.block.entities.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.entities.NetherDescentBlockEntityType;

public class NetherDescentSignBlockEntity extends SignBlockEntity {
    public NetherDescentSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(NetherDescentBlockEntityType.SIGNS.get(), pos, blockState);
    }
}
