package net.potionstudios.netherdescent.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.advancements.critereon.NetherDescentCriterionTriggers;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.world.level.block.custom.WailingGillsBlock;

import java.util.List;

public class WailingGillsBlockEntity extends BlockEntity {
	public WailingGillsBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.WAILING_GILLS.get(), pos, blockState);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, WailingGillsBlockEntity blockEntity) {
		ServerLevel serverLevel = (ServerLevel) level;

		if (serverLevel.getServer().getTickCount() % 5 == 0 && !level.getBlockState(pos.below()).isSolid()) {
			int powered = state.getValue(WailingGillsBlock.POWER);

            double distance = -15.0 - powered;

			AABB aabb = new AABB(pos.below()).expandTowards(0.0, distance, 0.0);

            List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb);

            if (entities.isEmpty()) return;

            int solid = level.getMinBuildHeight();
            for (int i = 1; i <= Math.abs(distance); i++)
                if (level.getBlockState(pos.below(i)).isSolid()) {
                    solid = pos.below(i).getY();
                    break;
                }

            for (LivingEntity entity: entities) {
                if (entity.isSpectator() || entity.getY() < solid)
                    return;

                for (ItemStack itemStack: entity.getArmorSlots())
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SOUL_SPEED, itemStack) > 0)
                        return;

                entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 6, powered, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160, powered, false, false));
	            if (entity instanceof ServerPlayer player)
		            NetherDescentCriterionTriggers.WAILING_INTERACTION.trigger(player, pos, entity);
                else if (entity instanceof Animal animal && animal.getLeashHolder() instanceof ServerPlayer player)
                    NetherDescentCriterionTriggers.WAILING_INTERACTION.trigger(player, pos, entity);
                ParticleOptions particleData = powered > 0 ? NetherDescentParticles.GILL_LEVITATE_POWERED.get() : NetherDescentParticles.GILL_LEVITATE.get();
                for (int i = 0; i < blockEntity.getBlockPos().getY() - entity.getY() - 1; i++)
                    serverLevel.sendParticles(particleData, entity.getX(), entity.getY() + i, entity.getZ(), 2,0.5, 0, 0.5, 1.2 + (powered * 0.9));
            }
		}
	}
}
