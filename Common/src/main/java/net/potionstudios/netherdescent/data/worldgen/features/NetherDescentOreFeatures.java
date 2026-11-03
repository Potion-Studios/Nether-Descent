package net.potionstudios.netherdescent.data.worldgen.features;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

public class NetherDescentOreFeatures {

    public static final ResourceKey<Feature> ORE_BLUE_NETHER_GOLD = FeaturesUtil.register("ore_blue_nether_gold", _ -> new OreFeature(new BlockMatchTest(NetherDescentBlocks.BLUE_NETHERRACK.get()), NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get().defaultBlockState(), 10));
    public static final ResourceKey<Feature> ORE_BLUE_QUARTZ = FeaturesUtil.register("ore_blue_quartz", _ -> new OreFeature(new BlockMatchTest(NetherDescentBlocks.BLUE_NETHERRACK.get()), NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get().defaultBlockState(), 14));
    public static final ResourceKey<Feature> ORE_PENDORITE = FeaturesUtil.register("ore_pendorite", _ -> new OreFeature(new BlockMatchTest(Blocks.BLACKSTONE), NetherDescentBlocks.PENDORITE_ORE.get().defaultBlockState(), 14));

    protected static void oreFeatures() {
        NetherDescent.LOGGER.info("Registering Nether Descent Ore Features");
    }
}
