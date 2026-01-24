package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FungalBulbsBlock extends FaceAttachedHorizontalDirectionalBlock implements BonemealableBlock {
	public static final MapCodec<FungalBulbsBlock> CODEC = simpleCodec(FungalBulbsBlock::new);
    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);
    private static final VoxelShape SHAPE_SOUTH = Block.box(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 4.0D);
    private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 1.0D, 1.0D, 4.0D, 15.0D, 15.0D);

    public FungalBulbsBlock(Properties properties) {
        super(properties);
	    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
    }

	@Override
	protected @NotNull MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
		return CODEC;
	}

	@Override
    protected void onProjectileHit(@NotNull Level level, @NotNull BlockState state, @NotNull BlockHitResult hit, @NotNull Projectile projectile) {
        if (!level.isClientSide()) {
	        level.getEntitiesOfClass(LivingEntity.class, new AABB(-4.0, -4.0, -4.0, 4.0, 4.0, 4.0).move(hit.getBlockPos()))
			        .forEach(entity -> {
				        entity.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1));
				        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 1));
			        });
	        level.levelEvent(2008, hit.getBlockPos(), 0);
			level.destroyBlock(hit.getBlockPos(), false);
        }
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
	    AttachFace face = state.getValue(FACE);
	    if (face == AttachFace.FLOOR)
		    return SHAPE;
	    else if (face == AttachFace.CEILING)
		    return SHAPE.move(0, 0.75D, 0);
	    else return switch (state.getValue(FACING)) {
			    case SOUTH -> SHAPE_SOUTH;
			    case WEST -> SHAPE_EAST.move(0.75D, 0, 0);
			    case EAST -> SHAPE_EAST;
			    default -> SHAPE_SOUTH.move(0, 0, 0.75D);
		    };
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        popResource(level, pos, defaultBlockState().getBlock().asItem().getDefaultInstance());
    }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FACING, FACE));
	}
}
