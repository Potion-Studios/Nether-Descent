package net.potionstudios.netherdescent.world.level.levelgen.surfacerules;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentRuleSources {

    public static WeightedRule weightedRuleSource(WeightedList<MaterialRule> ruleSource) {
        return new WeightedRule(ruleSource);
    }

    private static void register(String id, Supplier<MapCodec<? extends MaterialRule>> codec) {
        PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.MATERIAL_RULE_TYPE, id, codec);
    }

    public static void ruleSources() {
        NetherDescent.LOGGER.info("Registering Nether Descent Surface Rules");
        register("state_provider", () -> WeightedRule.CODEC);
    }
}
