package net.potionstudios.netherdescent.forge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.potionstudios.netherdescent.event.ServerEventsHandler;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.entity.animal.NetherDescentWolf;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.HashMap;

public class VanillaCompatForge {

    public static void init() {
        BlockItemFeatures.registerCompostables((item, chance) -> ComposterBlock.COMPOSTABLES.put(item.asItem(), chance.floatValue()));
        ToolInteractions.registerStrippableBlocks((block, stripped) -> {
            AxeItem.STRIPPABLES = new HashMap<>(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(block, stripped);
        });
    }

    public static void registerVanillaCompatEvents(final IEventBus bus) {
        bus.addListener(VanillaCompatForge::registerBrewingRecipes);
        bus.addListener(VanillaCompatForge::registerTillables);
        bus.addListener(VanillaCompatForge::registerFuels);
        bus.addListener(VanillaCompatForge::registerEntityInteract);
        bus.addListener(VanillaCompatForge::onBoneMealUse);
        bus.addListener((PlayerEvent.PlayerLoggedInEvent event) -> ServerEventsHandler.onPlayerJoin((ServerPlayer) event.getEntity()));
        bus.addListener(VanillaCompatForge::onBlockPlace);
    }

    /**
     * Register brewing recipes.
     * @see BrewingRecipeRegisterEvent
     */
    private static void registerBrewingRecipes(final BrewingRecipeRegisterEvent event) {
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
     * Register fuels for the furnace.
     * @see FurnaceFuelBurnTimeEvent
     */
    private static void registerFuels(final FurnaceFuelBurnTimeEvent event) {
        BlockItemFeatures.registerFurnaceFuels((block, burnTime) -> {
            if (event.getItemStack().is(block.asItem())) event.setBurnTime(burnTime);
        });
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
        if (VanillaBonemealHandler.boneMealEventHandler(event.getLevel(), event.getPos(), event.getBlock(), event.getStack()))
            event.setResult(Event.Result.ALLOW);
    }

    /**
     * Handle block placement.
     * @see BlockEvent.EntityPlaceEvent
     */
    private static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        BlockItemFeatures.onPlaceBlock(event.getLevel(), event.getEntity(), event.getPlacedAgainst(), event.getPlacedBlock(), event.getPos());
    }
}
