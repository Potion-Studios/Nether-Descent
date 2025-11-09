package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WailingBulbBlossomBlock extends Block {
	public WailingBulbBlossomBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (!level.isClientSide() && entity instanceof LivingEntity livingEntity && !livingEntity.isSpectator()) {
			Holder<Enchantment> soulSpeed = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SOUL_SPEED);
			for (ItemStack itemStack: livingEntity.getArmorSlots())
				if (EnchantmentHelper.getItemEnchantmentLevel(soulSpeed, itemStack) > 0) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100));
					livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160));
				}
		}
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
	}
}
