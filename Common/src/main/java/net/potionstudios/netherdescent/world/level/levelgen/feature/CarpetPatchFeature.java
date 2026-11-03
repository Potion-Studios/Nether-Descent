package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.potionstudios.netherdescent.world.level.block.custom.HangingMossyCarpetBlock;
import net.potionstudios.netherdescent.world.level.block.custom.NDMossyCarpetBlock;
import org.jspecify.annotations.NonNull;

public record CarpetPatchFeature(Block carpet, boolean hanging) implements Feature {
	public static final MapCodec<CarpetPatchFeature> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					BuiltInRegistries.BLOCK.byNameCodec().fieldOf("carpet").forGetter(CarpetPatchFeature::carpet),
					Codec.BOOL.fieldOf("hanging").orElse(false).forGetter(CarpetPatchFeature::hanging)
			).apply(instance, CarpetPatchFeature::new)
	);

	@Override
	public boolean place(@NonNull WorldGenLevel level, @NonNull ChunkGenerator chunkGenerator, @NonNull RandomSource random, @NonNull BlockPos origin) {
		if (carpet instanceof MossyCarpetBlock)
			NDMossyCarpetBlock.placeAt(carpet.defaultBlockState(), level, origin, random, 2);
		else if (carpet instanceof HangingMossyCarpetBlock)
			HangingMossyCarpetBlock.placeAt(carpet.defaultBlockState().setValue(HangingMossyCarpetBlock.HANGING, hanging), level, origin, random, 2);
		else setBlock(level, origin, carpet.defaultBlockState());

		return true;
	}

	@Override
	public @NonNull MapCodec<? extends Feature> codec() {
		return CODEC;
	}
}
