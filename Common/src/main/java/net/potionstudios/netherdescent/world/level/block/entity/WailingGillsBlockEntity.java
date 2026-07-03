package net.potionstudios.netherdescent.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.world.level.block.custom.WailingGillsBlock;

import java.util.List;

public class WailingGillsBlockEntity extends BlockEntity {
    private static Holder<Enchantment> SOUL_SPEED = null;
    private final AABB[] cachedBoxes = new AABB[16];
    public WailingGillsBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.WAILING_GILLS.get(), pos, blockState);
        this.cachedBoxes[0] = new AABB(pos.below()).expandTowards(0.0, -15, 0.0);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, WailingGillsBlockEntity blockEntity) {
		ServerLevel serverLevel = (ServerLevel) level;
		if (serverLevel.getServer().getTickCount() % 5 == 0 && !level.getBlockState(pos.below()).isSolid()) {

            if (SOUL_SPEED == null)
                SOUL_SPEED = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SOUL_SPEED);

            int powered = state.getValue(WailingGillsBlock.POWER);
            AABB searchArea = blockEntity.cachedBoxes[powered];

            if (searchArea == null) {
                double maxDistance = 15.0 + powered;
                searchArea = new AABB(
                        pos.getX(), pos.getY() - 1 - maxDistance, pos.getZ(),
                        pos.getX() + 1, pos.getY(), pos.getZ() + 1
                );
                blockEntity.cachedBoxes[powered] = searchArea;
            }

            List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, searchArea, LivingEntity::isAlive);

            if (entities.isEmpty()) return;

            int solid = level.getMinBuildHeight();
            for (int i = 1; i <= 15 + powered; i++)
                if (level.getBlockState(pos.below(i)).isSolid()) {
                    solid = pos.below(i).getY();
                    break;
                }

            for (LivingEntity entity: entities) {
                if (entity.isSpectator() || entity.getY() < solid)
                    continue;

                for (ItemStack itemStack: entity.getArmorSlots())
                    if (EnchantmentHelper.getItemEnchantmentLevel(SOUL_SPEED, itemStack) > 0)
                        return;

                entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 6, powered, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160, powered, false, false));
	            if (entity instanceof ServerPlayer player)
		            NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().trigger(player, pos, entity);
                else if (entity instanceof Animal animal && animal.getLeashHolder() instanceof ServerPlayer player)
                    NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().trigger(player, pos, entity);
                ParticleOptions particleData = powered > 0 ? NetherDescentParticles.GILL_LEVITATE_POWERED.get() : NetherDescentParticles.GILL_LEVITATE.get();
                for (int i = 0; i < blockEntity.getBlockPos().getY() - entity.getY() - 1; i++)
                    serverLevel.sendParticles(particleData, entity.getX(), entity.getY() + i, entity.getZ(), 2,0.5, 0, 0.5, 1.2 + (powered * 0.9));
            }
		}
	}
}
