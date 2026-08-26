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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SythianShootBlock extends Block implements BonemealableBlock {
    private static final VoxelShape BOTTOM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    private static final VoxelShape TOP_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public SythianShootBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(HANGING, false));
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
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
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(pos);
        VoxelShape shape = state.getValue(HANGING) ? TOP_SHAPE : BOTTOM_SHAPE;
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        boolean hanging = direction == Direction.DOWN;
        return this.defaultBlockState().setValue(HANGING, hanging);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HANGING));
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return level.getBlockState(state.getValue(HANGING) ? pos.above() : pos.below()).is(NetherDescentBlockTags.SYTHIAN_STALK_PLANTABLE_ON);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos neighborPos, @NotNull BlockState neighborState, @NotNull RandomSource random) {
        if (!state.canSurvive(level, pos))
            return Blocks.AIR.defaultBlockState();
        else {
            if ((direction == Direction.UP || direction == Direction.DOWN) && neighborState.is(NetherDescentBlocks.SYTHIAN_STALK.get())) {
                return NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.HANGING, state.getValue(HANGING));
            }
            return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
        }
    }

    @Override
    protected @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean includeData) {
        return NetherDescentBlocks.SYTHIAN_STALK.getItem().getDefaultInstance();
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return level.isEmptyBlock(state.getValue(HANGING) ? pos.below() : pos.above());
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        growStalk(level, pos, state);
    }

    protected void growStalk(Level level, BlockPos pos, BlockState state) {
        level.setBlock(state.getValue(HANGING) ? pos.below() : pos.above(), NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.LEAVES, BambooLeaves.SMALL).setValue(SythianStalkBlock.HANGING, state.getValue(HANGING)), 3);
    }
}
