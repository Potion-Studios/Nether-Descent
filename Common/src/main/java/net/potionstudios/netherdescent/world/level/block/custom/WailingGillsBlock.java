package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.level.redstone.Orientation;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.world.level.block.entity.WailingGillsBlockEntity;
import org.jspecify.annotations.NonNull;

public class WailingGillsBlock extends BaseEntityBlock {
	private static final MapCodec<WailingGillsBlock> CODEC = simpleCodec(WailingGillsBlock::new);
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public WailingGillsBlock(Properties properties) {
		super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(POWER, 0));
	}

	@Override
	protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

    @Override
    protected int getSignal(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Direction direction) {
        return state.getValue(POWER);
    }

    @Override
    protected void onPlace(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) refreshPower(level, pos, state);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void neighborChanged(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Block block, @org.jspecify.annotations.Nullable Orientation orientation, boolean movedByPiston) {
        if (!level.isClientSide()) refreshPower(level, pos, state);
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }

    private void refreshPower(Level level, BlockPos pos, BlockState state) {
        int power = level.getBestNeighborSignal(pos);
        if (state.getValue(POWER) != power)
            level.setBlock(pos, state.setValue(POWER, power), 3);
    }

    @Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new WailingGillsBlockEntity(pos, state);
	}

	@Override
	protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
		return RenderShape.MODEL;
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(POWER));
    }

    @Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
		return createTickerHelper(level, blockEntityType, NetherDescentBlockEntityType.WAILING_GILLS.get());
	}

	public static <T extends BlockEntity> BlockEntityTicker<T> createTickerHelper(
			Level level, BlockEntityType<T> serverType, BlockEntityType<? extends WailingGillsBlockEntity> clientType
	) {
		return level.isClientSide() ? null : createTickerHelper(serverType, clientType, WailingGillsBlockEntity::serverTick);
	}
}
