package net.potionstudios.netherdescent.world.level.block.wood.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.world.level.block.entity.sign.NetherDescentHangingSignBlockEntity;
import org.jspecify.annotations.NonNull;

public class NetherDescentWallHangingSignBlock extends WallHangingSignBlock {
    public NetherDescentWallHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public @NonNull BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new NetherDescentHangingSignBlockEntity(pos, state);
    }
}
