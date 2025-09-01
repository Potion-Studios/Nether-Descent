package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

public class HangingMossBlock extends Block implements BonemealableBlock {
    public static final MapCodec<HangingMossBlock> CODEC = simpleCodec(HangingMossBlock::new);
    private static final int SIDE_PADDING = 1;
    private static final VoxelShape TIP_SHAPE = Block.box(1.0, 2.0, 1.0, 15.0, 16.0, 15.0);
    private static final VoxelShape BASE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public static final BooleanProperty TIP = BooleanProperty.create("tip");

    @Override
    public @NotNull MapCodec<HangingMossBlock> codec() {
        return CODEC;
    }

    public HangingMossBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP, true));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(TIP) ? TIP_SHAPE : BASE_SHAPE;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        /*
        if (random.nextInt(500) == 0) {
            BlockState blockState = level.getBlockState(pos.above());
            if (blockState.is(BlockTags.PALE_OAK_LOGS) || blockState.is(Blocks.PALE_OAK_LEAVES)) {
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PALE_HANGING_MOSS_IDLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }

         */
    }

    @Override
    protected boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return this.canStayAtPosition(level, pos);
    }

    private boolean canStayAtPosition(BlockGetter level, BlockPos pos) {
        BlockPos blockPos = pos.relative(Direction.UP);
        BlockState blockState = level.getBlockState(blockPos);
        return MultifaceBlock.canAttachTo(level, Direction.UP, blockPos, blockState) || blockState.is(NetherDescentBlocks.EMBUR_MOSS_CARPET.get());
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (!this.canStayAtPosition(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }

        return state.setValue(TIP, !level.getBlockState(pos.below()).is(this));
    }

    @Override
    protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!this.canStayAtPosition(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(TIP);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return this.canGrowInto(level.getBlockState(this.getTip(level, pos).below()));
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    public BlockPos getTip(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

        BlockState blockState;
        do {
            mutableBlockPos.move(Direction.DOWN);
            blockState = level.getBlockState(mutableBlockPos);
        } while (blockState.is(this));

        return mutableBlockPos.relative(Direction.UP).immutable();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockPos blockPos = this.getTip(level, pos).below();
        if (this.canGrowInto(level.getBlockState(blockPos))) {
            level.setBlockAndUpdate(blockPos, state.setValue(TIP, true));
        }
    }
}
