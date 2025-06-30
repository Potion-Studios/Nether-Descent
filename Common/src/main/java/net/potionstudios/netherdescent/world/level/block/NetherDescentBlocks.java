package net.potionstudios.netherdescent.world.level.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.custom.NetherDescentNyliumBlock;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configured.NetherDescentConfiguredFeatures;

import java.util.ArrayList;
import java.util.function.Supplier;

public class NetherDescentBlocks {

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> BLOCK_ITEMS = new ArrayList<>();

    public static final ArrayList<Supplier<? extends Block>> cubeAllBlocks = new ArrayList<>();

    public static final Supplier<Block> BLUE_NETHERRACK = registerBlockItem("blue_netherrack", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)));
    public static final NetherDescentBlockSet BLUE_NETHER_BRICKS = new NetherDescentBlockSet("blue_nether_bricks", "blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));

    public static final Supplier<NetherDescentNyliumBlock> WAILING_NYLIUM = registerBlockItem("wailing_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_PURPLE), Blocks.SOUL_SAND, null));
    public static final Supplier<VineBlock> WAILING_VINE = registerBlockItem("wailing_vine", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_PURPLE)));

    public static Supplier<Block> registerBasicBlockWithItem(String key, BlockBehaviour.Properties properties) {
        return registerCubeAllBlockItem(key, () -> new Block(properties));
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = registerBlockItem(key, blockSupplier);
        cubeAllBlocks.add(block);
        return block;
    }

    public static <B extends Block> Supplier<B> registerBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = registerBlock(key, blockSupplier);
        Supplier<Item> item = NetherDescentItems.register(key, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_ITEMS.add(item);
        return block;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        Supplier<B> blockSupplier = register(id, block);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void blocks() {
        NetherDescent.LOGGER.info("Registering Nether Descent Blocks");
    }
}
