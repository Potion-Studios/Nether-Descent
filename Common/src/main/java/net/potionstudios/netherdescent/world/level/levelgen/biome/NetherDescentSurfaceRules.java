package net.potionstudios.netherdescent.world.level.levelgen.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
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

    private static final SurfaceRules.ConditionSource ABOVE_31 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);

    private static final SurfaceRules.RuleSource BEDROCK_RULES = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                    SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                    makeStateRule(Blocks.BEDROCK)
            ),
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), makeStateRule(Blocks.BEDROCK))
    );

    private static final SurfaceRules.RuleSource CRIMSON_GARDENS = makeifTrueRule(NetherDescentBiomes.CRIMSON_GARDENS, SurfaceRules.sequence(
            makeifTrueRule(ABOVE_31, makeifTrueRule(SurfaceRules.ON_FLOOR,
                    makeStateRule(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get()))),
            makeStateRule(Blocks.BLACKSTONE)));

    private static final SurfaceRules.RuleSource EMBUR_BOG = makeifTrueRule(NetherDescentBiomes.EMBUR_BOG, SurfaceRules.sequence(
            makeifTrueRule(ABOVE_31, makeifTrueRule(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                    makeifTrueRule(SurfaceRules.noiseCondition(Noises.PATCH, 0.3D), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get()),
                    makeStateRule(NetherDescentBlocks.EMBUR_NYLIUM.get())
            ))),
            makeStateRule(NetherDescentBlocks.BLUE_NETHERRACK.get())
    ));

    private static final SurfaceRules.RuleSource SYTHIAN_TORRIDS = makeifTrueRule(NetherDescentBiomes.SYTHIAN_TORRIDS, SurfaceRules.sequence(
            makeifTrueRule(ABOVE_31, makeifTrueRule(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                    makeifTrueRule(SurfaceRules.noiseCondition(Noises.PATCH, 0.3D), NetherDescentBlocks.SYTHIAN_SOIL.get()),
                    makeStateRule(NetherDescentBlocks.SYTHIAN_NYLIUM.get())
            ))),
            makeifTrueRule(SurfaceRules.ON_CEILING, makeifTrueRule(SurfaceRules.noiseCondition(Noises.PATCH, 0.3D), NetherDescentBlocks.SYTHIAN_SOIL.get()))
    ));

    private static final SurfaceRules.RuleSource WAILING_GARTH = makeifTrueRule(NetherDescentBiomes.WAILING_GARTH, SurfaceRules.sequence(
            NetherDescentRuleSources.weightedRuleSource(SimpleWeightedRandomList.<SurfaceRules.RuleSource>builder()
                    .add(makeifTrueRule(SurfaceRules.ON_FLOOR, Blocks.SOUL_SOIL), 3)
                    .add(makeifTrueRule(SurfaceRules.ON_FLOOR, NetherDescentBlocks.WAILING_NYLIUM.get()))
                    .build()),
            makeifTrueRule(SurfaceRules.UNDER_FLOOR, Blocks.SOUL_SOIL),
            makeifTrueRule(SurfaceRules.ON_CEILING, Blocks.SOUL_SOIL),
            makeifTrueRule(SurfaceRules.UNDER_CEILING, Blocks.SOUL_SOIL)));


    public static SurfaceRules.RuleSource makeRules() {
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder.add(BEDROCK_RULES);
        builder.add(CRIMSON_GARDENS, EMBUR_BOG, SYTHIAN_TORRIDS, WAILING_GARTH);
        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
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
