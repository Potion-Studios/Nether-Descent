package net.potionstudios.netherdescent.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WailingGillsBlockEntity extends BlockEntity {
	public WailingGillsBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.WAILING_GILLS.get(), pos, blockState);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, WailingGillsBlockEntity blockEntity) {
		ServerLevel serverLevel = (ServerLevel) level;
		if (serverLevel.getServer().getTickCount() % 20 == 0) {
			AABB aabb = new AABB(pos).expandTowards(0.0, 20.0, 0.0);
			Holder<Enchantment> soulSpeed = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SOUL_SPEED);
			serverLevel.getEntitiesOfClass(Entity.class, aabb).forEach(entity -> {
				if (entity instanceof ServerPlayer player) {
					if (player.isSpectator()) return;
					for (ItemStack itemStack: player.getArmorSlots())
						if (EnchantmentHelper.getItemEnchantmentLevel(soulSpeed, itemStack) > 0)
							return;
				}

			});
		}
	}
}
