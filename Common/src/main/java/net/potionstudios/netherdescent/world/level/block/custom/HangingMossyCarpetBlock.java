package net.potionstudios.netherdescent.world.level.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HangingMossyCarpetBlock extends Block implements BonemealableBlock {
    public static final MapCodec<MossyCarpetBlock> CODEC = simpleCodec(MossyCarpetBlock::new);
    public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
    private static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
    private static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
    private static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;

    private static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
            Util.make(Maps.newEnumMap(Direction.class), enumMap -> {
                enumMap.put(Direction.NORTH, NORTH);
                enumMap.put(Direction.EAST, EAST);
                enumMap.put(Direction.SOUTH, SOUTH);
                enumMap.put(Direction.WEST, WEST);
            })
    );

    // Floor Shapes
    private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape N_SHORT = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 1.0);
    private static final VoxelShape S_SHORT = Block.box(0.0, 0.0, 15.0, 16.0, 10.0, 16.0);
    private static final VoxelShape E_SHORT = Block.box(15.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    private static final VoxelShape W_SHORT = Block.box(0.0, 0.0, 0.0, 1.0, 10.0, 16.0);

    // Ceiling Shapes
    private static final VoxelShape UP_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape N_HANG_SHORT = Block.box(0.0, 6.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape S_HANG_SHORT = Block.box(0.0, 6.0, 15.0, 16.0, 16.0, 16.0);
    private static final VoxelShape E_HANG_SHORT = Block.box(15.0, 6.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape W_HANG_SHORT = Block.box(0.0, 6.0, 0.0, 1.0, 16.0, 16.0);

    // Tall Shapes (Full height 0-16)
    private static final VoxelShape N_TALL = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape S_TALL = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final VoxelShape E_TALL = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape W_TALL = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);

    private final Map<BlockState, VoxelShape> shapesCache;

    public HangingMossyCarpetBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BASE, true).setValue(HANGING, false)
                .setValue(NORTH, WallSide.NONE).setValue(EAST, WallSide.NONE)
                .setValue(SOUTH, WallSide.NONE).setValue(WEST, WallSide.NONE));

        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream()
                .collect(Collectors.toMap(Function.identity(), HangingMossyCarpetBlock::calculateShape)));
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    private static VoxelShape calculateShape(BlockState state) {
        boolean hang = state.getValue(HANGING);
        VoxelShape shape = state.getValue(BASE) ? (hang ? UP_AABB : DOWN_AABB) : Shapes.empty();

        shape = addSide(shape, state.getValue(NORTH), hang ? N_HANG_SHORT : N_SHORT, N_TALL);
        shape = addSide(shape, state.getValue(SOUTH), hang ? S_HANG_SHORT : S_SHORT, S_TALL);
        shape = addSide(shape, state.getValue(EAST), hang ? E_HANG_SHORT : E_SHORT, E_TALL);
        shape = addSide(shape, state.getValue(WEST), hang ? W_HANG_SHORT : W_SHORT, W_TALL);

        return shape.isEmpty() ? Shapes.block() : shape;
    }

    private static VoxelShape addSide(VoxelShape base, WallSide side, VoxelShape shortSide, VoxelShape tallSide) {
        return switch (side) {
            case NONE -> base;
            case LOW -> Shapes.or(base, shortSide);
            case TALL -> Shapes.or(base, tallSide);
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction supportDir = state.getValue(HANGING) ? Direction.UP : Direction.DOWN;
        BlockState supportState = level.getBlockState(pos.relative(supportDir));
        return state.getValue(BASE) ? !supportState.isAir() : supportState.is(this) && supportState.getValue(BASE) && supportState.getValue(HANGING) == state.getValue(HANGING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean hanging = context.getClickedFace() == Direction.DOWN;
        BlockState state = this.defaultBlockState().setValue(HANGING, hanging);
        return getUpdatedState(this, state, context.getLevel(), context.getClickedPos(), true);
    }

    private static BlockState getUpdatedState(Block block, BlockState state, BlockGetter level, BlockPos pos, boolean tip) {
        boolean hanging = state.getValue(HANGING);
        Direction verticalDir = hanging ? Direction.DOWN : Direction.UP;
        Direction supportDir = hanging ? Direction.UP : Direction.DOWN;
        tip |= state.getValue(BASE);

        for (Direction side : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> prop = PROPERTY_BY_DIRECTION.get(side);
            WallSide sideVal = MultifaceBlock.canAttachTo(level, side, pos, level.getBlockState(pos.relative(side)))
                    ? (tip ? WallSide.LOW : state.getValue(prop)) : WallSide.NONE;

            if (sideVal == WallSide.LOW) {
                BlockState neighbor = level.getBlockState(pos.relative(verticalDir));
                if (neighbor.is(block) && neighbor.getValue(prop) != WallSide.NONE && !neighbor.getValue(BASE)) {
                    sideVal = WallSide.TALL;
                }
                if (!state.getValue(BASE)) {
                    BlockState parent = level.getBlockState(pos.relative(supportDir));
                    if (parent.is(block) && parent.getValue(prop) == WallSide.NONE) sideVal = WallSide.NONE;
                }
            }
            state = state.setValue(prop, sideVal);
        }
        return state;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockState topper = createTopperWithSideChance(this, level, pos, () -> true, state.getValue(HANGING));
        if (!topper.isAir()) {
            Direction growthDir = state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
            level.setBlock(pos.relative(growthDir), topper.setValue(HANGING, state.getValue(HANGING)), 3);
        }
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide()) {
            BlockPos growthPos = pos.relative(state.getValue(HANGING) ? Direction.DOWN : Direction.UP);
            BlockState topper = createTopperWithSideChance(this, level, pos, level.getRandom()::nextBoolean, state.getValue(HANGING));

            if (!topper.isAir() && level.getBlockState(growthPos).canBeReplaced())
                level.setBlock(growthPos, topper.setValue(HANGING, state.getValue(HANGING)), 3);
        }
    }

    private static BlockState createTopperWithSideChance(Block block, BlockGetter level, BlockPos pos, BooleanSupplier placeSide, boolean hanging) {
        BlockPos blockPos = hanging ? pos.below() : pos.above();
        BlockState blockState = level.getBlockState(blockPos);
        boolean bl = blockState.is(block);
        if ((!bl || !(Boolean)blockState.getValue(BASE)) && (bl || blockState.canBeReplaced())) {
            BlockState blockState2 = block.defaultBlockState().setValue(BASE, false).setValue(HANGING, hanging);
            BlockState blockState3 = getUpdatedState(block, blockState2, level, hanging ? pos.below() : pos.above(), true);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> enumProperty = getPropertyForFace(direction);
                if (blockState3.getValue(enumProperty) != WallSide.NONE && !placeSide.getAsBoolean())
                    blockState3 = blockState3.setValue(enumProperty, WallSide.NONE);
            }

            return hasFaces(blockState3) && blockState3 != blockState ? blockState3 : Blocks.AIR.defaultBlockState();
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Nullable
    public static EnumProperty<WallSide> getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }

    private static boolean hasFaces(BlockState state) {
        if (state.getValue(BASE)) {
            return true;
        } else {
            for (EnumProperty<WallSide> enumProperty : PROPERTY_BY_DIRECTION.values()) {
                if (state.getValue(enumProperty) != WallSide.NONE) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public @NotNull MapCodec<MossyCarpetBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(BASE) ? (state.getValue(HANGING) ? UP_AABB : DOWN_AABB) : Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(BASE, HANGING, NORTH, EAST, SOUTH, WEST));
    }

    @Override
    protected boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, BlockState state) {
        return state.getValue(BASE);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, @NotNull Direction dir, @NotNull BlockState neighbor, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) return Blocks.AIR.defaultBlockState();
        return getUpdatedState(this, state, level, pos, false);
    }

    public static void placeAt(BlockState blockState, LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
        BlockState blockState2 = getUpdatedState(blockState.getBlock(), blockState, level, pos, true);
        level.setBlock(pos, blockState2, 3);
        BlockState blockState3 = createTopperWithSideChance(blockState.getBlock(), level, pos, random::nextBoolean, blockState.getValue(HANGING));
        if (!blockState3.isAir()) {
            level.setBlock(blockState.getValue(HANGING) ? pos.below() : pos.above(), blockState3, flags);
        }
    }
}