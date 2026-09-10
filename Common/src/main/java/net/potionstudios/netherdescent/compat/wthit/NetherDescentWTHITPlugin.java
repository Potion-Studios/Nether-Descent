package net.potionstudios.netherdescent.compat.wthit;

import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.ICommonRegistrar;
import mcp.mobius.waila.api.IWailaClientPlugin;
import mcp.mobius.waila.api.IWailaCommonPlugin;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import net.potionstudios.netherdescent.world.level.block.custom.HornetNestBlock;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import org.jspecify.annotations.NonNull;

public class NetherDescentWTHITPlugin implements IWailaClientPlugin, IWailaCommonPlugin {
    @Override
    public void register(@NonNull IClientRegistrar iClientRegistrar) {
        iClientRegistrar.body(HornetNestProvider.INSTANCE, HornetNestBlock.class);
    }

    @Override
    public void register(@NonNull ICommonRegistrar iCommonRegistrar) {
        iCommonRegistrar.dataType(HornetNestDataProvider.OCCUPANTS, HornetNestDataProvider.OCCUPANTS_CODEC);
        iCommonRegistrar.dataType(HornetDataProvider.NEST_POS, HornetDataProvider.NEST_POS_CODEC);
        iCommonRegistrar.blockData(HornetNestDataProvider.INSTANCE, HornetNestBlockEntity.class);
        iCommonRegistrar.entityData(HornetDataProvider.INSTANCE, Hornet.class);
    }
}
