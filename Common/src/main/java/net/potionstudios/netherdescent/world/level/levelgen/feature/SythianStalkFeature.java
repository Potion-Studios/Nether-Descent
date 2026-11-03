package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.SythianStalkBlock;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public record SythianStalkFeature(boolean hanging) implements Feature {
	public static final MapCodec<SythianStalkFeature> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.BOOL.fieldOf("hanging").forGetter(SythianStalkFeature::hanging)
			).apply(instance, SythianStalkFeature::new)
	);
	
    private static final Supplier<BlockState> STALK = Suppliers.memoize(() ->
            NetherDescentBlocks.SYTHIAN_STALK.getBlockState()
                    .setValue(SythianStalkBlock.AGE, 1)
                    .setValue(SythianStalkBlock.LEAVES, BambooLeaves.NONE)
                    .setValue(SythianStalkBlock.STAGE, 0));

    private static final Supplier<BlockState> STALK_FINAL_LARGE = Suppliers.memoize(() ->
            STALK.get().setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE).setValue(SythianStalkBlock.STAGE, 1));

    private static final Supplier<BlockState> STALK_TOP_LARGE = Suppliers.memoize(() ->
            STALK.get().setValue(SythianStalkBlock.LEAVES, BambooLeaves.LARGE));

    private static final Supplier<BlockState> STALK_TOP_SMALL = Suppliers.memoize(() ->
            STALK.get().setValue(SythianStalkBlock.LEAVES, BambooLeaves.SMALL));

	@Override
	public boolean place(@NonNull WorldGenLevel level, @NonNull ChunkGenerator chunkGenerator, @NonNull RandomSource random, @NonNull BlockPos origin) {
		int i = 0;
		BlockPos.MutableBlockPos mutableBlockPos = origin.mutable();
		if (level.isEmptyBlock(origin)) {
			if (NetherDescentBlocks.SYTHIAN_STALK.getBlockState().setValue(SythianStalkBlock.HANGING, hanging).canSurvive(level, mutableBlockPos)) {
				int j = random.nextInt(12) + 5;
				Direction move = hanging ? Direction.DOWN : Direction.UP;
				for (int k = 0; k < j && level.isEmptyBlock(mutableBlockPos); k++) {
					level.setBlock(mutableBlockPos, STALK.get().setValue(SythianStalkBlock.HANGING, hanging), 2);
					mutableBlockPos.move(move);
				}

				int check = hanging ? origin.getY() - mutableBlockPos.getY() : mutableBlockPos.getY() - origin.getY();

				if (check >= 3) {
					if (!level.isEmptyBlock(mutableBlockPos.offset(0, hanging ? -3 : 3, 0)))
						mutableBlockPos.move(move.getOpposite(), 3);
					level.setBlock(mutableBlockPos, STALK_FINAL_LARGE.get().setValue(SythianStalkBlock.HANGING, hanging), 2);
					level.setBlock(mutableBlockPos.move(move), STALK_TOP_LARGE.get().setValue(SythianStalkBlock.HANGING, hanging), 2);
					level.setBlock(mutableBlockPos.move(move), STALK_TOP_SMALL.get().setValue(SythianStalkBlock.HANGING, hanging), 2);
				}
			}

			i++;
		}
		return i > 0;
	}

	@Override
	public @NonNull MapCodec<? extends Feature> codec() {
		return CODEC;
	}
}
