package net.potionstudios.netherdescent.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SoulGhast extends Ghast {
    public SoulGhast(EntityType<? extends Ghast> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 8;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0F).add(Attributes.FOLLOW_RANGE, 100.0F);
    }

    public static boolean checkSoulGhastSpawnRules(EntityType<SoulGhast> ghast, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0 && checkMobSpawnRules(ghast, level, spawnType, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new GhastLookGoal(this));
        this.goalSelector.addGoal(7, new GhastShootFireballGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (livingEntity) -> Math.abs(livingEntity.getY() - this.getY()) <= (double)4.0F));
    }

    static class GhastShootFireballGoal extends Ghast.GhastShootFireballGoal {
        private final Ghast ghast;
        private int shotsFired = 0;
        private int nextShotTick = 0;
        public GhastShootFireballGoal(Ghast ghast) {
            super(ghast);
            this.ghast = ghast;
        }

        private void fireBallOnce(LivingEntity target) {
            Level level = this.ghast.level();
            Vec3 view = this.ghast.getViewVector(1.0F);

            double spawnX = this.ghast.getX() + view.x * 4.0;
            double spawnY = this.ghast.getY(0.5) + 0.5;
            double spawnZ = this.ghast.getZ() + view.z * 4.0;

            double dx = target.getX() - spawnX;
            double dy = target.getY(0.5) - spawnY;
            double dz = target.getZ() - spawnZ;

            Vec3 direction = new Vec3(dx, dy, dz).normalize();

            LargeFireball fireball = new LargeFireball(level, this.ghast, direction, this.ghast.getExplosionPower());
            fireball.setPos(spawnX, spawnY, spawnZ);

            if (!this.ghast.isSilent()) {
                level.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
            }

            level.addFreshEntity(fireball);
        }

        @Override
        public void tick() {
            LivingEntity target = this.ghast.getTarget();
            if (target == null) return;

            if (target.distanceToSqr(this.ghast) < 4096.0 && this.ghast.hasLineOfSight(target)) {
                Level level = this.ghast.level();
                this.chargeTime++;

                // Charging sound
                if (this.chargeTime == 10 && !this.ghast.isSilent()) {
                    level.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
                }

                // FIRST SHOT
                if (this.chargeTime == 20) {
                    fireBallOnce(target);
                    shotsFired = 1;
                    nextShotTick = 22;  // fire again 2 ticks later
                }

                // SECOND SHOT (tracks the player’s movement)
                if (shotsFired == 1 && this.chargeTime == nextShotTick) {
                    fireBallOnce(target);  // re-aims using NEW target position
                    shotsFired = 0;
                    this.chargeTime = -40; // cooldown
                }

            } else {
                if (this.chargeTime > 0) this.chargeTime--;
            }

            this.ghast.setCharging(this.chargeTime > 10);
        }

//        public void tick() {
//            LivingEntity livingEntity = this.ghast.getTarget();
//            if (livingEntity != null) {
//                if (livingEntity.distanceToSqr(this.ghast) < (double)4096.0F && this.ghast.hasLineOfSight(livingEntity)) {
//                    Level level = this.ghast.level();
//                    ++this.chargeTime;
//                    if (this.chargeTime == 10 && !this.ghast.isSilent()) {
//                        level.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
//                    }
//
//                    if (this.chargeTime == 20) {
//                        Vec3 vec3 = this.ghast.getViewVector(1.0F);
//                        double f = livingEntity.getX() - (this.ghast.getX() + vec3.x * (double)4.0F);
//                        double g = livingEntity.getY(0.5F) - ((double)0.5F + this.ghast.getY((double)0.5F));
//                        double h = livingEntity.getZ() - (this.ghast.getZ() + vec3.z * (double)4.0F);
//                        Vec3 vec32 = new Vec3(f, g, h);
//                        if (!this.ghast.isSilent()) {
//                            level.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
//                        }
//
//                        for (int i = 0; i < 2; i++) {
//                            LargeFireball largeFireball = new LargeFireball(level, this.ghast, vec32.normalize(), this.ghast.getExplosionPower());
//                            largeFireball.setPos(this.ghast.getX() + vec3.x * (double) 4.0F, this.ghast.getY(0.5F) + (double) 0.5F, largeFireball.getZ() + vec3.z * (double) 4.0F);
//                            level.addFreshEntity(largeFireball);
//                        }
//
//                        this.chargeTime = -40;
//                    }
//                } else if (this.chargeTime > 0) {
//                    --this.chargeTime;
//                }
//
//                this.ghast.setCharging(this.chargeTime > 10);
//            }
//        }
    }
}
