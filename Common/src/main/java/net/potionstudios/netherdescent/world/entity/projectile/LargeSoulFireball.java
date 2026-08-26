package net.potionstudios.netherdescent.world.entity.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.jetbrains.annotations.NotNull;

public class LargeSoulFireball extends Fireball {
	private int explosionPower = 1;

	public LargeSoulFireball(EntityType<? extends LargeSoulFireball> entityType, Level level) {
		super(entityType, level);
	}

	public LargeSoulFireball(Level level, LivingEntity owner, Vec3 movement, int explosionPower) {
		super(NetherDescentEntityType.SOUL_FIREBALL.get(), owner, movement, level);
		this.explosionPower = explosionPower;
	}

	protected void onHit(@NotNull HitResult result) {
		super.onHit(result);
		if (level() instanceof ServerLevel serverLevel) {
			boolean bl = serverLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
			serverLevel.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, bl, Level.ExplosionInteraction.MOB);
			this.discard();
		}
	}

	protected void onHitEntity(@NotNull EntityHitResult result) {
		super.onHitEntity(result);
		if (this.level() instanceof ServerLevel serverLevel) {
			Entity var6 = result.getEntity();
			Entity entity2 = this.getOwner();
			DamageSource damageSource = this.damageSources().fireball(this, entity2);
			var6.hurtServer(serverLevel, damageSource, 6.0F);
			EnchantmentHelper.doPostAttackEffects(serverLevel, var6, damageSource);
		}
	}

	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putByte("ExplosionPower", (byte)this.explosionPower);
	}

	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("ExplosionPower", 99))
			this.explosionPower = compound.getByte("ExplosionPower");
	}

	@Override
	public @NotNull ItemStack getItem() {
		return NetherDescentItems.SOUL_FIRE_CHARGE.get().getDefaultInstance();
	}
}
