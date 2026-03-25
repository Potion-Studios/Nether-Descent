package net.potionstudios.netherdescent.fabric;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.potionstudios.netherdescent.util.VanillaBonemealHandler;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.entity.animal.NetherDescentWolf;
import net.potionstudios.netherdescent.world.item.brewing.NetherDescentBrewingRecipes;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;

public class VanillaCompatFabric {

    public static void init() {
        BlockItemFeatures.registerCompostables(CompostingChanceRegistry.INSTANCE::add);
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> NetherDescentBrewingRecipes.buildBrewingRecipes(builder::addMix));
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
                return InteractionResult.sidedSuccess(world.isClientSide());
            }
            return InteractionResult.PASS;
        });
    }
}
