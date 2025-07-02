package net.potionstudios.netherdescent.world.level.block.wood.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.world.level.block.entities.sign.NetherDescentHangingSignBlockEntity;
import org.jetbrains.annotations.NotNull;

public class NetherDescentWallHangingSignBlock extends WallHangingSignBlock {
    public NetherDescentWallHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new NetherDescentHangingSignBlockEntity(pos, state);
    }
}
