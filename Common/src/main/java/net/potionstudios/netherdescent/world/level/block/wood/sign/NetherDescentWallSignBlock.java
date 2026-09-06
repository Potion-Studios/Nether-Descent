package net.potionstudios.netherdescent.world.level.block.wood.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.world.level.block.entity.sign.NetherDescentSignBlockEntity;
import org.jspecify.annotations.NonNull;

public class NetherDescentWallSignBlock extends WallSignBlock {
    public NetherDescentWallSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public @NonNull BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new NetherDescentSignBlockEntity(pos, state);
    }
}
