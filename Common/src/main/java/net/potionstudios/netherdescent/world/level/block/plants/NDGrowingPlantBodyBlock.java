package net.potionstudios.netherdescent.world.level.block.plants;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NDGrowingPlantBodyBlock extends GrowingPlantBodyBlock {
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private final Supplier<? extends GrowingPlantHeadBlock> headBlock;
	public NDGrowingPlantBodyBlock(Properties properties, Supplier<? extends GrowingPlantHeadBlock> headBlock) {
		super(properties, Direction.DOWN, SHAPE, true);
        this.headBlock = headBlock;
	}

	@Override
    @NotNull
    public GrowingPlantHeadBlock getHeadBlock() {
		return headBlock.get();
	}
}
