package net.potionstudios.netherdescent.world.level.levelgen.surfacerules;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.minecraft.world.level.levelgen.material.rule.RuleEvaluator;
import org.jspecify.annotations.NonNull;

public record WeightedRule(WeightedList<MaterialRule> ruleSources) implements MaterialRule {
    public static final MapCodec<WeightedRule> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    WeightedList.codec(MaterialRule.CODEC).fieldOf("provider").forGetter(WeightedRule::ruleSources)
            ).apply(builder, WeightedRule::new));

    @Override
    public @NonNull RuleEvaluator compile(@NonNull MaterialRuleContext context) {
        PositionalRandomFactory random = context.system.noiseRandom;
        RuleEvaluator[][] rules = new RuleEvaluator[16][16];

        for (int x = 0; x < rules.length; x++)
            for (int z = 0; z < rules[x].length; z++) {
                RuleEvaluator apply = this.ruleSources.getRandom(random.at(x, 0, z)).get().compile(context);
                rules[x][z] = apply;
            }

        return (x, y, z) -> rules[x & 15][z & 15].tryApply(x, y, z);
    }

    @Override
    public @NonNull MapCodec<? extends MaterialRule> codec() {
        return CODEC;
    }
}
