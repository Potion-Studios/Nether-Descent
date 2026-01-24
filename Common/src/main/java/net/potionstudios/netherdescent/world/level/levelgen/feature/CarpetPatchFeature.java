package net.potionstudios.netherdescent.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.potionstudios.netherdescent.world.level.block.custom.MossyCarpetBlock;
import net.potionstudios.netherdescent.world.level.levelgen.feature.configurations.CarpetPatchFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class CarpetPatchFeature extends Feature<CarpetPatchFeatureConfiguration> {
	public CarpetPatchFeature(Codec<CarpetPatchFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<CarpetPatchFeatureConfiguration> context) {
		CarpetPatchFeatureConfiguration config = context.config();

		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();

        if (config.carpet() instanceof MossyCarpetBlock)
            MossyCarpetBlock.placeAt(config.carpet().defaultBlockState(), level, origin, context.random(), 2);
        else setBlock(level, origin, config.carpet().defaultBlockState());

        return true;
	}
}
