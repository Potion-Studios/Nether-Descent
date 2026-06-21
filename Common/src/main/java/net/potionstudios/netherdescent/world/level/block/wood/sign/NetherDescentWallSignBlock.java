package net.potionstudios.netherdescent.world.level.block.wood.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.world.level.block.entity.sign.NetherDescentSignBlockEntity;
import org.jetbrains.annotations.NotNull;

public class NetherDescentWallSignBlock extends WallSignBlock {
    public NetherDescentWallSignBlock(WoodType type, Properties properties) {
        super(properties, type);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new NetherDescentSignBlockEntity(pos, state);
    }
}
