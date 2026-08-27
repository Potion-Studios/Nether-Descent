package net.potionstudios.netherdescent.compat.wthit;

import mcp.mobius.waila.api.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public enum HornetNestDataProvider implements IDataProvider<HornetNestBlockEntity> {
    INSTANCE;

    public static final IData.Type<OccupantsData> OCCUPANTS = IData.createType(NetherDescent.id("hornet.occupants"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OccupantsData> OCCUPANTS_CODEC = StreamCodec.composite(
            StreamCodec.composite(
                    ByteBufCodecs.registry(Registries.ENTITY_TYPE), OccupantsData.Occupant::entityType,
                    WailaHelper.nullable(ByteBufCodecs.STRING_UTF8), OccupantsData.Occupant::customName,
                    OccupantsData.Occupant::new).apply(ByteBufCodecs.list()), OccupantsData::occupants,
            OccupantsData::new);

    @Override
    public void appendData(IDataWriter iDataWriter, IServerAccessor<HornetNestBlockEntity> iServerAccessor, IPluginConfig iPluginConfig) {
        if (iPluginConfig.getBoolean(Identifier.withDefaultNamespace("bee.hive_occupants"))) {
            List<HornetNestBlockEntity.HornetData> stored = iServerAccessor.getTarget().stored;
            if (!stored.isEmpty()) {
                var occupants = new ArrayList<OccupantsData.Occupant>(stored.size());

                for (var beeData : stored) {
                    var beeNbt = beeData.toOccupant().entityData().getUnsafe();

                    var entityType = EntityType.by(beeNbt);
                    if (entityType.isEmpty()) continue;

                    var customName = beeNbt.contains("CustomName", Tag.TAG_STRING)
                            ? beeNbt.getString("CustomName")
                            : null;

                    occupants.add(new OccupantsData.Occupant(entityType.get(), customName));
                }

                if (!occupants.isEmpty()) iDataWriter.addImmediate(new OccupantsData(occupants));
            }
        }
    }

    public record OccupantsData(List<Occupant> occupants) implements IData {

        public record Occupant(EntityType<?> entityType, @Nullable String customName) {}

        @Override
        public Type<? extends IData> type() {
            return OCCUPANTS;
        }

    }
}
