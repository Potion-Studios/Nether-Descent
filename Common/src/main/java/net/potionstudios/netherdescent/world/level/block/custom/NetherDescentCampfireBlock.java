package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentCampfireBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NetherDescentCampfireBlock extends CampfireBlock {
	public NetherDescentCampfireBlock(boolean spawnParticles, int fireDamage, Properties properties) {
		super(spawnParticles, fireDamage, properties);
	}

	@Override
	public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new NetherDescentCampfireBlockEntity(pos, state);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
		if (level.isClientSide())
			return state.getValue(LIT) ? createTickerHelper(blockEntityType, NetherDescentBlockEntityType.CAMPFIRE.get(), CampfireBlockEntity::particleTick) : null;
		else
			return state.getValue(LIT) ? createTickerHelper(blockEntityType, NetherDescentBlockEntityType.CAMPFIRE.get(), CampfireBlockEntity::cookTick) : createTickerHelper(blockEntityType, NetherDescentBlockEntityType.CAMPFIRE.get(), CampfireBlockEntity::cooldownTick);
	}
}
