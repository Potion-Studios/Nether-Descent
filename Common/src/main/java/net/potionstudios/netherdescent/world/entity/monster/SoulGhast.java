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
        public GhastShootFireballGoal(Ghast ghast) {
            super(ghast);
            this.ghast = ghast;
        }

	    public void tick() {
		    LivingEntity livingEntity = this.ghast.getTarget();
		    if (livingEntity != null) {
			    if (livingEntity.distanceToSqr(this.ghast) < 4096.0D && this.ghast.hasLineOfSight(livingEntity)) {
				    Level level = this.ghast.level();
				    ++this.chargeTime;

				    if (this.chargeTime == 10 && !this.ghast.isSilent()) {
					    level.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
				    }

				    if ((this.chargeTime == 20 || this.chargeTime == 28)) {
					    double e = 4.0D;
					    Vec3 view = this.ghast.getViewVector(1.0F);
					    double f = livingEntity.getX() - (this.ghast.getX() + view.x * e);
					    double g = livingEntity.getY(0.5D) - (this.ghast.getY(0.5D) + 0.5D);
					    double h = livingEntity.getZ() - (this.ghast.getZ() + view.z * e);
					    Vec3 dir = new Vec3(f, g, h).normalize();

					    if (!this.ghast.isSilent()) {
						    level.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
					    }

					    LargeFireball largeFireball = new LargeFireball(level, this.ghast, dir, this.ghast.getExplosionPower());
					    largeFireball.setPos(
							    this.ghast.getX() + view.x * e,
							    this.ghast.getY(0.5D) + 0.5D,
							    this.ghast.getZ() + view.z * e
					    );
					    level.addFreshEntity(largeFireball);

					    if (this.chargeTime == 28) {
						    this.chargeTime = -40;
					    }
				    }
			    } else if (this.chargeTime > 0) {
				    --this.chargeTime;
			    }

			    this.ghast.setCharging(this.chargeTime > 10);
		    }
	    }

    }
}
