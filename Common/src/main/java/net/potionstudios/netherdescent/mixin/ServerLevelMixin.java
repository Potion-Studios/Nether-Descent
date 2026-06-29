package net.potionstudios.netherdescent.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.potionstudios.netherdescent.world.level.ServerChunkTickEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Inject(method = "tickChunk", at = @At("HEAD"))
    private void tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerChunkTickEvents.EVENTS.forEach(listener -> listener.tick((ServerLevel) (Object) this, chunk));
    }
}
