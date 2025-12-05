package net.potionstudios.netherdescent.world.entity.animal;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.ai.village.poi.NetherDescentPoiTypes;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import net.potionstudios.netherdescent.world.level.block.entity.NetherDescentBlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hornet extends Bee {
    public Hornet(EntityType<? extends Bee> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
    }

    HornetGoToNestGoal goToNestGoal;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new HornetEnterNestGoal());
	    this.goalSelector.addGoal(2, new BreedGoal(this, 1.0F));
	    this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, itemStack -> itemStack.is(ItemTags.BEE_FOOD), false));
	    this.beePollinateGoal = new Bee.BeePollinateGoal();
	    this.goalSelector.addGoal(4, this.beePollinateGoal);
	    this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25));
	    this.goalSelector.addGoal(5, new HornetLocateNestGoal());
        goToNestGoal = new HornetGoToNestGoal();
        this.goalSelector.addGoal(5, goToNestGoal);
	    this.goToKnownFlowerGoal = new BeeGoToKnownFlowerGoal();
	    this.goalSelector.addGoal(6, this.goToKnownFlowerGoal);
	    this.goalSelector.addGoal(7, new BeeGrowCropGoal());
	    this.goalSelector.addGoal(8, new HornetWanderGoal());
	    this.goalSelector.addGoal(9, new FloatGoal(this));
	    this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.FLYING_SPEED, 0.7F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    private boolean doesNestSpace(BlockPos hivePos) {
        BlockEntity blockEntity = this.level().getBlockEntity(hivePos);
        if (blockEntity instanceof HornetNestBlockEntity) {
            return !((HornetNestBlockEntity)blockEntity).isFull();
        } else {
            return false;
        }
    }

	public boolean wantsToEnterNest() {
		if (this.stayOutOfHiveCountdown <= 0 && this.getTarget() == null) {
			return this.isTiredOfLookingForNectar() || level().isRaining() || level().isNight() || this.hasNectar();
		}
		return false;
	}

    boolean isNestValid() {
        if (!this.hasHive()) {
            return false;
        } else if (this.isTooFarAway(this.getHivePos())) {
            return false;
        } else {
            BlockEntity blockEntity = this.level().getBlockEntity(this.getHivePos());
            return blockEntity != null && blockEntity.getType() == NetherDescentBlockEntityType.HORNET_NEST.get();
        }
    }

    abstract class BaseHornetGoal extends Goal {
        public abstract boolean canHornetUse();

        public abstract boolean canHornetContinueToUse();

        @Override
        public boolean canUse() {
            return this.canHornetUse() && !Hornet.this.isAngry();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canHornetContinueToUse() && !Hornet.this.isAngry();
        }
    }

    class HornetEnterNestGoal extends BaseHornetGoal {
        @Override
        public void start() {
            if (Hornet.this.level().getBlockEntity(Hornet.this.getHivePos()) instanceof HornetNestBlockEntity hornetNestBlockEntity) {
                hornetNestBlockEntity.addOccupant(Hornet.this);
            }
        }

        @Override
        public boolean canHornetUse() {
            if (Hornet.this.hasHive()
                    && Hornet.this.wantsToEnterNest()
                    && Hornet.this.getHivePos().closerToCenterThan(Hornet.this.position(), 2.0)
                    && Hornet.this.level().getBlockEntity(Hornet.this.getHivePos()) instanceof HornetNestBlockEntity hornetNestBlockEntity) {
                if (!hornetNestBlockEntity.isFull()) {
                    return true;
                }

                Hornet.this.setHivePos(null);
            }

            return false;
        }

        @Override
        public boolean canHornetContinueToUse() {
            return false;
        }
    }

    boolean closerThan(BlockPos pos, int distance) {
        return pos.closerThan(this.blockPosition(), distance);
    }

    @VisibleForDebug
    public class HornetGoToNestGoal extends BaseHornetGoal {
        public static final int MAX_TRAVELLING_TICKS = 600;
        int travellingTicks = Hornet.this.level().random.nextInt(10);
        private static final int MAX_BLACKLISTED_TARGETS = 3;
        final List<BlockPos> blacklistedTargets = Lists.<BlockPos>newArrayList();
        @Nullable
        private Path lastPath;
        private static final int TICKS_BEFORE_HIVE_DROP = 60;
        private int ticksStuck;

        HornetGoToNestGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canHornetUse() {
            return Hornet.this.getHivePos() != null
                    && !Hornet.this.hasRestriction()
                    && Hornet.this.wantsToEnterHive()
                    && !this.hasReachedTarget(Hornet.this.getHivePos())
                    && Hornet.this.level().getBlockState(Hornet.this.getHivePos()).is(NetherDescentBlocks.HORNET_NEST.get());
        }

        @Override
        public boolean canHornetContinueToUse() {
            return this.canHornetUse();
        }

        @Override
        public void start() {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            super.start();
        }

        @Override
        public void stop() {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            Hornet.this.navigation.stop();
            Hornet.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        @Override
        public void tick() {
            if (Hornet.this.getHivePos() != null) {
                this.travellingTicks++;
                if (this.travellingTicks > this.adjustedTickDelay(600)) {
                    this.dropAndBlacklistHive();
                } else if (!Hornet.this.navigation.isInProgress()) {
                    if (!Hornet.this.closerThan(Hornet.this.getHivePos(), 16)) {
                        if (Hornet.this.isTooFarAway(Hornet.this.getHivePos())) {
                            this.dropHive();
                        } else {
                            Hornet.this.pathfindRandomlyTowards(Hornet.this.getHivePos());
                        }
                    } else {
                        boolean bl = this.pathfindDirectlyTowards(Hornet.this.getHivePos());
                        if (!bl) {
                            this.dropAndBlacklistHive();
                        } else if (this.lastPath != null && Hornet.this.navigation.getPath().sameAs(this.lastPath)) {
                            this.ticksStuck++;
                            if (this.ticksStuck > 60) {
                                this.dropHive();
                                this.ticksStuck = 0;
                            }
                        } else {
                            this.lastPath = Hornet.this.navigation.getPath();
                        }
                    }
                }
            }
        }

        private boolean pathfindDirectlyTowards(BlockPos pos) {
            Hornet.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
            Hornet.this.navigation.moveTo(pos.getX(), pos.getY(), pos.getZ(), 2, 1.0);
            return Hornet.this.navigation.getPath() != null && Hornet.this.navigation.getPath().canReach();
        }

        boolean isTargetBlacklisted(BlockPos pos) {
            return this.blacklistedTargets.contains(pos);
        }

        private void blacklistTarget(BlockPos pos) {
            this.blacklistedTargets.add(pos);

            while (this.blacklistedTargets.size() > 3) {
                this.blacklistedTargets.remove(0);
            }
        }

        void clearBlacklist() {
            this.blacklistedTargets.clear();
        }

        private void dropAndBlacklistHive() {
            if (Hornet.this.getHivePos() != null) {
                this.blacklistTarget(Hornet.this.getHivePos());
            }

            this.dropHive();
        }

        private void dropHive() {
            Hornet.this.setHivePos(null);
            Hornet.this.remainingCooldownBeforeLocatingNewHive = 200;
        }

        private boolean hasReachedTarget(BlockPos pos) {
            if (Hornet.this.closerThan(pos, 2)) {
                return true;
            } else {
                Path path = Hornet.this.navigation.getPath();
                return path != null && path.getTarget().equals(pos) && path.canReach() && path.isDone();
            }
        }
    }

    class HornetLocateNestGoal extends BaseHornetGoal {
        @Override
        public boolean canHornetUse() {
            return Hornet.this.remainingCooldownBeforeLocatingNewHive == 0 && !Hornet.this.hasHive() && Hornet.this.wantsToEnterHive();
        }

        @Override
        public boolean canHornetContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            Hornet.this.remainingCooldownBeforeLocatingNewHive = 200;
            List<BlockPos> list = this.findNearbyHivesWithSpace();
            if (!list.isEmpty()) {
                for (BlockPos blockPos : list) {
                    if (!Hornet.this.goToNestGoal.isTargetBlacklisted(blockPos)) {
                        Hornet.this.setHivePos(blockPos);
                        return;
                    }
                }

                Hornet.this.goToNestGoal.clearBlacklist();
                Hornet.this.setHivePos(list.getFirst());
            }
        }

        private List<BlockPos> findNearbyHivesWithSpace() {
            BlockPos blockPos = Hornet.this.blockPosition();
            PoiManager poiManager = ((ServerLevel)Hornet.this.level()).getPoiManager();
            Stream<PoiRecord> stream = poiManager.getInRange((holder) -> holder.is(NetherDescentPoiTypes.HORNET_NEST), blockPos, 20, PoiManager.Occupancy.ANY);
            return stream.map(PoiRecord::getPos).filter((pos) -> level().getBlockEntity(pos) instanceof HornetNestBlockEntity hornetNestBlockEntity && !hornetNestBlockEntity.isFull()).sorted(Comparator.comparingDouble((blockPos2) -> blockPos2.distSqr(blockPos))).collect(Collectors.toList());
        }
    }

	class HornetEnterHiveGoal extends BaseHornetGoal {

		@Override
		public boolean canHornetUse() {
			if (Hornet.this.hasHive() && Hornet.this.wantsToEnterNest() && Hornet.this.getHivePos().closerToCenterThan(Hornet.this.position(), 2.0) && Hornet.this.level().getBlockEntity(Hornet.this.getHivePos()) instanceof HornetNestBlockEntity hornetNestBlockEntity) {
				if (!hornetNestBlockEntity.isFull()) {
					return true;
				}

				Hornet.this.setHivePos(null);
			}
			return false;
		}

		@Override
		public boolean canHornetContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			if (Hornet.this.level().getBlockEntity(Hornet.this.getHivePos()) instanceof HornetNestBlockEntity hornetNestBlockEntity)
				hornetNestBlockEntity.addOccupant(Hornet.this);
		}
	}

    class HornetWanderGoal extends Goal {
        private static final int WANDER_THRESHOLD = 22;

        HornetWanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return Hornet.this.navigation.isDone() && Hornet.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return Hornet.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                Hornet.this.navigation.moveTo(Hornet.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec32;
            if (Hornet.this.isNestValid() && !Hornet.this.closerThan(Hornet.this.getHivePos(), 22)) {
                Vec3 vec3 = Vec3.atCenterOf(Hornet.this.getHivePos());
                vec32 = vec3.subtract(Hornet.this.position()).normalize();
            } else {
                vec32 = Hornet.this.getViewVector(0.0F);
            }

            int i = 8;
            Vec3 vec33 = HoverRandomPos.getPos(Hornet.this, 8, 7, vec32.x, vec32.z, (float) (Math.PI / 2), 3, 1);
            return vec33 != null ? vec33 : AirAndWaterRandomPos.getPos(Hornet.this, 8, 4, -2, vec32.x, vec32.z, (float) (Math.PI / 2));
        }
    }
}
