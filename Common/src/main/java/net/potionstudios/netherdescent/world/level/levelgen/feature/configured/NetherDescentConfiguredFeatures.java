package net.potionstudios.netherdescent.world.level.levelgen.feature.configured;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

public class NetherDescentConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_SPROUT = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_sprout", NetherDescentBlocks.EMBUR_SPROUTS, 15);
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMBUR_LILY = ConfiguredFeaturesUtil.createPatchConfiguredFeatureWithBlock("embur_lily", NetherDescentBlocks.EMBUR_LILY, 15);


    public static void configuredFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Configured Features");
    }
}
