package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
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

public class NetherDescentCampfireBlock extends CampfireBlock {
	public NetherDescentCampfireBlock(boolean spawnParticles, int fireDamage, Properties properties) {
		super(spawnParticles, fireDamage, properties);
	}

	@Override
	public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new NetherDescentCampfireBlockEntity(pos, state);
	}

	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
		if (level instanceof ServerLevel serverLevel) {
			if (state.getValue(LIT)) {
				RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
				return createTickerHelper(blockEntityType, NetherDescentBlockEntityType.CAMPFIRE.get(), (levelx, blockPos, blockState, campfireBlockEntity) -> CampfireBlockEntity.cookTick(serverLevel, blockPos, blockState, campfireBlockEntity, cachedCheck));
			} else {
				return createTickerHelper(blockEntityType, NetherDescentBlockEntityType.CAMPFIRE.get(), CampfireBlockEntity::cooldownTick);
			}
		} else {
			return state.getValue(LIT) ? createTickerHelper(blockEntityType, NetherDescentBlockEntityType.CAMPFIRE.get(), CampfireBlockEntity::particleTick) : null;
		}
	}
}
