package net.potionstudios.netherdescent.world.level.block.plants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NDGrowingPlantBodyBlock extends GrowingPlantBodyBlock {
	public static final MapCodec<NDGrowingPlantBodyBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    propertiesCodec(),
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("head_block").forGetter(NDGrowingPlantBodyBlock::getHeadBlock)
            ).apply(instance, NDGrowingPlantBodyBlock::new)
    );
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private final Supplier<? extends GrowingPlantHeadBlock> headBlock;
	public NDGrowingPlantBodyBlock(Properties properties, Supplier<? extends GrowingPlantHeadBlock> headBlock) {
		super(properties, Direction.DOWN, SHAPE, true);
        this.headBlock = headBlock;
	}

    /**
     * @deprecated Use the constructor with Supplier instead.
     * This is only kept for compatibility with CODEC
     */
    @Deprecated
    private NDGrowingPlantBodyBlock(Properties properties, Block headBlock) {
        this(properties, () -> (GrowingPlantHeadBlock) headBlock);
    }

	@Override
	protected @NotNull MapCodec<? extends GrowingPlantBodyBlock> codec() {
		return CODEC;
	}

	@Override
    @NotNull
    public GrowingPlantHeadBlock getHeadBlock() {
		return headBlock.get();
	}
}
