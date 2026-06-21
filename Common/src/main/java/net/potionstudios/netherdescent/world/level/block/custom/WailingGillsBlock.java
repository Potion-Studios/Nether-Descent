package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.world.level.block.entity.WailingGillsBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WailingGillsBlock extends BaseEntityBlock {
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public WailingGillsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    public int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return state.getValue(POWER);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) refreshPower(level, pos, state);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        if (!level.isClientSide()) refreshPower(level, pos, state);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    private void refreshPower(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        int power = level.getBestNeighborSignal(pos);
        if (state.getValue(POWER) != power)
            level.setBlock(pos, state.setValue(POWER, power), 3);
    }

    @Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new WailingGillsBlockEntity(pos, state);
	}

	@Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(POWER));
    }

    @Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
		return createTickerHelper(level, blockEntityType, NetherDescentBlockEntityType.WAILING_GILLS.get());
	}

	public static <T extends BlockEntity> BlockEntityTicker<T> createTickerHelper(
			Level level, BlockEntityType<T> serverType, BlockEntityType<? extends WailingGillsBlockEntity> clientType
	) {
		return level.isClientSide() ? null : createTickerHelper(serverType, clientType, WailingGillsBlockEntity::serverTick);
	}
}
