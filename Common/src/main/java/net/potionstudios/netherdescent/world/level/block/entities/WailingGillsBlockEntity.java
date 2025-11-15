package net.potionstudios.netherdescent.world.level.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.world.level.block.custom.WailingGillsBlock;

public class WailingGillsBlockEntity extends BlockEntity {
	public WailingGillsBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.WAILING_GILLS.get(), pos, blockState);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, WailingGillsBlockEntity blockEntity) {
		ServerLevel serverLevel = (ServerLevel) level;
		if (serverLevel.getServer().getTickCount() % 4 == 0) {
			AABB aabb = new AABB(pos).expandTowards(0.0, -15.0 - state.getValue(WailingGillsBlock.POWER), 0.0);
			Holder<Enchantment> soulSpeed = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SOUL_SPEED);
			serverLevel.getEntitiesOfClass(LivingEntity.class, aabb).forEach(entity -> {
				if (entity.isSpectator())
					return;

                for (ItemStack itemStack: entity.getArmorSlots())
                    if (EnchantmentHelper.getItemEnchantmentLevel(soulSpeed, itemStack) > 0)
                        return;

                entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 80, 0, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160, 0, false, false));
			});
		}
	}
}
