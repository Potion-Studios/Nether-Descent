package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HornetNestBlock extends BaseEntityBlock {
	public static final MapCodec<HornetNestBlock> CODEC = simpleCodec(HornetNestBlock::new);
	public HornetNestBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

//	private boolean nestContainsHornets(Level level, BlockPos pos) {
//		return level.getBlockEntity(pos) instanceof HornetNestBlockEntity hornetNestBlockEntity ? hornetNestBlockEntity.isEmpty() : false;
//	}

	@Override
	protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new HornetNestBlockEntity(pos, state);
	}
}
