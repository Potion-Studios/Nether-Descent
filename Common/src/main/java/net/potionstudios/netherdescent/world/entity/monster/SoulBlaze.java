package net.potionstudios.netherdescent.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class SoulBlaze extends Blaze {
    public SoulBlaze(EntityType<? extends Blaze> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 14;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new SoulBlazeAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0F).add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.FOLLOW_RANGE, 30.0F);
    }

    static class SoulBlazeAttackGoal extends Goal {
        private final SoulBlaze blaze;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        public SoulBlazeAttackGoal(SoulBlaze blaze) {
            this.blaze = blaze;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.blaze.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.blaze.canAttack(livingEntity);
        }

        @Override
        public void start() {
            this.attackStep = 0;
        }

        @Override
        public void stop() {
            this.blaze.setCharged(false);
            this.lastSeen = 0;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            this.attackTime--;
            LivingEntity livingEntity = this.blaze.getTarget();
            if (livingEntity != null) {
                boolean bl = this.blaze.getSensing().hasLineOfSight(livingEntity);
                if (bl) {
                    this.lastSeen = 0;
                } else {
                    this.lastSeen++;
                }

                double d = this.blaze.distanceToSqr(livingEntity);
                if (d < 4.0) {
                    if (!bl) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.blaze.doHurtTarget(livingEntity);
                    }

                    this.blaze.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                } else if (d < this.getFollowDistance() * this.getFollowDistance() && bl) {
                    double e = livingEntity.getX() - this.blaze.getX();
                    double f = livingEntity.getY(0.5) - this.blaze.getY(0.5);
                    double g = livingEntity.getZ() - this.blaze.getZ();
                    if (this.attackTime <= 0) {
                        this.attackStep++;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.blaze.setCharged(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.blaze.setCharged(false);
                        }

                        if (this.attackStep > 1) {
                            double h = Math.sqrt(Math.sqrt(d)) * 0.5;
                            if (!this.blaze.isSilent()) {
                                this.blaze.level().levelEvent(null, 1018, this.blaze.blockPosition(), 0);
                            }

                            for (int i = 0; i < 1; i++) {
                                Vec3 vec3 = new Vec3(this.blaze.getRandom().triangle(e, 2.297 * h), f, this.blaze.getRandom().triangle(g, 2.297 * h));
                                SmallSoulFireball smallFireball = new SmallSoulFireball(this.blaze, vec3.normalize(), this.blaze.level());
                                smallFireball.setPos(smallFireball.getX(), this.blaze.getY(0.5) + 0.5, smallFireball.getZ());
                                this.blaze.level().addFreshEntity(smallFireball);
                            }
                        }
                    }

                    this.blaze.getLookControl().setLookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.blaze.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                }

                super.tick();
            }
        }

        private double getFollowDistance() {
            return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}
