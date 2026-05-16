package net.potionstudios.netherdescent.neoforge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.potionstudios.netherdescent.event.ServerEventsHandler;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.entity.animal.NetherDescentWolf;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

public class VanillaCompatNeoForge {

    public static void registerVanillaCompatEvents(final IEventBus bus) {
        bus.addListener(VanillaCompatNeoForge::registerBrewingRecipes);
        bus.addListener(VanillaCompatNeoForge::registerTillables);
        bus.addListener(VanillaCompatNeoForge::registerEntityInteract);
        bus.addListener(VanillaCompatNeoForge::onBoneMealUse);
        bus.addListener((PlayerEvent.PlayerLoggedInEvent event) -> ServerEventsHandler.onPlayerJoin((ServerPlayer) event.getEntity()));
        bus.addListener(VanillaCompatNeoForge::onBlockPlace);
    }

    /**
     * Register brewing recipes.
     * @see RegisterBrewingRecipesEvent
     */
    private static void registerBrewingRecipes(final RegisterBrewingRecipesEvent event) {
        NetherDescentBrewingRecipes.buildBrewingRecipes(event.getBuilder()::addMix);
    }

    /**
     * Register tillable blocks.
     * @see BlockEvent.BlockToolModificationEvent
     */
    private static void registerTillables(final BlockEvent.BlockToolModificationEvent event) {
        BlockState state = event.getState();
        if (state.is(NetherDescentBlocks.SYTHIAN_SOIL.get()))
            event.setFinalState(NetherDescentBlocks.SYTHIAN_FARMLAND.get().defaultBlockState());
    }

    /**
     * Register entity interaction events.
     * @see PlayerInteractEvent.EntityInteract
     */
    private static void registerEntityInteract(final PlayerInteractEvent.EntityInteract event) {
        if (NetherDescentWolf.onEntityInteract(event.getLevel(), event.getEntity(), event.getTarget(), event.getItemStack()) == InteractionResult.SUCCESS) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
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
