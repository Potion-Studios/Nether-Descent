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

public class MossyCarpetBlock extends Block implements BonemealableBlock {
    public static final MapCodec<MossyCarpetBlock> CODEC = simpleCodec(MossyCarpetBlock::new);
    public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
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
    private static final float AABB_OFFSET = 1.0F;
    private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final int SHORT_HEIGHT = 10;
    private static final VoxelShape WEST_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 10.0, 16.0);
    private static final VoxelShape EAST_SHORT_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    private static final VoxelShape NORTH_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 1.0);
    private static final VoxelShape SOUTH_SHORT_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 10.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    @Override
    public @NotNull MapCodec<MossyCarpetBlock> codec() {
        return CODEC;
    }

    public MossyCarpetBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(BASE, true)
                        .setValue(NORTH, WallSide.NONE)
                        .setValue(EAST, WallSide.NONE)
                        .setValue(SOUTH, WallSide.NONE)
                        .setValue(WEST, WallSide.NONE)
        );
        this.shapesCache = ImmutableMap.copyOf(
                this.stateDefinition
                        .getPossibleStates()
                        .stream()
                        .collect(Collectors.toMap(Function.identity(), MossyCarpetBlock::calculateShape))
        );
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    private static VoxelShape calculateShape(BlockState state) {
        VoxelShape voxelShape = Shapes.empty();
        if (state.getValue(BASE)) {
            voxelShape = DOWN_AABB;
        }
        voxelShape = switch (state.getValue(NORTH)) {
            case NONE -> voxelShape;
            case LOW -> Shapes.or(voxelShape, NORTH_SHORT_AABB);
            case TALL -> Shapes.or(voxelShape, NORTH_AABB);
        };

        voxelShape = switch (state.getValue(SOUTH)) {
            case NONE -> voxelShape;
            case LOW -> Shapes.or(voxelShape, SOUTH_SHORT_AABB);
            case TALL -> Shapes.or(voxelShape, SOUTH_AABB);
        };

        voxelShape = switch (state.getValue(EAST)) {
            case NONE -> voxelShape;
            case LOW -> Shapes.or(voxelShape, EAST_SHORT_AABB);
            case TALL -> Shapes.or(voxelShape, EAST_AABB);
        };

        voxelShape = switch (state.getValue(WEST)) {
            case NONE -> voxelShape;
            case LOW -> Shapes.or(voxelShape, WEST_SHORT_AABB);
            case TALL -> Shapes.or(voxelShape, WEST_AABB);
        };
        return voxelShape.isEmpty() ? Shapes.block() : voxelShape;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(BASE) ? DOWN_AABB : Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.below());
        return state.getValue(BASE) ? !blockState.isAir() : blockState.is(this) && blockState.getValue(BASE);
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

    private static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        return direction != Direction.UP && MultifaceBlock.canAttachTo(level, direction, pos, level.getBlockState(pos.relative(direction)));
    }

    private static BlockState getUpdatedState(Block block, BlockState state, BlockGetter level, BlockPos pos, boolean tip) {
        BlockState blockState = null;
        BlockState blockState2 = null;
        tip |= state.getValue(BASE);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> enumProperty = getPropertyForFace(direction);
            WallSide wallSide = canSupportAtFace(level, pos, direction) ? (tip ? WallSide.LOW : state.getValue(enumProperty)) : WallSide.NONE;
            if (wallSide == WallSide.LOW) {
                if (blockState == null) {
                    blockState = level.getBlockState(pos.above());
                }

                if (blockState.is(block) && blockState.getValue(enumProperty) != WallSide.NONE && !(Boolean)blockState.getValue(BASE)) {
                    wallSide = WallSide.TALL;
                }

                if (!(Boolean)state.getValue(BASE)) {
                    if (blockState2 == null) {
                        blockState2 = level.getBlockState(pos.below());
                    }

                    if (blockState2.is(block) && blockState2.getValue(enumProperty) == WallSide.NONE) {
                        wallSide = WallSide.NONE;
                    }
                }
            }

            state = state.setValue(enumProperty, wallSide);
        }

        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getUpdatedState(this.asBlock(), this.defaultBlockState(), context.getLevel(), context.getClickedPos(), true);
    }

    public static void placeAt(BlockState blockState, LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
        BlockState blockState2 = getUpdatedState(blockState.getBlock(), blockState, level, pos, true);
        level.setBlock(pos, blockState2, 3);
        BlockState blockState3 = createTopperWithSideChance(blockState.getBlock(), level, pos, random::nextBoolean);
        if (!blockState3.isAir()) {
            level.setBlock(pos.above(), blockState3, flags);
        }
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide) {
            RandomSource randomSource = level.getRandom();
            BlockState blockState = createTopperWithSideChance(this.asBlock(), level, pos, randomSource::nextBoolean);
            if (!blockState.isAir()) {
                level.setBlock(pos.above(), blockState, 3);
            }
        }
    }

    private static BlockState createTopperWithSideChance(Block block, BlockGetter level, BlockPos pos, BooleanSupplier placeSide) {
        BlockPos blockPos = pos.above();
        BlockState blockState = level.getBlockState(blockPos);
        boolean bl = blockState.is(block);
        if ((!bl || !(Boolean)blockState.getValue(BASE)) && (bl || blockState.canBeReplaced())) {
            BlockState blockState2 = block.defaultBlockState().setValue(BASE, false);
            BlockState blockState3 = getUpdatedState(block, blockState2, level, pos.above(), true);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> enumProperty = getPropertyForFace(direction);
                if (blockState3.getValue(enumProperty) != WallSide.NONE && !placeSide.getAsBoolean()) {
                    blockState3 = blockState3.setValue(enumProperty, WallSide.NONE);
                }
            }

            return hasFaces(blockState3) && blockState3 != blockState ? blockState3 : Blocks.AIR.defaultBlockState();
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            BlockState blockState = getUpdatedState(asBlock(), state, level, pos, false);
            return !hasFaces(blockState) ? Blocks.AIR.defaultBlockState() : blockState;
        }
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(BASE, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected @NotNull BlockState rotate(@NotNull BlockState state, Rotation rotation) {
        return switch (rotation) {
            case Rotation.CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH))
                    .setValue(EAST, state.getValue(WEST))
                    .setValue(SOUTH, state.getValue(NORTH))
                    .setValue(WEST, state.getValue(EAST));
            case Rotation.COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST))
                    .setValue(EAST, state.getValue(SOUTH))
                    .setValue(SOUTH, state.getValue(WEST))
                    .setValue(WEST, state.getValue(NORTH));
            case Rotation.CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST))
                    .setValue(EAST, state.getValue(NORTH))
                    .setValue(SOUTH, state.getValue(EAST))
                    .setValue(WEST, state.getValue(SOUTH));
            default -> state;
        };
    }

    @Override
    protected @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Nullable
    public static EnumProperty<WallSide> getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, BlockState state) {
        return state.getValue(BASE) && !createTopperWithSideChance(this.asBlock(), level, pos, () -> true).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockState blockState = createTopperWithSideChance(this.asBlock(), level, pos, () -> true);
        if (!blockState.isAir()) {
            level.setBlock(pos.above(), blockState, 3);
        }
    }
}
