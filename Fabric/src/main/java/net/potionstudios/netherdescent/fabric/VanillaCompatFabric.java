package net.potionstudios.netherdescent.fabric;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.event.ServerEventsHandler;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.entity.animal.NetherDescentWolf;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;

public class VanillaCompatFabric {

    public static void init() {
        BlockItemFeatures.registerCompostables(CompostingChanceRegistry.INSTANCE::add);
        FabricPotionBrewingBuilder.BUILD.register(builder -> NetherDescentBrewingRecipes.buildBrewingRecipes(builder::addMix));
        ToolInteractions.registerStrippableBlocks(StrippableBlockRegistry::register);
        ToolInteractions.registerTillables((block, pair) -> TillableBlockRegistry.register(block, pair.getFirst(), pair.getSecond()));
        BlockItemFeatures.registerFurnaceFuels(FuelRegistry.INSTANCE::add);
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (NetherDescentWolf.onEntityInteract(world, player, entity, player.getItemInHand(hand)) == InteractionResult.SUCCESS)
                return InteractionResult.SUCCESS;
            return InteractionResult.PASS;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(Items.BONE_MEAL) && VanillaBonemealHandler.boneMealEventHandler(world, hitResult.getBlockPos(), world.getBlockState(hitResult.getBlockPos()), stack)) {
                if (world.isClientSide())
                    BoneMealItem.addGrowthParticles(world, hitResult.getBlockPos(), 0);
                return InteractionResult.SUCCESS_SERVER;
            }

            if (!(stack.getItem() instanceof BlockItem blockItem))
                return InteractionResult.PASS;

            BlockPos placedAgainstPos = hitResult.getBlockPos();
            BlockState placedAgainst = world.getBlockState(placedAgainstPos);

            BlockPos placedPos = placedAgainstPos.offset(hitResult.getDirection().getNormal());

            BlockPlaceContext context = new BlockPlaceContext(player, hand, stack, hitResult);

            BlockState placedState = blockItem.getBlock().getStateForPlacement(context);

            if (placedState != null) {
                BlockItemFeatures.onPlaceBlock(
                        world,
                        player,
                        placedAgainst,
                        placedState,
                        placedPos
                );
            }

            return InteractionResult.PASS;
        });
        ServerPlayerEvents.JOIN.register(ServerEventsHandler::onPlayerJoin);
    }
}
