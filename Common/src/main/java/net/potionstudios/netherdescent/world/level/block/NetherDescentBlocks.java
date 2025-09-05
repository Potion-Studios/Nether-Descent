package net.potionstudios.netherdescent.world.level.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.custom.*;
import net.potionstudios.netherdescent.world.level.block.plants.*;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.ArrayList;
import java.util.function.Supplier;

public class NetherDescentBlocks {

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> BLOCK_ITEMS = new ArrayList<>();

    public static final ArrayList<Supplier<? extends Block>> cubeAllBlocks = new ArrayList<>();

    public static final Supplier<Block> BLUE_NETHERRACK = registerBlockItem("blue_netherrack", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)));
    public static final NetherDescentBlockSet BLUE_NETHER_BRICKS = new NetherDescentBlockSet("blue_nether_bricks", "blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
	public static final Supplier<FenceBlock> BLUE_NETHER_BRICK_FENCE = registerBlockItem("blue_nether_brick_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICK_FENCE)));
	public static final NetherDescentBlockSet MOSSY_BLUE_NETHER_BRICKS = new NetherDescentBlockSet("mossy_blue_nether_bricks", "mossy_blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
	public static final Supplier<Block> CHISELED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem("chiseled_blue_nether_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_NETHER_BRICKS));
	public static final Supplier<Block> CRACKED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem("cracked_blue_nether_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_NETHER_BRICKS));

    public static final Supplier<NetherDescentNyliumBlock> WAILING_NYLIUM = registerBlockItem("wailing_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_PURPLE), Blocks.SOUL_SAND, null));
    public static final Supplier<VineBlock> WAILING_VINE = registerBlockItem("wailing_vine", () -> new VineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_PURPLE)));

    public static final Supplier<NetherDescentNyliumBlock> EMBUR_NYLIUM = registerBlockItem("embur_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_ORANGE), BLUE_NETHERRACK, null));
    public static final Supplier<Block> EMBUR_GEL_BLOCK = registerBlockItem("embur_gel_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)));
    public static final Supplier<EmburVineBlock> EMBUR_GEL_VINES = registerBlockItem("embur_gel_vines", () -> new EmburVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollission().strength(0.2F).dynamicShape()));
    public static final Supplier<EmburVinePlantBlock> EMBUR_GEL_VINES_PLANT = registerBlock("embur_gel_vines_plant", () -> new EmburVinePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_ORANGE).noCollission().strength(0.2F).dynamicShape()));
    public static final Supplier<NetherSproutsBlock> EMBUR_SPROUTS = registerBlockItem("embur_sprouts", () -> new NetherSproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<EmburLilyBlock> EMBUR_LILY = registerBlock("embur_lily", () -> new EmburLilyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<EmburRootsBlock> EMBUR_ROOTS = registerBlockItem("embur_roots", () -> new EmburRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_EMBUR_ROOTS = registerBlockItem("tall_embur_roots", () -> new NetherDescentDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<EmburCaveMossBlock> EMBUR_CAVE_MOSS = registerBlockItem("embur_cave_moss", () -> new EmburCaveMossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(EmburCaveMossBlock.emission(6)).mapColor(MapColor.COLOR_ORANGE)));

    public static final Supplier<BonemealableFeaturePlacerBlock> EMBUR_MOSS_BLOCK = registerCubeAllBlockItem("embur_moss_block", () -> new BonemealableFeaturePlacerBlock(null, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<MossyCarpetBlock> EMBUR_MOSS_CARPET = registerBlockItem("embur_moss_carpet", () -> new MossyCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<HangingMossBlock> EMBUR_HANGING_MOSS = registerBlockItem("embur_hanging_moss", () -> new HangingMossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollission().strength(0.2F).dynamicShape()));

    public static final Supplier<NetherDescentNyliumBlock> SYTHIAN_NYLIUM = registerBlockItem("sythian_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_YELLOW), Blocks.NETHERRACK, null));

    public static final NetherDescentWoodSet EMBUR = new NetherDescentWoodSet("embur", MapColor.COLOR_PURPLE, NetherDescentWoodSet.LogStem.PEDU, NetherDescentWoodSet.GrowerItem.WART, EMBUR_NYLIUM);
    public static final NetherDescentWoodSet SYTHIAN = new NetherDescentWoodSet("sythian", MapColor.COLOR_YELLOW, SYTHIAN_NYLIUM);
    public static final NetherDescentWoodSet WAILING = new NetherDescentWoodSet("wailing", MapColor.COLOR_PURPLE, WAILING_NYLIUM);

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
