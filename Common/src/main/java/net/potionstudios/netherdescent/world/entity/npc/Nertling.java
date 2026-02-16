package net.potionstudios.netherdescent.world.entity.npc;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.potionstudios.netherdescent.world.entity.schedule.NetherDescentSchedule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.IntFunction;

public class Nertling extends AbstractVillager implements VariantHolder<Nertling.Variant>, GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Nertling.class, EntityDataSerializers.INT);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of();
    private static final ImmutableList<SensorType<? extends Sensor<? super Nertling>>> SENSOR_TYPES = ImmutableList.of();

    public Nertling(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Brain<Nertling> getBrain() {
        return (Brain<Nertling>) super.getBrain();
    }

    @Override
    protected Brain.@NotNull Provider<Nertling> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
        Brain<Nertling> brain = brainProvider().makeBrain(dynamic);
        registerBrainGoals(brain);
        return brain;
    }

    public void refreshBrain(ServerLevel serverLevel) {
        Brain<Nertling> brain = getBrain();
        brain.stopAll(serverLevel, this);
        this.brain = brain.copyWithoutBehaviors();
        registerBrainGoals(getBrain());
    }

    private void registerBrainGoals(Brain<Nertling> brain) {
        brain.setSchedule(NetherDescentSchedule.NERTLING.get());
        brain.updateActivityFromSchedule(level().getDayTime(), level().getGameTime());
    }

    @Override
    protected void customServerAiStep() {
        level().getProfiler().push("nertlingBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, 0);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Villager.createAttributes();
    }

    @Override
    protected void rewardTradeXp(@NotNull MerchantOffer offer) {

    }

    @Override
    protected void updateTrades() {

    }

    @Override
    protected int getBaseExperienceReward() {
        return getRandom().nextInt(3, 6);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        return null;
    }

    @Override
    public void setVariant(@NotNull Variant variant) {
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    @Override
    public @NotNull Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation IDLE_1 = RawAnimation.begin().then("idle", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation IDLE_2 = RawAnimation.begin().then("idle_2", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation IDLE_3 = RawAnimation.begin().then("idle_3", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation NO = RawAnimation.begin().thenPlay("no");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");
    private static final RawAnimation DEATH = RawAnimation.begin().then("death", Animation.LoopType.HOLD_ON_LAST_FRAME);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(@NotNull AnimationState<E> event) {
        event.getController().transitionLength(0);
        if (this.isDeadOrDying())
            return event.setAndContinue(DEATH);

        AnimationController<E> controller = event.getController();
        RawAnimation currentAnimation = controller.getCurrentRawAnimation();
        boolean finished = controller.hasAnimationFinished() || currentAnimation == null;

        if (finished)
            return switch (getRandom().nextInt(3)) {
                case 0 -> event.setAndContinue(IDLE_1);
                case 1 -> event.setAndContinue(IDLE_2);
                default -> event.setAndContinue(IDLE_3);
            };

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    public enum Variant implements StringRepresentable {
        STANDARD(0, "standard"),
        CYAN(1, "cyan"),
        DARK_BROWN(2, "dark_brown"),
        GREEN(3, "green"),
        MAGENTA(4, "magenta"),
        RED(5, "red");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        private final String name;
        private final int id;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }
    }
}
