package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class SythianFarmBlock extends Block {

    public static final BooleanProperty MOSSY = BooleanProperty.create("mossy");
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    private final Supplier<? extends Block> dirtBlock;

    public SythianFarmBlock(Properties properties, Supplier<? extends Block> dirtBlock) {
        super(properties);
        this.dirtBlock = dirtBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(MOSSY, false));
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos pos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        if (directionToNeighbour == Direction.UP && !state.canSurvive(level, pos))
            ticks.scheduleTick(pos, this, 1);

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.above());
        return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock || blockState.getBlock() instanceof MovingPistonBlock || blockState.is(NetherDescentBlocks.SYTHIAN_SHOOT.get()) || blockState.is(NetherDescentBlocks.SYTHIAN_STALK.get());
    }

    @Override
    protected boolean useShapeForLightOcclusion(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
                ? dirtBlock.get().defaultBlockState()
                : super.getStateForPlacement(context);
    }

    @Override
    protected void tick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (!state.canSurvive(level, pos))
            turnToDirtBlock(null, state, level, pos);
    }

    @Override
    public void fallOn(@NonNull Level level, @NonNull BlockState state, @NonNull BlockPos pos, @NonNull Entity entity, double fallDistance) {
        if (level instanceof ServerLevel serverLevel)
            if (level.getRandom().nextFloat() < fallDistance - 0.5F
                    && entity instanceof LivingEntity
                    && (entity instanceof Player || serverLevel.getGameRules().get(GameRules.MOB_GRIEFING))
                    && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
                turnToDirtBlock(entity, state, level, pos);
            }

        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }

    private void turnToDirtBlock(Entity entity, BlockState state, Level level, BlockPos pos) {
        BlockState blockState = pushEntitiesUp(state, dirtBlock.get().defaultBlockState(), level, pos);
        level.setBlockAndUpdate(pos, blockState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(MOSSY));
    }

    @Override
    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType pathComputationType) {
        return false;
    }

    
    
    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (!state.getValue(MOSSY) && stack.is(NetherDescentBlocks.EMBUR_CAVE_MOSS.get().asItem())) {
            level.setBlockAndUpdate(pos, state.setValue(MOSSY, true));
            if (!player.isCreative())
                stack.shrink(1);
            return InteractionResult.SUCCESS_SERVER;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
