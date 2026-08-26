package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record PottedBlock(Supplier<? extends Block> block, Supplier<? extends Block> pottedBlock) implements Supplier<Block> {
    public PottedBlock(@NotNull Supplier<? extends Block> block, @NotNull Supplier<? extends Block> pottedBlock) {
        this.block = block;
        this.pottedBlock = pottedBlock;
    }

    public PottedBlock(String id, @NotNull Supplier<? extends Block> block) {
        this(block, NetherDescentBlocks.registerBlock("potted_" + id, properties -> PlatformHandler.PLATFORM_HANDLER.createPottedBlock(block, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    }

    public Item getItem() {
        return block.get().asItem();
    }

    public Block getPottedBlock() {
        return pottedBlock.get();
    }

    public BlockState getBlockState() {
        return block.get().defaultBlockState();
    }

    @Override
    public Block get() {
        return block.get();
    }
}