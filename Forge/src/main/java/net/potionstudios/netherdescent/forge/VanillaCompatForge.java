package net.potionstudios.netherdescent.forge;

import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

public class VanillaCompatForge {

    public static void init() {
        BlockItemFeatures.registerCompostables((item, chance) -> ComposterBlock.COMPOSTABLES.put(item.asItem(), chance.floatValue()));
    }

    public static void registerVanillaCompatEvents(final IEventBus bus) {
        bus.addListener(VanillaCompatForge::registerBrewingRecipes);
        bus.addListener(VanillaCompatForge::registerTillables);
        bus.addListener(VanillaCompatForge::registerFuels);
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
}
