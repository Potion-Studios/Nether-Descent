package net.potionstudios.netherdescent.world.level.levelgen.biome;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.data.worldgen.material.VanillaMaterialRules;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.surfacerules.NetherDescentRuleSources;

import java.util.Map;

/**
 * Surface rules for the overworld biomes in Nether Descent.
 * @see NetherDescentBiomes
 * @author Joseph T. McQuigg
 */
public class NetherDescentMaterialRules {

    public static final Map<ResourceKey<MaterialRule>, MaterialRuleFactory> MATERIAL_RULES_FACTORIES = new Reference2ObjectOpenHashMap<>();

    private static final MaterialCondition ABOVE_31 = MaterialRules.yBlockCheck(VerticalAnchor.absolute(31), 0);

    public static final ResourceKey<MaterialRule> NETHER = register("nether", materialRuleBootstrapContext -> {
        HolderGetter<MaterialRule> rules = materialRuleBootstrapContext.lookup(Registries.MATERIAL_RULE);
        HolderGetter<MaterialCondition> conditions = materialRuleBootstrapContext.lookup(Registries.MATERIAL_CONDITION);
        HolderGetter<Biome> biomes = materialRuleBootstrapContext.lookup(Registries.BIOME);
        MaterialCondition onFloor = MaterialRules.getCondition(conditions, VanillaMaterialConditions.ON_FLOOR);
        MaterialCondition underCeiling = MaterialRules.getCondition(conditions, VanillaMaterialConditions.UNDER_CEILING);
        MaterialCondition onCeiling = MaterialRules.getCondition(conditions, VanillaMaterialConditions.ON_CEILING);

        return MaterialRules.sequence(
                MaterialRules.getRule(rules, VanillaMaterialRules.BEDROCK_FLOOR),
                MaterialRules.getRule(rules, VanillaMaterialRules.BEDROCK_ROOF),
                makeifTrueRule(biomes, NetherDescentBiomes.ARISIAN_UNDERGROWTH, MaterialRules.sequence(
                        makeifTrueRule(ABOVE_31, makeifTrueRule(onFloor, makeStateRule(NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get()))),
                        makeifTrueRule(onCeiling, makeifTrueRule(MaterialRules.noiseCondition3d(Noises.PATCH, 0.3D), NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get())),
                        makeStateRule(Blocks.BLACKSTONE)
                )),
                makeifTrueRule(biomes, NetherDescentBiomes.CRIMSON_GARDENS, MaterialRules.sequence(
                        makeifTrueRule(ABOVE_31, makeifTrueRule(onFloor,
                                makeStateRule(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get()))),
                        makeStateRule(Blocks.BLACKSTONE)
                )),
                makeifTrueRule(biomes, NetherDescentBiomes.EMBUR_BOG, MaterialRules.sequence(
                        makeifTrueRule(ABOVE_31, makeifTrueRule(onFloor, MaterialRules.sequence(
                                makeifTrueRule(MaterialRules.noiseCondition3d(Noises.PATCH, 0.3D), NetherDescentBlocks.EMBUR_MOSS_BLOCK.get()),
                                makeStateRule(NetherDescentBlocks.EMBUR_NYLIUM.get())
                        ))),
                        makeStateRule(NetherDescentBlocks.BLUE_NETHERRACK.get())
                )),
                makeifTrueRule(biomes, NetherDescentBiomes.SYTHIAN_TORRIDS, MaterialRules.sequence(
                        makeifTrueRule(ABOVE_31, makeifTrueRule(onFloor, MaterialRules.sequence(
                                makeifTrueRule(MaterialRules.noiseCondition3d(Noises.PATCH, 0.3D), NetherDescentBlocks.SYTHIAN_SOIL.get()),
                                makeStateRule(NetherDescentBlocks.SYTHIAN_NYLIUM.get())
                        ))),
                        makeifTrueRule(onCeiling, makeifTrueRule(MaterialRules.noiseCondition3d(Noises.PATCH, 0.3D), NetherDescentBlocks.SYTHIAN_SOIL.get()))
                )),
                makeifTrueRule(biomes, NetherDescentBiomes.WAILING_GARTH, MaterialRules.sequence(
                        makeifTrueRule(ABOVE_31, makeifTrueRule(onFloor, MaterialRules.sequence(
                                NetherDescentRuleSources.weightedRuleSource(WeightedList.<MaterialRule>builder()
                                        .add(makeifTrueRule(onFloor, Blocks.SOUL_SOIL), 3)
                                        .add(makeifTrueRule(onFloor, NetherDescentBlocks.WAILING_NYLIUM.get()))
                                        .build())
                        ))),
                        makeifTrueRule(onCeiling, Blocks.SOUL_SOIL),
                        makeifTrueRule(underCeiling, Blocks.SOUL_SOIL)))
        );
    });

    /**
     * Makes a State Rule for a block.
     * @param block The block to make the rule for.
     * @return The State rule
     */
    private static <B extends Block> MaterialRule makeStateRule(B block) {
        return MaterialRules.state(block.defaultBlockState());
    }

    /**
     * Makes a rule that checks if a biome is true.
     * @param biome The biome to check for.
     * @param rule(s) The rule(s) to apply if the biome is true.
     * @return the surface rule
     */
    private static MaterialRule makeifTrueRule(HolderGetter<Biome> holderGetter, ResourceKey<Biome> biome, MaterialRule rule) {
        return makeifTrueRule(MaterialRules.isBiome(holderGetter, biome), rule);
    }

    /**
     * Makes a rule that checks if a condition is true.
     * @param conditionSource The condition to check for.
     * @param block The block to apply if the condition is true.
     * @return the surface rule
     */
    private static <B extends Block> MaterialRule makeifTrueRule(MaterialCondition conditionSource, B block) {
        return makeifTrueRule(conditionSource, makeStateRule(block));
    }

    private static MaterialRule makeifTrueRule(MaterialCondition ifTrue, MaterialRule thenRun) {
        return MaterialRules.ifTrue(ifTrue, thenRun);
    }

    private static ResourceKey<MaterialRule> register(String name, MaterialRuleFactory factory) {
        ResourceKey<MaterialRule> key = NetherDescent.key(Registries.MATERIAL_RULE, name);
        MATERIAL_RULES_FACTORIES.put(key, factory);
        return key;
    }

    @FunctionalInterface
    public interface MaterialRuleFactory {
        MaterialRule generate(BootstrapContext<MaterialRule> materialRuleBootstrapContext);
    }
}
