package net.potionstudios.netherdescent.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.jetbrains.annotations.NotNull;

public class SmallSoulFireball extends Fireball {
    public SmallSoulFireball(EntityType<? extends SmallSoulFireball> entityType, Level level) {
        super(entityType, level);
    }

    public SmallSoulFireball(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ) {
        super(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), shooter, offsetX, offsetY, offsetZ, level);
    }

    public SmallSoulFireball(Level level, double x, double y, double z, double offsetX, double offsetY, double offsetZ) {
        super(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), x, y, z, offsetX, offsetY, offsetZ, level);
    }
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide()) {
            Entity entity1 = result.getEntity();
            Entity entity = getOwner();
            int i = entity1.getRemainingFireTicks();
            entity1.setSecondsOnFire(5);
            DamageSource damageSource = damageSources().fireball(this, entity);
            if (!entity1.hurt(damageSource, 7.0F))
                entity1.setRemainingFireTicks(i);
            else if (entity instanceof LivingEntity livingEntity)
                this.doEnchantDamageEffects(livingEntity, entity1);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                BlockPos blockPos = result.getBlockPos().relative(result.getDirection());
                if (this.level().isEmptyBlock(blockPos)) {
                    this.level().setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level(), blockPos));
                }
            }
        }
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!level().isClientSide())
            discard();
    }

	@Override
	public @NotNull ItemStack getItem() {
		return NetherDescentItems.SOUL_FIRE_CHARGE.get().getDefaultInstance();
	}
}
