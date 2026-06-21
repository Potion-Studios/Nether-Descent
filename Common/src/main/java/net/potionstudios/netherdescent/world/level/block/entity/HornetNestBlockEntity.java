package net.potionstudios.netherdescent.world.level.block.entity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

	static void removeIgnoredHornetTags(CompoundTag tag) {
		for (String string : IGNORED_HORNET_TAGS) {
			tag.remove(string);
		}
	}

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
		List<Entity> list = Lists.<Entity>newArrayList();
		this.stored.removeIf(arg3 -> releaseOccupant(this.level, this.worldPosition, state, arg3, list, releaseStatus, this.savedFlowerPos));
		if (!list.isEmpty()) {
			super.setChanged();
		}

		return list;
	}

	@VisibleForDebug
	public int getOccupantCount() {
		return this.stored.size();
	}

	public void addOccupant(Entity occupant) {
		this.addOccupantWithPresetTicks(occupant, 0);
	}

	public void addOccupantWithPresetTicks(Entity occupant, int ticksInHive) {
		if (this.stored.size() < 3) {
			occupant.stopRiding();
			occupant.ejectPassengers();
			CompoundTag compoundTag = new CompoundTag();
			occupant.save(compoundTag);
			this.storeHornet(compoundTag, ticksInHive);
			if (this.level != null) {
				if (occupant instanceof Hornet hornet && hornet.hasSavedFlowerPos() && (!this.hasSavedFlowerPos() || this.level.random.nextBoolean())) {
					this.savedFlowerPos = hornet.getSavedFlowerPos();
				}

				BlockPos blockPos = this.getBlockPos();
				this.level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
				this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(occupant, this.getBlockState()));
			}

			occupant.discard();
			super.setChanged();
		}
	}

	private boolean hasSavedFlowerPos() {
		return this.savedFlowerPos != null;
	}

	public void storeHornet(CompoundTag entityData, int ticksInHive) {
		this.stored.add(new HornetData(entityData, ticksInHive));
	}

    private static boolean releaseOccupant(
            Level level,
            BlockPos pos,
			BlockState state,
            HornetData data,
            @Nullable List<Entity> storedInHives,
            HornetReleaseStatus releaseStatus,
            @Nullable BlockPos storedFlowerPos
    ) {
        if ((level.isNight() || level.isRaining()) && releaseStatus != HornetReleaseStatus.EMERGENCY) {
            return false;
        } else {
            BlockPos blockPos = pos.below();
            boolean bl = !level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty();
            if (bl && releaseStatus != HornetReleaseStatus.EMERGENCY) {
                return false;
            } else {
	            CompoundTag compoundTag = data.entityData.copy();
                Entity entity = EntityType.loadEntityRecursive(compoundTag, level, arg -> arg);
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
                        entity.moveTo(e, g, h, entity.getYRot(), entity.getXRot());
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
//			if (hornetData.tick())
				if (releaseOccupant(level, pos, state, hornetData, null, HornetReleaseStatus.HORNET_RELEASED, savedFlowerPos)) {
					bl = true;
					iterator.remove();
				}

			hornetData.ticksInHive++;
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
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.stored.clear();
		ListTag listTag = tag.getList("Hornets", 10);

		for (int i = 0; i < listTag.size(); i++) {
			CompoundTag compoundTag = listTag.getCompound(i);
			HornetData hornetData = new HornetData(
					compoundTag.getCompound("EntityData"), compoundTag.getInt("TicksInHive")
			);
			this.stored.add(hornetData);
		}

		this.savedFlowerPos = null;
		if (tag.contains("FlowerPos"))
			this.savedFlowerPos = NbtUtils.readBlockPos(tag.getCompound("FlowerPos"));
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("hornets", writeHornets());
		if (this.hasSavedFlowerPos())
			tag.put("flower_pos", NbtUtils.writeBlockPos(this.savedFlowerPos));
	}

	public ListTag writeHornets() {
		ListTag listTag = new ListTag();

		for (HornetNestBlockEntity.HornetData occupant : this.stored) {
			CompoundTag compoundTag = occupant.entityData.copy();
			compoundTag.remove("UUID");
			CompoundTag compoundTag2 = new CompoundTag();
			compoundTag2.put("EntityData", compoundTag);
			compoundTag2.putInt("TicksInHive", occupant.ticksInHive);
			listTag.add(compoundTag2);
		}

		return listTag;
	}


    public enum HornetReleaseStatus {
        HORNET_RELEASED,
        EMERGENCY
    }

	static class HornetData {
		final CompoundTag entityData;
		int ticksInHive;

		HornetData(CompoundTag entityData, int ticksInHive) {
			HornetNestBlockEntity.removeIgnoredHornetTags(entityData);
			this.entityData = entityData;
			this.ticksInHive = ticksInHive;
		}
	}
}
