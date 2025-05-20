package net.potionstudios.netherdescent.world.level.levelgen.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.surfacerules.NetherDescentRuleSources;

/**
 * Surface rules for the overworld biomes in Nether Descent.
 * @see NetherDescentBiomes
 * @see terrablender.worldgen.TBSurfaceRuleData
 * @author Joseph T. McQuigg
 */
public class NetherDescentSurfaceRules {

    public static final SurfaceRules.ConditionSource ABOVE_31 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);

    public static final SurfaceRules.RuleSource WAILING_GARTH = makeifTrueRule(NetherDescentBiomes.WAILING_GARTH, SurfaceRules.sequence(
            NetherDescentRuleSources.weightedRuleSource(SimpleWeightedRandomList.<SurfaceRules.RuleSource>builder()
                    .add(SurfaceRules.sequence(
                            makeifTrueRule(SurfaceRules.ON_FLOOR, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.UNDER_FLOOR, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.ON_CEILING, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.UNDER_CEILING, Blocks.SOUL_SAND)
                    ), 4)
                    .add(SurfaceRules.sequence(
                            makeifTrueRule(SurfaceRules.ON_FLOOR, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.UNDER_FLOOR, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.ON_CEILING, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.UNDER_CEILING, Blocks.SOUL_SAND)
                    ), 2)
                    .add(SurfaceRules.sequence(
                            //SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, NetherDescentBlocks.WAILING_NYLIUM.get()),
                            makeifTrueRule(SurfaceRules.UNDER_FLOOR, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.ON_CEILING, Blocks.SOUL_SAND),
                            makeifTrueRule(SurfaceRules.UNDER_CEILING, Blocks.SOUL_SAND)
                    ), 2)
                    .build())));
            //makeifTrueRule(ABOVE_31, NetherDescentBlocks.SCORIA_STONE.get())));


    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                WAILING_GARTH
        );
    }

    /**
     * Makes a State Rule for a block.
     * @param block The block to make the rule for.
     * @return The State rule
     */
    private static <B extends Block> SurfaceRules.RuleSource makeStateRule(B block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    /**
     * Makes a rule that checks if a biome is true.
     * @param biome The biome to check for.
     * @param rule(s) The rule(s) to apply if the biome is true.
     * @return the surface rule
     */
    private static SurfaceRules.RuleSource makeifTrueRule(ResourceKey<Biome> biome, SurfaceRules.RuleSource rule) {
        return makeifTrueRule(SurfaceRules.isBiome(biome), rule);
    }

    /**
     * Makes a rule that checks if a condition is true.
     * @param conditionSource The condition to check for.
     * @param block The block to apply if the condition is true.
     * @return the surface rule
     */
    private static <B extends Block> SurfaceRules.RuleSource makeifTrueRule(SurfaceRules.ConditionSource conditionSource, B block) {
        return makeifTrueRule(conditionSource, makeStateRule(block));
    }

    private static SurfaceRules.RuleSource makeifTrueRule(SurfaceRules.ConditionSource ifTrue, SurfaceRules.RuleSource thenRun) {
        return SurfaceRules.ifTrue(ifTrue, thenRun);
    }
}
