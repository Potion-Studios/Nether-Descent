package net.potionstudios.netherdescent.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.core.component.NetherDescentDataComponents;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class HornetNestBlockEntity extends BlockEntity {
	static final List<String> IGNORED_HORNET_TAGS = Arrays.asList(
			"Air",
			"ArmorDropChances",
			"ArmorItems",
			"Brain",
			"CanPickUpLoot",
			"DeathTime",
			"FallDistance",
			"FallFlying",
			"HandDropChances",
			"HandItems",
			"HurtByTimestamp",
			"HurtTime",
			"LeftHanded",
			"Motion",
			"NoGravity",
			"OnGround",
			"PortalCooldown",
			"Pos",
			"Rotation",
			"SleepingX",
			"SleepingY",
			"SleepingZ",
			"CannotEnterHiveTicks",
			"TicksSincePollination",
			"CropsGrownSincePollination",
			"hive_pos",
			"Passengers",
			"leash",
			"UUID"
	);
	public final List<HornetData> stored = Lists.newArrayList();
	@Nullable
	private BlockPos savedFlowerPos;

	public HornetNestBlockEntity(BlockPos pos, BlockState blockState) {
		super(NetherDescentBlockEntityType.HORNET_NEST.get(), pos, blockState);
	}

	public boolean isEmpty() {
		return this.stored.isEmpty();
	}

	public boolean isFull() {
		return this.stored.size() == 3;
	}

    public void emptyAllLivingFromHive(@Nullable Player player, BlockState state, HornetReleaseStatus releaseStatus) {
        List<Entity> list = this.releaseAllOccupants(state, releaseStatus);
        if (player != null) {
            for (Entity entity : list) {
                if (entity instanceof Bee bee && player.position().distanceToSqr(entity.position()) <= 16.0) {
                        bee.setStayOutOfHiveCountdown(400);
                }
            }
        }
    }

	private List<Entity> releaseAllOccupants(BlockState state, HornetReleaseStatus releaseStatus) {
		List<Entity> list = Lists.newArrayList();
		this.stored.removeIf(beeData -> releaseOccupant(this.level, this.worldPosition, beeData.toOccupant(), list, releaseStatus, this.savedFlowerPos));
		if (!list.isEmpty())
			super.setChanged();

		return list;
	}

	@VisibleForDebug
	public int getOccupantCount() {
		return this.stored.size();
	}

	public void addOccupant(Entity occupant) {
		if (this.stored.size() < 3) {
			occupant.stopRiding();
			occupant.ejectPassengers();
			this.storeHornet(Occupant.of(occupant));
			if (this.level != null) {
				if (occupant instanceof Hornet hornet && hornet.hasSavedFlowerPos() && (!this.hasSavedFlowerPos() || this.level.random.nextBoolean()))
					this.savedFlowerPos = hornet.getSavedFlowerPos();

				BlockPos blockPos = this.getBlockPos();
				this.level
						.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
				this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(occupant, this.getBlockState()));
			}

			occupant.discard();
			super.setChanged();
		}
	}

	private boolean hasSavedFlowerPos() {
		return this.savedFlowerPos != null;
	}

	public void storeHornet(Occupant occupant) {
		this.stored.add(new HornetData(occupant));
	}

    private static boolean releaseOccupant(
            Level level,
            BlockPos pos,
            Occupant occupant,
            @Nullable List<Entity> storedInHives,
            HornetReleaseStatus releaseStatus,
            @Nullable BlockPos storedFlowerPos
    ) {
        if ((level.isDarkOutside() || level.isRaining()) && releaseStatus != HornetReleaseStatus.EMERGENCY) {
            return false;
        } else {
            BlockPos blockPos = pos.below();
            boolean bl = !level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty();
            if (bl && releaseStatus != HornetReleaseStatus.EMERGENCY) {
                return false;
            } else {
                Entity entity = occupant.createEntity(level, pos);
                if (entity != null) {
                    if (entity instanceof Hornet hornet) {
                        if (storedFlowerPos != null && !hornet.hasSavedFlowerPos() && level.getRandom().nextFloat() < 0.9F)
                            hornet.setSavedFlowerPos(storedFlowerPos);

                        if (storedInHives != null)
                            storedInHives.add(hornet);

                        float f = entity.getBbWidth();
                        double d = bl ? 0.0 : 0.55 + f / 2.0F;
                        double e = pos.getX() + 0.5 + d * Direction.DOWN.getStepX();
                        double g = pos.getY() - 0.5 - entity.getBbHeight() / 2.0F;
                        double h = pos.getZ() + 0.5 + d * Direction.DOWN.getStepZ();
                        entity.snapTo(e, g, h, entity.getYRot(), entity.getXRot());
                    }

                    level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, level.getBlockState(pos)));
                    return level.addFreshEntity(entity);
                }
            }
        }
        return false;
    }

	private static void tickOccupants(Level level, BlockPos pos, BlockState state, List<HornetData> data, @Nullable BlockPos savedFlowerPos) {
		boolean bl = false;
		Iterator<HornetData> iterator = data.iterator();

		while (iterator.hasNext()) {
			HornetData hornetData = iterator.next();
			if (hornetData.tick())
				if (releaseOccupant(level, pos, hornetData.toOccupant(), null, HornetReleaseStatus.HORNET_RELEASED, savedFlowerPos)) {
					bl = true;
					iterator.remove();
				}
		}

		if (bl) setChanged(level, pos, state);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, HornetNestBlockEntity hornetNest) {
		tickOccupants(level, pos, state, hornetNest.stored, hornetNest.savedFlowerPos);
		if (!hornetNest.stored.isEmpty() && level.getRandom().nextDouble() < 0.005) {
			double d = pos.getX() + 0.5;
			double e = pos.getY();
			double f = pos.getZ() + 0.5;
			level.playSound(null, d, e, f, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	protected void loadAdditional(@NonNull ValueInput input) {
		super.loadAdditional(input);
		this.stored.clear();
		(input.read("hornets", Occupant.LIST_CODEC).orElse(List.of())).forEach(this::storeHornet);
		this.savedFlowerPos = input.read("flower_pos", BlockPos.CODEC).orElse(null);
	}

	@Override
	protected void saveAdditional(@NonNull ValueOutput output) {
		super.saveAdditional(output);
		output.store("hornets", Occupant.LIST_CODEC, this.getHornets());
		output.storeNullable("flower_pos", BlockPos.CODEC, this.savedFlowerPos);
	}

	@Override
	protected void applyImplicitComponents(@NonNull DataComponentGetter componentGetter) {
		super.applyImplicitComponents(componentGetter);
		this.stored.clear();
		List<Occupant> list = componentGetter.getOrDefault(NetherDescentDataComponents.HORNETS.get(), List.of());
		list.forEach(this::storeHornet);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
		super.collectImplicitComponents(components);
		components.set(NetherDescentDataComponents.HORNETS.get(), getHornets());
	}

	@Override
	public void removeComponentsFromTag(@NonNull ValueOutput output) {
		super.removeComponentsFromTag(output);
		output.discard("hornets");
	}

	private List<Occupant> getHornets() {
		return this.stored.stream().map(HornetData::toOccupant).toList();
	}

	public static class HornetData {
		private final Occupant occupant;
		private int ticksInNest;

		HornetData(Occupant occupant) {
			this.occupant = occupant;
			this.ticksInNest = occupant.ticksInNest();
		}

		public boolean tick() {
			return this.ticksInNest++ > this.occupant.minTicksInNest;
		}

		public Occupant toOccupant() {
			return new Occupant(this.occupant.entityData, this.ticksInNest, this.occupant.minTicksInNest);
		}
	}

    public enum HornetReleaseStatus {
        HORNET_RELEASED,
        EMERGENCY
    }

	public record Occupant(TypedEntityData<EntityType<?>> entityData, int ticksInNest, int minTicksInNest) {
		public static final Codec<Occupant> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								TypedEntityData.codec(EntityType.CODEC).fieldOf("entity_data").forGetter(Occupant::entityData),
								Codec.INT.fieldOf("ticks_in_nest").forGetter(Occupant::ticksInNest),
								Codec.INT.fieldOf("min_ticks_in_nest").forGetter(Occupant::minTicksInNest)
						)
						.apply(instance, Occupant::new)
		);
		public static final Codec<List<Occupant>> LIST_CODEC = CODEC.listOf();
		public static final StreamCodec<RegistryFriendlyByteBuf, Occupant> STREAM_CODEC = StreamCodec.composite(
				TypedEntityData.streamCodec(EntityType.STREAM_CODEC),
				Occupant::entityData,
				ByteBufCodecs.VAR_INT,
				Occupant::ticksInNest,
				ByteBufCodecs.VAR_INT,
				Occupant::minTicksInNest,
				Occupant::new
		);

		public static Occupant of(Entity entity) {
			Occupant var5;
			try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(entity.problemPath(), NetherDescent.LOGGER)) {
				TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, entity.registryAccess());
				entity.save(tagValueOutput);
				Objects.requireNonNull(tagValueOutput);
				IGNORED_HORNET_TAGS.forEach(tagValueOutput::discard);
				CompoundTag compoundTag = tagValueOutput.buildResult();
				boolean bl = compoundTag.getBooleanOr("HasNectar", false);
				var5 = new Occupant(TypedEntityData.of(entity.getType(), compoundTag), 0, bl ? 2400 : 600);
			}

			return var5;
		}

		public static Occupant create(int ticksInHive) {
			CompoundTag compoundTag = new CompoundTag();
			compoundTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(NetherDescentEntityType.HORNET.get()).toString());
			return new Occupant(TypedEntityData.of(NetherDescentEntityType.HORNET.get(), new CompoundTag()), ticksInHive, 600);
		}

		@Nullable
		public Entity createEntity(Level level, BlockPos pos) {
			CompoundTag compoundTag = this.entityData.copyTagWithoutId();
			IGNORED_HORNET_TAGS.forEach(compoundTag::remove);
			Entity entity = EntityType.loadEntityRecursive(compoundTag, level, EntitySpawnReason.LOAD, entityx -> entityx);
			if (entity instanceof Hornet hornet) {
				hornet.setHivePos(pos);
				setHornetReleaseData(this.ticksInNest, hornet);

				return entity;
			} else {
				return null;
			}
		}

		private static void setHornetReleaseData(int ticksInHive, Hornet hornet) {
			int i = hornet.getAge();
			if (i < 0) {
				hornet.setAge(Math.min(0, i + ticksInHive));
			} else if (i > 0) {
				hornet.setAge(Math.max(0, i - ticksInHive));
			}

			hornet.setInLoveTime(Math.max(0, hornet.getInLoveTime() - ticksInHive));
		}
	}
}
