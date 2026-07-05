package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.tags.NetherDescentBlockTags;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.SythianFarmBlock;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SythianShootBlock extends Block implements BonemealableBlock {
    private static final VoxelShape BOTTOM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    private static final VoxelShape TOP_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public SythianShootBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(HANGING, false));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        boolean hanging = state.getValue(HANGING);
        if (level.isEmptyBlock(hanging ? pos.below() : pos.above())) {
                int modifier = 6;
                BlockState dirtState = level.getBlockState(hanging ? pos.above() : pos.below());
                if (dirtState.getBlock() instanceof SythianFarmBlock)
                    modifier = dirtState.getValue(SythianFarmBlock.MOSSY) ? 4 : 2;
                if (random.nextInt(modifier) == 0)
                    growStalk(level, pos, state);
        }
    }

    @Override
    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(pos);
        VoxelShape shape = state.getValue(HANGING) ? TOP_SHAPE : BOTTOM_SHAPE;
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        boolean hanging = direction == Direction.DOWN;
        return this.defaultBlockState().setValue(HANGING, hanging);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HANGING));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, @NonNull BlockPos pos) {
        return level.getBlockState(state.getValue(HANGING) ? pos.above() : pos.below()).is(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON);
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos pos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        if (!state.canSurvive(level, pos))
            return Blocks.AIR.defaultBlockState();
        else {
            if ((directionToNeighbour == Direction.UP || directionToNeighbour == Direction.DOWN) && neighbourState.is(NetherDescentBlocks.SYTHIAN_STALK.get()))
                return NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.HANGING, state.getValue(HANGING));

            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }
    }

    @Override
    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        return NetherDescentBlocks.SYTHIAN_STALK.getItem().getDefaultInstance();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, @NonNull BlockPos pos, BlockState state) {
        return level.isEmptyBlock(state.getValue(HANGING) ? pos.below() : pos.above());
    }

    @Override
    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        growStalk(level, pos, state);
    }

    protected void growStalk(Level level, BlockPos pos, BlockState state) {
        level.setBlock(state.getValue(HANGING) ? pos.below() : pos.above(), NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.LEAVES, BambooLeaves.SMALL).setValue(SythianStalkBlock.HANGING, state.getValue(HANGING)), 3);
    }
}
