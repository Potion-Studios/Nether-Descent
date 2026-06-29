package net.potionstudios.netherdescent.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.chunk.LevelChunk;
import net.potionstudios.netherdescent.world.entity.ai.village.poi.NetherDescentPoiTypes;
import net.potionstudios.netherdescent.world.level.block.custom.WailingGillsBlock;

import java.util.ArrayList;
import java.util.List;

public class ServerChunkTickEvents {

    public static final List<ServerChunkTickEvents.Tickable> EVENTS = new ArrayList<>();

    static {
        EVENTS.add((serverLevel, chunk) -> {
            if (serverLevel.getGameTime() % 5 == 0) {
                serverLevel.getPoiManager().getInChunk(poiTypeHolder -> poiTypeHolder.is(NetherDescentPoiTypes.WAILING_GILLS), chunk.getPos(), PoiManager.Occupancy.ANY).forEach(poi -> {
                    BlockPos pos = poi.getPos();
                    WailingGillsBlock.tryApplyWailingGillEffects(serverLevel.getBlockState(pos), serverLevel, pos);
                });
            }
        });
    }

    @FunctionalInterface
    public interface Tickable {
        void tick(ServerLevel level, LevelChunk levelChunk);
    }
}
