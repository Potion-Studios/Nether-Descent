package net.potionstudios.netherdescent.neoforge;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;

import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.potionstudios.netherdescent.event.ServerEventsHandler;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;

public class VanillaCompatNeoForge {
    public static void registerVanillaCompatEvents(final IEventBus bus) {
        bus.addListener(VanillaCompatNeoForge::onBoneMealUse);
        bus.addListener((PlayerEvent.PlayerLoggedInEvent event) -> ServerEventsHandler.onPlayerJoin((ServerPlayer) event.getEntity()));
        bus.addListener(VanillaCompatNeoForge::onBlockPlace);
    }

    /**
     * Handle bone meal use.
     * @see BonemealEvent
     */
    private static void onBoneMealUse(final BonemealEvent event) {
        if (VanillaBonemealHandler.boneMealEventHandler(event.getLevel(), event.getPos(), event.getState(), event.getStack()))
            event.setSuccessful(true);
    }

    /**
     * Handle block placement.
     * @see BlockEvent.EntityPlaceEvent
     */
    private static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        BlockItemFeatures.onPlaceBlock(event.getLevel(), event.getEntity(), event.getPlacedAgainst(), event.getPlacedBlock(), event.getPos());
    }
}
