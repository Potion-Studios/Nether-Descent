package net.potionstudios.netherdescent.neoforge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.potionstudios.netherdescent.event.ServerEventsHandler;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.HashMap;

public class VanillaCompatNeoForge {
    public static void init() {
        ToolInteractions.registerStrippableBlocks((block, stripped) -> {
            AxeItem.STRIPPABLES = new HashMap<>(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(block, stripped);
        });
    }

    public static void registerVanillaCompatEvents(final IEventBus bus) {
        bus.addListener(VanillaCompatNeoForge::registerBrewingRecipes);
        bus.addListener(VanillaCompatNeoForge::registerTillables);
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
