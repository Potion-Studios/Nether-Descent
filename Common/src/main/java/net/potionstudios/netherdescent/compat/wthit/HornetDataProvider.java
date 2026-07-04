package net.potionstudios.netherdescent.compat.wthit;

import mcp.mobius.waila.api.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;

public enum HornetDataProvider implements IDataProvider<Hornet> {

    INSTANCE;

    public static final IData.Type<NestPosData> NEST_POS = IData.createType(NetherDescent.id("hornet.nest_pos"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NestPosData> NEST_POS_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, NestPosData::pos,
            NestPosData::new);

    @Override
    public void appendData(IDataWriter iDataWriter, IServerAccessor<Hornet> iServerAccessor, IPluginConfig iPluginConfig) {
        if (iPluginConfig.getBoolean(Identifier.withDefaultNamespace("bee.hive_pos"))) {
            BlockPos nestPos = iServerAccessor.getTarget().getHivePos();
            if (nestPos != null)
                iDataWriter.addImmediate(new NestPosData(nestPos));
        }

    }

    public record NestPosData(BlockPos pos) implements IData {

        @Override
        public Type<? extends IData> type() {
            return NEST_POS;
        }
    }
}
