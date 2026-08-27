package net.potionstudios.netherdescent.world.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.config.configs.MobSpawnConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PendoriteBlaze extends Blaze implements NeutralMob {
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(PendoriteBlaze.class, EntityDataSerializers.BYTE);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

	public PendoriteBlaze(EntityType<? extends Blaze> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 8;
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(4, new PendoriteBlazeAttackGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector
                .addGoal(
                        3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (livingEntity, level) -> livingEntity instanceof Enemy && !(livingEntity instanceof Creeper))
                );
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

	@Override
	public boolean checkSpawnRules(@NotNull LevelAccessor level, @NotNull EntitySpawnReason spawnReason) {
		return MobSpawnConfig.INSTANCE.pendorite_blaze && super.checkSpawnRules(level, spawnReason);
	}

	@Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("PlayerCreated", this.isPlayerCreated());
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
        this.readPersistentAngerSaveData(this.level(), compound);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.FOLLOW_RANGE, 25.0F).add(Attributes.MAX_HEALTH, 40.0F);
	}

    @Override
    public void aiStep() {
        if (!level().isClientSide())
            updatePersistentAnger((ServerLevel) level(), true);
		super.aiStep();
    }

    @Override
	protected boolean shouldDropLoot() {
		return false;
	}

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.remainingPersistentAngerTime = remainingPersistentAngerTime;
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public boolean isPlayerCreated() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setPlayerCreated(boolean playerCreated) {
        byte b = this.entityData.get(DATA_FLAGS_ID);
        if (playerCreated) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b | 1));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b & -2));
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean canAttackType(@NotNull EntityType<?> entityType) {
        if (this.isPlayerCreated() && entityType == EntityType.PLAYER) {
            return false;
        } else {
            return entityType != EntityType.CREEPER && super.canAttackType(entityType);
        }
    }

	@Override
	public boolean canBeLeashed() {
		return true;
	}

	static class PendoriteBlazeAttackGoal extends Blaze.BlazeAttackGoal {
		private final PendoriteBlaze blaze;
		private int attackStep;
		private int attackTime;
		private int lastSeen;

		public PendoriteBlazeAttackGoal(PendoriteBlaze blaze) {
			super(blaze);
			this.blaze = blaze;
		}

		@Override
		public boolean canUse() {
			LivingEntity livingEntity = this.blaze.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.blaze.canAttack(livingEntity) && livingEntity.getType() != blaze.getType();
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
						if (this.blaze.doHurtTarget(getServerLevel(this.blaze), livingEntity))
							livingEntity.setSharedFlagOnFire(true);
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
						} else if (this.attackStep <= 3) {
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
								SmallFireball smallFireball = new SmallFireball(this.blaze.level(), this.blaze, vec3.normalize());
								smallFireball.setPos(smallFireball.getX(), this.blaze.getY(0.5) + 0.5, smallFireball.getZ());
								this.blaze.level().addFreshEntity(smallFireball);
							}
						}
					}

					this.blaze.getLookControl().setLookAt(livingEntity, 10.0F, 10.0F);
				} else if (this.lastSeen < 5) {
					this.blaze.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
				}
			}
		}

		private double getFollowDistance() {
			return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
		}
	}
}
