package net.potionstudios.netherdescent.fabric;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.event.ServerEventsHandler;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;

public class VanillaCompatFabric {

    public static void init() {
        BlockItemFeatures.registerCompostables(CompostingChanceRegistry.INSTANCE::add);
        NetherDescentBrewingRecipes.buildBrewingRecipes((input, item, result) -> FabricBrewingRecipeRegistry.registerPotionRecipe(input, Ingredient.of(item), result));
        ToolInteractions.registerStrippableBlocks(StrippableBlockRegistry::register);
        ToolInteractions.registerTillables((block, pair) -> TillableBlockRegistry.register(block, pair.getFirst(), pair.getSecond()));
        BlockItemFeatures.registerFurnaceFuels(FuelRegistry.INSTANCE::add);
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(Items.BONE_MEAL) && VanillaBonemealHandler.boneMealEventHandler(world, hitResult.getBlockPos(), world.getBlockState(hitResult.getBlockPos()), stack)) {
                if (world.isClientSide())
                    BoneMealItem.addGrowthParticles(world, hitResult.getBlockPos(), 0);
                return InteractionResult.sidedSuccess(world.isClientSide());
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
        ServerPlayConnectionEvents.JOIN.register((event, sender, server) -> ServerEventsHandler.onPlayerJoin(event.getPlayer()));
        registerLootModifiers();
    }

    private static void registerLootModifiers() {
        LootTableEvents.MODIFY.register((resourceManager, lootDataManager, resourceLocation, builder, source)  -> {
            if (resourceLocation.equals(BuiltInLootTables.NETHER_BRIDGE))
                builder.pool(
		                LootPool.lootPool().with(
				                LootTableReference.lootTableReference(NetherDescent.id("chests/nether_bridge")).build()
		                ).build()
                );
        });
    }
}
