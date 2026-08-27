package net.potionstudios.netherdescent.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;

import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlaceFlowerNearHornetTrigger extends SimpleCriterionTrigger<PlaceFlowerNearHornetTrigger.TriggerInstance> {

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                                .forGetter(TriggerInstance::player)
                ).apply(inst, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> create() {
            return NetherDescentCriteriaTriggers.PLACE_FLOWER_NEAR_HORNET.get().createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
