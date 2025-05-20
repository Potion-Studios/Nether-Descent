package net.potionstudios.netherdescent.world.level.levelgen.surfacerules;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentRuleSources {

    public static WeightedRuleSource weightedRuleSource(SimpleWeightedRandomList<SurfaceRules.RuleSource> ruleSource) {
        return new WeightedRuleSource(ruleSource);
    }

    private static void register(String id, Supplier<MapCodec<? extends SurfaceRules.RuleSource>> codec) {
        PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.MATERIAL_RULE, id, codec);
    }

    public static void ruleSources() {
        NetherDescent.LOGGER.info("Registering Nether Descent Surface Rules");
        register("state_provider", WeightedRuleSource.CODEC::codec);
    }
}
