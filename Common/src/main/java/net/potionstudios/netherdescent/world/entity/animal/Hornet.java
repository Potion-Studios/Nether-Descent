package net.potionstudios.netherdescent.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Hornet extends Bee {
    public static final int PLAYER_AGGRO_RADIUS = 10;
    public static final int DOCILE_FLOWER_RADIUS = 15;
    public static final int DOCILE_CHECK_INTERVAL_TICKS = 40;

    public static final int MAX_DISTANCE_FROM_NEST = 16;
    public static final int NEST_SEARCH_RADIUS = 24;
    public static final int NEST_SEARCH_INTERVAL_TICKS = 200;

    public static final int SLEEP_START_TIME = 18000;
    public static final int WAKE_TIME = 2000;

    private static final String NBT_NEST_POS = "HornetNestPos";

    @Nullable
    private BlockPos nestPos;

    private boolean cachedDocile = false;
    private long nextDocileCheckTick = 0L;
    private long nextNestSearchTick = 0L;

    public Hornet(EntityType<? extends Hornet> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Bee.createAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.FLYING_SPEED, 0.7D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    public void setHivePos(BlockPos pos) {
        this.nestPos = pos.immutable();
        this.restrictTo(this.nestPos, MAX_DISTANCE_FROM_NEST);
    }

    @Nullable
    public BlockPos getHornetNestPos() {
        return this.nestPos;
    }

    public boolean hasHornetNest() {
        return this.nestPos != null;
    }

    public void clearHornetNest() {
        this.nestPos = null;
        this.clearRestriction();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> {
            String n = goal.getClass().getSimpleName();
            return n.contains("Hive") || n.equals("ValidateHiveGoal");
        });
        this.goalSelector.addGoal(0, new GoToAndEnterNestGoal(this));
        this.targetSelector.addGoal(0, new AggroNearbyPlayerGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Bee.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        long now = level().getGameTime();

        if (this.nestPos == null && now >= this.nextNestSearchTick) {
            this.nextNestSearchTick = now + NEST_SEARCH_INTERVAL_TICKS;
            BlockPos found = this.findNearestNest((ServerLevel) level(), NEST_SEARCH_RADIUS);
            if (found != null) {
                this.setHivePos(found);
            }
        }

        if (now >= this.nextDocileCheckTick) {
            this.nextDocileCheckTick = now + DOCILE_CHECK_INTERVAL_TICKS;
            this.cachedDocile = this.hasFlowerWithin(level(), this.blockPosition(), DOCILE_FLOWER_RADIUS, 5);
        }

        if (this.cachedDocile && this.getTarget() instanceof Player) {
            super.setTarget(null);
        }
    }

    public boolean isDocile() {
        return this.cachedDocile;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.isDocile() && target instanceof Player) return;

        if (target instanceof Hornet) return;

        super.setTarget(target);
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        if (target instanceof Hornet) return false;
        if (this.isDocile() && target instanceof Player) return false;
        return super.canAttack(target);
    }


    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        return target.hurt(damageSources().sting(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }

    @Override
    @Nullable
    public Hornet getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        return NetherDescentEntityType.HORNET.get().create(level);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.nestPos != null) {
            tag.putLong(NBT_NEST_POS, this.nestPos.asLong());
        }
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(NBT_NEST_POS)) {
            this.nestPos = BlockPos.of(tag.getLong(NBT_NEST_POS));
            if (this.nestPos != null) {
                this.restrictTo(this.nestPos, MAX_DISTANCE_FROM_NEST);
            }
        }
    }

    private boolean shouldSleepNow() {
        long dayTime = this.level().getDayTime() % 24000L;
        return dayTime >= SLEEP_START_TIME || dayTime < WAKE_TIME;
    }

    private boolean shouldDepositNectarNow() {
        if (!this.hasNectar() || this.nestPos == null) return false;
        return this.level().isDay() || this.shouldSleepNow();
    }

    private boolean isTooFarFromNest() {
        if (this.nestPos == null) return false;
        Vec3 c = Vec3.atCenterOf(this.nestPos);
        return this.distanceToSqr(c) > (MAX_DISTANCE_FROM_NEST * MAX_DISTANCE_FROM_NEST);
    }

    private Vec3 getNestEntryPos() {
        if (this.nestPos == null) return this.position();
        BlockPos p = this.nestPos;
        return new Vec3(p.getX() + 0.5D, p.getY() - 0.25D, p.getZ() + 0.5D);
    }

    private boolean tryEnterNest(ServerLevel level) {
        if (this.nestPos == null) return false;
        if (!(level.getBlockEntity(this.nestPos) instanceof HornetNestBlockEntity nest)) {
            this.clearHornetNest();
            return false;
        }
        if (nest.isFull()) return false;

        Vec3 entry = this.getNestEntryPos();
        if (this.distanceToSqr(entry) > 2.0D) return false;

        if (this.hasNectar()) {
            this.setHasNectar(false);
        }

        nest.storeHornet(HornetNestBlockEntity.Occupant.of(this));
        this.discard();
        return true;
    }

    @Nullable
    private BlockPos findNearestNest(ServerLevel level, int radius) {
        BlockPos origin = this.blockPosition();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        int bestDist2 = Integer.MAX_VALUE;
        BlockPos best = null;

        int yRange = 8;
        for (int dy = -yRange; dy <= yRange; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                    if (!level.hasChunkAt(cursor)) continue;

                    if (level.getBlockEntity(cursor) instanceof HornetNestBlockEntity nest && !nest.isFull()) {
                        int d2 = dx * dx + dy * dy + dz * dz;
                        if (d2 < bestDist2) {
                            bestDist2 = d2;
                            best = cursor.immutable();
                        }
                    }
                }
            }
        }
        return best;
    }

    private boolean hasFlowerWithin(Level level, BlockPos center, int radiusXZ, int radiusY) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dy = -radiusY; dy <= radiusY; dy++) {
            for (int dx = -radiusXZ; dx <= radiusXZ; dx++) {
                for (int dz = -radiusXZ; dz <= radiusXZ; dz++) {
                    cursor.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    if (!level.hasChunkAt(cursor)) continue;

                    BlockState state = level.getBlockState(cursor);
                    if (state.is(BlockTags.BEE_GROWABLES) || state.is(BlockTags.FLOWERS)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static final class GoToAndEnterNestGoal extends Goal {
        private final Hornet hornet;
        private int repathCooldown = 0;

        GoToAndEnterNestGoal(Hornet hornet) {
            this.hornet = hornet;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!hornet.hasHornetNest()) return false;
            return hornet.shouldSleepNow() || hornet.isTooFarFromNest() || hornet.shouldDepositNectarNow();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void start() {
            this.repathCooldown = 0;
        }

        @Override
        public void tick() {
            if (!hornet.hasHornetNest()) return;

            if (hornet.tryEnterNest((ServerLevel) hornet.level())) return;

            Vec3 entry = hornet.getNestEntryPos();

            if (this.repathCooldown-- <= 0) {
                this.repathCooldown = 10;
                hornet.getNavigation().moveTo(entry.x, entry.y, entry.z, 1.2D);
            }

            hornet.getLookControl().setLookAt(entry.x, entry.y, entry.z);
        }
    }

    private static final class AggroNearbyPlayerGoal extends Goal {
        private final Hornet hornet;
        private final TargetingConditions conditions;
        @Nullable private Player target;

        AggroNearbyPlayerGoal(Hornet hornet) {
            this.hornet = hornet;
            this.setFlags(EnumSet.of(Flag.TARGET));
            this.conditions = TargetingConditions.forCombat()
                    .range(PLAYER_AGGRO_RADIUS)
                    .selector(p -> !hornet.isDocile() && !p.isSpectator());
        }

        @Override
        public boolean canUse() {
            if (hornet.isDocile() || hornet.getTarget() instanceof Player) return false;

            this.target = hornet.level().getNearestPlayer(this.conditions, hornet);
            return this.target != null;
        }

        @Override
        public void start() {
            hornet.setTarget(this.target);
        }
    }
}
