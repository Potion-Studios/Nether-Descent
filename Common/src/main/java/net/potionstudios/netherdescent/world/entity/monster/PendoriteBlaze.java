package net.potionstudios.netherdescent.world.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class PendoriteBlaze extends Blaze implements OwnableEntity {
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(PendoriteBlaze.class, EntityDataSerializers.OPTIONAL_UUID);;

	public PendoriteBlaze(EntityType<? extends Blaze> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 8;
	}

	public PendoriteBlaze(Level level, UUID owner) {
		this(NetherDescentEntityType.PENDORITE_BLAZE.get(), level);
		setOwnerUUID(owner);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_OWNERUUID_ID, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		if (this.getOwnerUUID() != null)
			compound.putUUID("Owner", this.getOwnerUUID());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		UUID uuid;
		if (compound.hasUUID("Owner")) {
			uuid = compound.getUUID("Owner");
		} else {
			String string = compound.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
		}

		if (uuid != null)
			setOwnerUUID(uuid);
	}

	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0F).add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.FOLLOW_RANGE, 25.0F);
	}

	@Override
	public @Nullable UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
	}

	public void setOwnerUUID(@Nullable UUID uuid) {
		this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(uuid));
	}

	@Override
	protected boolean shouldDropLoot() {
		return false;
	}
}
