package net.potionstudios.netherdescent.world.level.block.set;

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.ArrayList;
import java.util.function.Supplier;

public class NetherDescentBlockSet {

    private static final ArrayList<NetherDescentBlockSet> blockSets = new ArrayList<>();

    private final Supplier<Block> base;
    private final Supplier<StairBlock> stairs;
    private final Supplier<SlabBlock> slab;
    private final Supplier<WallBlock> wall;

    public NetherDescentBlockSet(String name, BlockBehaviour.Properties properties) {
        this.base = NetherDescentBlocks.registerBasicBlockWithItem(name ,properties);
        this.stairs = NetherDescentBlocks.registerBlockItem(name + "_stairs", () -> new StairBlock(base.get().defaultBlockState(), properties));
        this.slab = NetherDescentBlocks.registerBlockItem(name + "_slab", () -> new SlabBlock(properties));
        this.wall = NetherDescentBlocks.registerBlockItem(name + "_wall", () -> new WallBlock(properties));
        blockSets.add(this);
    }

    public NetherDescentBlockSet(String name, String alt, BlockBehaviour.Properties properties) {
        this.base = NetherDescentBlocks.registerBasicBlockWithItem(name ,properties);
        this.stairs = NetherDescentBlocks.registerBlockItem(alt + "_stairs", () -> new StairBlock(base.get().defaultBlockState(), properties));
        this.slab = NetherDescentBlocks.registerBlockItem(alt + "_slab", () -> new SlabBlock(properties));
        this.wall = NetherDescentBlocks.registerBlockItem(alt + "_wall", () -> new WallBlock(properties));
        blockSets.add(this);
    }

    public NetherDescentBlockSet(Supplier<Block> base, Supplier<SlabBlock> slab, Supplier<StairBlock> stairs, Supplier<WallBlock> wall) {
        this.base = base;
        this.stairs = stairs;
        this.slab = slab;
        this.wall = wall;
        blockSets.add(this);
    }

    public NetherDescentBlockSet(String name, MapColor color) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color);
        this.base = NetherDescentBlocks.registerBasicBlockWithItem(name ,properties);
        this.stairs = NetherDescentBlocks.registerBlockItem(name + "_stairs", () -> new StairBlock(base.get().defaultBlockState(), properties));
        this.slab = NetherDescentBlocks.registerBlockItem(name + "_slab", () -> new SlabBlock(properties));
        this.wall = NetherDescentBlocks.registerBlockItem(name + "_wall", () -> new WallBlock(properties));
        blockSets.add(this);
    }

    public NetherDescentBlockSet(String name, String alt, MapColor color) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color);
        this.base = NetherDescentBlocks.registerBasicBlockWithItem(name, properties);
        this.stairs = NetherDescentBlocks.registerBlockItem(alt + "_stairs", () -> new StairBlock(base.get().defaultBlockState(), properties));
        this.slab = NetherDescentBlocks.registerBlockItem(alt + "_slab", () -> new SlabBlock(properties));
        this.wall = NetherDescentBlocks.registerBlockItem(alt + "_wall", () -> new WallBlock(properties));
        blockSets.add(this);
    }

    public Block getBase() {
        return base.get();
    }

    public StairBlock getStairs() {
        return stairs.get();
    }

    public SlabBlock getSlab() {
        return slab.get();
    }

    public WallBlock getWall() {
        return wall.get();
    }

    public BlockFamily getBlockFamily() {
        return BlockFamilies.familyBuilder(getBase()).stairs(getStairs()).slab(getSlab()).wall(getWall()).getFamily();
    }

    public static ArrayList<NetherDescentBlockSet> getBlockSets() {
        return blockSets;
    }
}
