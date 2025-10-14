package net.potionstudios.netherdescent.world.level.block.plants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NDGrowingPlantHeadBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<NDGrowingPlantHeadBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    propertiesCodec(),
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body_block").forGetter(NDGrowingPlantHeadBlock::getBodyBlock)
            ).apply(instance, NDGrowingPlantHeadBlock::new)
    );
	private static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    private final Supplier<? extends Block> bodyBlock;

	public NDGrowingPlantHeadBlock(Properties properties, Supplier<? extends Block> bodyBlock) {
		super(properties, Direction.DOWN, SHAPE, true, 0.1D);
        this.bodyBlock = bodyBlock;
	}

    protected NDGrowingPlantHeadBlock(Properties properties, Block bodyBlock) {
        this(properties, () -> bodyBlock);
    }

	@Override
	protected @NotNull MapCodec<? extends GrowingPlantHeadBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull Block getBodyBlock() {
		return bodyBlock.get();
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(@NotNull RandomSource random) {
		return NetherVines.getBlocksToGrowWhenBonemealed(random);
	}

	@Override
	protected boolean canGrowInto(@NotNull BlockState state) {
		return NetherVines.isValidGrowthState(state);
	}
}
