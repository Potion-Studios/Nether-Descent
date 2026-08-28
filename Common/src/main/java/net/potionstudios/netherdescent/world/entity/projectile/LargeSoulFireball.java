package net.potionstudios.netherdescent.world.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

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
			boolean bl = serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
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

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putByte("ExplosionPower", (byte) this.explosionPower);
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		this.explosionPower = input.getByteOr("ExplosionPower", (byte)1);
	}

	@Override
	public @NotNull ItemStack getItem() {
		return NetherDescentItems.SOUL_FIRE_CHARGE.get().getDefaultInstance();
	}
}
