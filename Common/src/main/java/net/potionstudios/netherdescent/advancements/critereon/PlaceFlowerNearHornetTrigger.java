package net.potionstudios.netherdescent.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class PlaceFlowerNearHornetTrigger extends SimpleCriterionTrigger<PlaceFlowerNearHornetTrigger.TriggerInstance> {

    @Override
    public @NonNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        LootItemCondition.CODEC.optionalFieldOf("player")
                                .forGetter(TriggerInstance::player)
                ).apply(inst, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> create() {
            return NetherDescentCriteriaTriggers.PLACE_FLOWER_NEAR_HORNET.get().createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
