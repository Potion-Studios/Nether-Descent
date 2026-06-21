package net.potionstudios.netherdescent.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;

public class PlaceFlowerNearHornetTrigger extends SimpleCriterionTrigger<PlaceFlowerNearHornetTrigger.TriggerInstance> {
    static final ResourceLocation ID = NetherDescent.id("place_flower_near_hornet");

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate predicate, @NotNull DeserializationContext deserializationContext) {
        return new TriggerInstance(predicate);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) {
            super(ID, player);
        }

        public static TriggerInstance create() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }
    }
}
