package net.potionstudios.netherdescent.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WailingFungalGillsBlockEntity extends BlockEntity {
	public WailingFungalGillsBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.WAILING_FUNGAL_GILLS.get(), pos, blockState);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, WailingFungalGillsBlockEntity blockEntity) {
		ServerLevel serverLevel = (ServerLevel) level;
		if (serverLevel.getServer().getTickCount() % 20 == 0) {
			AABB aabb = new AABB(pos).expandTowards(0.0, 20.0, 0.0);
			serverLevel.getEntitiesOfClass(Entity.class, aabb).forEach(entity -> {
				if (entity instanceof ServerPlayer player)
					if (player.isSpectator()) return;


			});
		}
	}
}
