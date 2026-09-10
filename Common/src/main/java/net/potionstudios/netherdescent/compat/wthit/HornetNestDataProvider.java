package net.potionstudios.netherdescent.compat.wthit;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.util.WCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public enum HornetNestDataProvider implements IDataProvider<HornetNestBlockEntity> {

    INSTANCE;

    public static final IData.Type<OccupantsData> OCCUPANTS = IData.createType(NetherDescent.id("hornet.occupants"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OccupantsData> OCCUPANTS_CODEC = StreamCodec.composite(
            StreamCodec.composite(
                    ByteBufCodecs.registry(Registries.ENTITY_TYPE), OccupantsData.Occupant::entityType,
                    WCodecs.nullable(ByteBufCodecs.STRING_UTF8), OccupantsData.Occupant::customName,
                    OccupantsData.Occupant::new).apply(ByteBufCodecs.list()), OccupantsData::occupants,
            OccupantsData::new);

    @Override
    public void appendData(@NonNull IDataWriter iDataWriter, @NonNull IServerAccessor<HornetNestBlockEntity> iServerAccessor, IPluginConfig iPluginConfig) {
        if (iPluginConfig.getBoolean(Identifier.withDefaultNamespace("bee.hive_occupants"))) {
            List<HornetNestBlockEntity.HornetData> stored = iServerAccessor.getTarget().stored;
            if (!stored.isEmpty()) {
                List<OccupantsData.Occupant> occupants = new ArrayList<>(stored.size());

                for (HornetNestBlockEntity.HornetData hornetData : stored) {
                    EntityType<?> entityType = hornetData.toOccupant().entityData().type();
                    CompoundTag hornetNbt = hornetData.toOccupant().entityData().copyTagWithoutId();
                    String customName = hornetNbt.getString("CustomName").orElse(null);
                    occupants.add(new OccupantsData.Occupant(entityType, customName));
                }

                iDataWriter.addImmediate(new OccupantsData(occupants));
            }
        }
    }

    public record OccupantsData(List<Occupant> occupants) implements IData {

        public record Occupant(EntityType<?> entityType, String customName) {}

        @Override
        public @NonNull Type<? extends IData> type() {
            return OCCUPANTS;
        }

    }
}