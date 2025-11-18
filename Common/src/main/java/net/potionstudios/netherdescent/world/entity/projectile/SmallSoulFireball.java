package net.potionstudios.netherdescent.world.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import org.jetbrains.annotations.NotNull;

public class SmallSoulFireball extends Fireball {
    public SmallSoulFireball(EntityType<? extends SmallSoulFireball> entityType, Level level) {
        super(entityType, level);
    }

    public SmallSoulFireball(double x, double y, double z, Vec3 movement, Level level) {
        super(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), x, y, z, movement, level);
    }

    public SmallSoulFireball(LivingEntity owner, Vec3 movement, Level level) {
        super(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), owner, movement, level);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (level() instanceof ServerLevel serverlevel) {
            Entity entity1 = result.getEntity();
            Entity entity = getOwner();
            int i = entity1.getRemainingFireTicks();
            entity1.igniteForSeconds(5.0F);
            DamageSource damageSource = damageSources().fireball(this, entity);
            if (!entity1.hurt(damageSource, 5.0F))
                entity1.setRemainingFireTicks(i);
            else EnchantmentHelper.doPostAttackEffects(serverlevel, entity1, damageSource);
        }
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (!level().isClientSide())
            discard();
    }
}
